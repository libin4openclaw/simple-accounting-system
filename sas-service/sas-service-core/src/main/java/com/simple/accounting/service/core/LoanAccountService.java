package com.simple.accounting.service.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.accounting.common.constants.BusinessConstants;
import com.simple.accounting.common.constants.CommonConstants;
import com.simple.accounting.common.exception.BusinessException;
import com.simple.accounting.common.util.NoGeneratorUtil;
import com.simple.accounting.entity.LoanAccount;
import com.simple.accounting.entity.PaymentSchedule;
import com.simple.accounting.mapper.LoanAccountMapper;
import com.simple.accounting.service.calc.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 贷款账户服务
 * 提供贷款账户的创建、查询、更新等功能
 */
@Slf4j
@Service
public class LoanAccountService extends ServiceImpl<LoanAccountMapper, LoanAccount> {

    @Resource
    private LoanAccountMapper loanAccountMapper;

    @Resource
    private PaymentScheduleService paymentScheduleService;

    /**
     * 创建贷款账户
     * @param loanAccount 贷款账户信息
     * @return 创建后的贷款账户
     */
    @Transactional(rollbackFor = Exception.class)
    public LoanAccount createLoanAccount(LoanAccount loanAccount) {
        log.info("开始创建贷款账户，客户ID：{}，合同金额：{}", loanAccount.getCustomerId(), loanAccount.getContractAmount());

        // 校验参数
        validateLoanAccount(loanAccount);

        // 生成贷款账号
        String loanNo = NoGeneratorUtil.generateLoanNo();
        loanAccount.setLoanNo(loanNo);

        // 生成合同编号（如果没有）
        if (loanAccount.getContractNo() == null || loanAccount.getContractNo().isEmpty()) {
            loanAccount.setContractNo(NoGeneratorUtil.generateContractNo());
        }

        // 设置默认值
        if (loanAccount.getActualAmount() == null) {
            loanAccount.setActualAmount(loanAccount.getContractAmount());
        }
        if (loanAccount.getRemainingTerm() == null) {
            loanAccount.setRemainingTerm(loanAccount.getContractTerm());
        }
        if (loanAccount.getOverdueRate() == null) {
            // 默认逾期罚息利率为年利率的1.5倍
            loanAccount.setOverdueRate(loanAccount.getInterestRate().multiply(new BigDecimal("1.5")));
        }
        if (loanAccount.getLoanStatus() == null) {
            loanAccount.setLoanStatus(BusinessConstants.LOAN_STATUS_NORMAL);
        }
        if (loanAccount.getRemainingPrincipal() == null) {
            loanAccount.setRemainingPrincipal(loanAccount.getActualAmount());
        }
        if (loanAccount.getPaidPrincipal() == null) {
            loanAccount.setPaidPrincipal(BigDecimal.ZERO);
        }
        if (loanAccount.getPaidInterest() == null) {
            loanAccount.setPaidInterest(BigDecimal.ZERO);
        }
        if (loanAccount.getPaidPenalty() == null) {
            loanAccount.setPaidPenalty(BigDecimal.ZERO);
        }
        if (loanAccount.getOverdueDays() == null) {
            loanAccount.setOverdueDays(0);
        }
        if (loanAccount.getOverdueAmount() == null) {
            loanAccount.setOverdueAmount(BigDecimal.ZERO);
        }

        // 计算到期日期
        if (loanAccount.getMaturityDate() == null && loanAccount.getFirstRepayDate() != null) {
            loanAccount.setMaturityDate(loanAccount.getFirstRepayDate().plusMonths(loanAccount.getContractTerm() - 1));
        }

        // 保存贷款账户
        loanAccountMapper.insert(loanAccount);

        // 生成还款计划
        if (loanAccount.getDisbursementDate() != null && loanAccount.getFirstRepayDate() != null) {
            List<PaymentSchedule> scheduleList = ScheduleService.generateSchedule(
                    loanAccount.getActualAmount(),
                    loanAccount.getInterestRate(),
                    loanAccount.getContractTerm(),
                    loanAccount.getRepaymentMethod(),
                    loanAccount.getDisbursementDate(),
                    loanAccount.getFirstRepayDate()
            );

            // 保存还款计划
            for (PaymentSchedule schedule : scheduleList) {
                schedule.setLoanNo(loanAccount.getLoanNo());
                paymentScheduleService.save(schedule);
            }
        }

        log.info("贷款账户创建成功，贷款账号：{}", loanAccount.getLoanNo());
        return loanAccount;
    }

    /**
     * 根据贷款账号查询贷款账户
     * @param loanNo 贷款账号
     * @return 贷款账户信息
     */
    public LoanAccount getByLoanNo(String loanNo) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        return loanAccountMapper.selectByLoanNo(loanNo);
    }

    /**
     * 根据客户ID查询贷款账户列表
     * @param customerId 客户ID
     * @return 贷款账户列表
     */
    public List<LoanAccount> getByCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new BusinessException("客户ID不能为空");
        }

        LambdaQueryWrapper<LoanAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanAccount::getCustomerId, customerId);
        wrapper.orderByDesc(LoanAccount::getCreateTime);

        return loanAccountMapper.selectList(wrapper);
    }

    /**
     * 更新贷款账户状态
     * @param loanNo 贷款账号
     * @param loanStatus 贷款状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLoanStatus(String loanNo, Integer loanStatus) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        if (loanStatus == null) {
            throw new BusinessException("贷款状态不能为空");
        }

        int rows = loanAccountMapper.updateLoanStatus(loanNo, loanStatus);
        return rows > 0;
    }

    /**
     * 更新剩余本金
     * @param loanNo 贷款账号
     * @param remainingPrincipal 剩余本金
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRemainingPrincipal(String loanNo, BigDecimal remainingPrincipal) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        if (remainingPrincipal == null) {
            throw new BusinessException("剩余本金不能为空");
        }

        int rows = loanAccountMapper.updateRemainingPrincipal(loanNo, remainingPrincipal);
        return rows > 0;
    }

    /**
     * 校验贷款账户参数
     * @param loanAccount 贷款账户信息
     */
    private void validateLoanAccount(LoanAccount loanAccount) {
        if (loanAccount.getCustomerId() == null || loanAccount.getCustomerId().isEmpty()) {
            throw new BusinessException("客户ID不能为空");
        }
        if (loanAccount.getCustomerName() == null || loanAccount.getCustomerName().isEmpty()) {
            throw new BusinessException("客户姓名不能为空");
        }
        if (loanAccount.getContractAmount() == null || loanAccount.getContractAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("合同金额必须大于0");
        }
        if (loanAccount.getContractTerm() == null || loanAccount.getContractTerm() <= 0) {
            throw new BusinessException("合同期限必须大于0");
        }
        if (loanAccount.getInterestRate() == null || loanAccount.getInterestRate().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("年利率必须大于0");
        }
        if (loanAccount.getRepaymentMethod() == null) {
            throw new BusinessException("还款方式不能为空");
        }
        if (loanAccount.getDisbursementDate() == null) {
            throw new BusinessException("放款日期不能为空");
        }
        if (loanAccount.getFirstRepayDate() == null) {
            throw new BusinessException("首次还款日不能为空");
        }
        if (loanAccount.getFirstRepayDate().isBefore(loanAccount.getDisbursementDate())) {
            throw new BusinessException("首次还款日不能早于放款日期");
        }
    }
}
