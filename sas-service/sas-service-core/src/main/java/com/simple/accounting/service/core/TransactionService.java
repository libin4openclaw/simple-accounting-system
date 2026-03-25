package com.simple.accounting.service.core;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.accounting.common.constants.BusinessConstants;
import com.simple.accounting.common.exception.BusinessException;
import com.simple.accounting.common.util.NoGeneratorUtil;
import com.simple.accounting.entity.LoanAccount;
import com.simple.accounting.entity.PaymentSchedule;
import com.simple.accounting.entity.TransactionRecord;
import com.simple.accounting.mapper.TransactionRecordMapper;
import com.simple.accounting.service.calc.InterestCalcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * 交易服务
 * 提供放款、还款、提前还款等交易处理功能
 */
@Slf4j
@Service
public class TransactionService extends ServiceImpl<TransactionRecordMapper, TransactionRecord> {

    @Resource
    private TransactionRecordMapper transactionRecordMapper;

    @Resource
    private LoanAccountService loanAccountService;

    @Resource
    private PaymentScheduleService paymentScheduleService;

    /**
     * 放款交易
     * @param loanNo 贷款账号
     * @param disbursementDate 放款日期
     * @return 交易记录
     */
    @Transactional(rollbackFor = Exception.class)
    public TransactionRecord disbursement(String loanNo, LocalDate disbursementDate) {
        log.info("开始放款交易，贷款账号：{}", loanNo);

        // 1. 查询贷款账户
        LoanAccount account = loanAccountService.getByLoanNo(loanNo);
        if (account == null) {
            throw new BusinessException("贷款账户不存在");
        }
        if (!BusinessConstants.LOAN_STATUS_NORMAL.equals(account.getLoanStatus())) {
            throw new BusinessException("贷款账户状态异常，无法放款");
        }

        // 2. 创建交易记录
        TransactionRecord transaction = new TransactionRecord();
        transaction.setTransNo(NoGeneratorUtil.generateTransNo());
        transaction.setLoanNo(loanNo);
        transaction.setTransType(BusinessConstants.TRANS_TYPE_DISBURSEMENT);
        transaction.setTransDate(disbursementDate);
        transaction.setTransAmount(account.getActualAmount());
        transaction.setPrincipal(account.getActualAmount());
        transaction.setInterest(BigDecimal.ZERO);
        transaction.setPenalty(BigDecimal.ZERO);
        transaction.setBalanceBefore(BigDecimal.ZERO);
        transaction.setBalanceAfter(account.getActualAmount());
        transaction.setRemark("放款交易");
        transaction.setCreateTime(java.time.LocalDateTime.now());

        // 3. 保存交易记录
        transactionRecordMapper.insert(transaction);

        // 4. 更新贷款账户状态为放款中
        loanAccountService.updateLoanStatus(loanNo, BusinessConstants.LOAN_STATUS_ACTIVE);

        log.info("放款交易完成，交易流水号：{}", transaction.getTransNo());
        return transaction;
    }

    /**
     * 正常还款交易
     * @param loanNo 贷款账号
     * @param repayAmount 还款金额
     * @param repayDate 还款日期
     * @return 交易记录
     */
    @Transactional(rollbackFor = Exception.class)
    public TransactionRecord normalRepayment(String loanNo, BigDecimal repayAmount, LocalDate repayDate) {
        log.info("开始正常还款交易，贷款账号：{}，还款金额：{}", loanNo, repayAmount);

        // 1. 查询贷款账户
        LoanAccount account = loanAccountService.getByLoanNo(loanNo);
        if (account == null) {
            throw new BusinessException("贷款账户不存在");
        }
        if (!BusinessConstants.LOAN_STATUS_ACTIVE.equals(account.getLoanStatus())) {
            throw new BusinessException("贷款账户状态异常，无法还款");
        }

        // 2. 查询未还的还款计划
        List<PaymentSchedule> unpaidSchedules = paymentScheduleService.getUnpaidSchedules(loanNo);
        if (unpaidSchedules == null || unpaidSchedules.isEmpty()) {
            throw new BusinessException("没有待还的还款计划");
        }

        // 3. 逐期还款
        BigDecimal remainingAmount = repayAmount;
        BigDecimal totalPaidPrincipal = BigDecimal.ZERO;
        BigDecimal totalPaidInterest = BigDecimal.ZERO;
        BigDecimal totalPaidPenalty = BigDecimal.ZERO;

        for (PaymentSchedule schedule : unpaidSchedules) {
            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            // 计算当期应还总额
            BigDecimal shouldPay = schedule.getPrincipal().add(schedule.getInterest());

            // 检查是否逾期
            int overdueDays = 0;
            BigDecimal penalty = BigDecimal.ZERO;
            if (repayDate.isAfter(schedule.getRepayDate())) {
                overdueDays = (int) java.time.temporal.ChronoUnit.DAYS.between(schedule.getRepayDate(), repayDate);
                penalty = InterestCalcService.calcOverduePenalty(
                    schedule.getRemainingPrincipal(),
                    account.getOverdueRate(),
                    overdueDays
                );
                shouldPay = shouldPay.add(penalty);
            }

            if (remainingAmount.compareTo(shouldPay) >= 0) {
                // 足额还款
                totalPaidPrincipal = totalPaidPrincipal.add(schedule.getPrincipal());
                totalPaidInterest = totalPaidInterest.add(schedule.getInterest());
                totalPaidPenalty = totalPaidPenalty.add(penalty);

                // 更新还款计划
                paymentScheduleService.updatePaidAmount(
                    schedule.getId(),
                    schedule.getPrincipal(),
                    schedule.getInterest(),
                    penalty,
                    BusinessConstants.SCHEDULE_STATUS_PAID
                );

                remainingAmount = remainingAmount.subtract(shouldPay);
            } else {
                // 不足额还款，先还罚息，再还利息，最后还本
                BigDecimal penaltyPart = remainingAmount.min(penalty);
                BigDecimal interestPart = penalty.equals(penaltyPart)
                    ? remainingAmount.subtract(penaltyPart).min(schedule.getInterest())
                    : BigDecimal.ZERO;
                BigDecimal principalPart = remainingAmount.subtract(penaltyPart).subtract(interestPart)
                    .min(schedule.getPrincipal());

                totalPaidPrincipal = totalPaidPrincipal.add(principalPart);
                totalPaidInterest = totalPaidInterest.add(interestPart);
                totalPaidPenalty = totalPaidPenalty.add(penaltyPart);

                // 更新还款计划（部分还款）
                paymentScheduleService.updatePaidAmount(
                    schedule.getId(),
                    principalPart,
                    interestPart,
                    penaltyPart,
                    BusinessConstants.SCHEDULE_STATUS_PARTIAL_PAID
                );

                remainingAmount = BigDecimal.ZERO;
            }
        }

        // 4. 创建交易记录
        TransactionRecord transaction = new TransactionRecord();
        transaction.setTransNo(NoGeneratorUtil.generateTransNo());
        transaction.setLoanNo(loanNo);
        transaction.setTransType(BusinessConstants.TRANS_TYPE_NORMAL_REPAYMENT);
        transaction.setTransDate(repayDate);
        transaction.setTransAmount(repayAmount.subtract(remainingAmount));
        transaction.setPrincipal(totalPaidPrincipal);
        transaction.setInterest(totalPaidInterest);
        transaction.setPenalty(totalPaidPenalty);
        transaction.setBalanceBefore(account.getRemainingPrincipal());
        transaction.setBalanceAfter(account.getRemainingPrincipal().subtract(totalPaidPrincipal));
        transaction.setRemark("正常还款");
        transaction.setCreateTime(java.time.LocalDateTime.now());

        // 5. 保存交易记录
        transactionRecordMapper.insert(transaction);

        // 6. 更新贷款账户
        account.setRemainingPrincipal(account.getRemainingPrincipal().subtract(totalPaidPrincipal));
        account.setPaidPrincipal(account.getPaidPrincipal().add(totalPaidPrincipal));
        account.setPaidInterest(account.getPaidInterest().add(totalPaidInterest));
        account.setPaidPenalty(account.getPaidPenalty().add(totalPaidPenalty));
        account.setRemainingTerm(calculateRemainingTerm(loanNo));

        // 检查是否已结清
        if (account.getRemainingPrincipal().compareTo(BigDecimal.ZERO) <= 0) {
            account.setLoanStatus(BusinessConstants.LOAN_STATUS_SETTLED);
        }

        loanAccountService.updateById(account);

        log.info("正常还款交易完成，交易流水号：{}", transaction.getTransNo());
        return transaction;
    }

    /**
     * 提前还款交易
     * @param loanNo 贷款账号
     * @param repayAmount 还款金额
     * @param repayDate 还款日期
     * @param prepaymentPenaltyRate 提前还款违约金比例
     * @return 交易记录
     */
    @Transactional(rollbackFor = Exception.class)
    public TransactionRecord advanceRepayment(String loanNo, BigDecimal repayAmount,
                                                 LocalDate repayDate, BigDecimal prepaymentPenaltyRate) {
        log.info("开始提前还款交易，贷款账号：{}，还款金额：{}", loanNo, repayAmount);

        // 1. 查询贷款账户
        LoanAccount account = loanAccountService.getByLoanNo(loanNo);
        if (account == null) {
            throw new BusinessException("贷款账户不存在");
        }
        if (!BusinessConstants.LOAN_STATUS_ACTIVE.equals(account.getLoanStatus())) {
            throw new BusinessException("贷款账户状态异常，无法还款");
        }

        // 2. 计算提前还款违约金
        BigDecimal penalty = account.getRemainingPrincipal().multiply(prepaymentPenaltyRate)
            .setScale(2, RoundingMode.HALF_UP);

        // 3. 计算可还本金（扣除违约金）
        BigDecimal availableForPrincipal = repayAmount.subtract(penalty);
        if (availableForPrincipal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("还款金额不足以支付违约金");
        }

        // 4. 实际还本金额（不超过剩余本金）
        BigDecimal actualPrincipal = availableForPrincipal.min(account.getRemainingPrincipal());

        // 5. 创建交易记录
        TransactionRecord transaction = new TransactionRecord();
        transaction.setTransNo(NoGeneratorUtil.generateTransNo());
        transaction.setLoanNo(loanNo);
        transaction.setTransType(BusinessConstants.TRANS_TYPE_ADVANCE_REPAYMENT);
        transaction.setTransDate(repayDate);
        transaction.setTransAmount(actualPrincipal.add(penalty));
        transaction.setPrincipal(actualPrincipal);
        transaction.setInterest(BigDecimal.ZERO);
        transaction.setPenalty(penalty);
        transaction.setBalanceBefore(account.getRemainingPrincipal());
        transaction.setBalanceAfter(account.getRemainingPrincipal().subtract(actualPrincipal));
        transaction.setRemark("提前还款，违约金比例：" + prepaymentPenaltyRate);
        transaction.setCreateTime(java.time.LocalDateTime.now());

        // 6. 保存交易记录
        transactionRecordMapper.insert(transaction);

        // 7. 更新贷款账户
        account.setRemainingPrincipal(account.getRemainingPrincipal().subtract(actualPrincipal));
        account.setPaidPrincipal(account.getPaidPrincipal().add(actualPrincipal));
        account.setPaidPenalty(account.getPaidPenalty().add(penalty));

        // 检查是否已结清
        if (account.getRemainingPrincipal().compareTo(BigDecimal.ZERO) <= 0) {
            account.setLoanStatus(BusinessConstants.LOAN_STATUS_SETTLED);
            account.setRemainingTerm(0);
        } else {
            // 重新生成还款计划
            paymentScheduleService.regenerateSchedules(account, repayDate);
        }

        loanAccountService.updateById(account);

        log.info("提前还款交易完成，交易流水号：{}", transaction.getTransNo());
        return transaction;
    }

    /**
     * 计算剩余期数
     * @param loanNo 贷款账号
     * @return 剩余期数
     */
    private Integer calculateRemainingTerm(String loanNo) {
        List<PaymentSchedule> unpaidSchedules = paymentScheduleService.getUnpaidSchedules(loanNo);
        if (unpaidSchedules == null || unpaidSchedules.isEmpty()) {
            return 0;
        }
        return unpaidSchedules.size();
    }
}
