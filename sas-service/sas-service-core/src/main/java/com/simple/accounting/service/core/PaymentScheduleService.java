package com.simple.accounting.service.core;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simple.accounting.common.constants.BusinessConstants;
import com.simple.accounting.common.exception.BusinessException;
import com.simple.accounting.entity.LoanAccount;
import com.simple.accounting.entity.PaymentSchedule;
import com.simple.accounting.mapper.PaymentScheduleMapper;
import com.simple.accounting.service.calc.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 还款计划服务
 * 提供还款计划的查询、更新等功能
 */
@Slf4j
@Service
public class PaymentScheduleService extends ServiceImpl<PaymentScheduleMapper, PaymentSchedule> {

    @Resource
    private PaymentScheduleMapper paymentScheduleMapper;

    /**
     * 根据贷款账号查询还款计划列表
     * @param loanNo 贷款账号
     * @return 还款计划列表
     */
    public List<PaymentSchedule> getByLoanNo(String loanNo) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        return paymentScheduleMapper.selectByLoanNo(loanNo);
    }

    /**
     * 根据贷款账号和期次查询还款计划
     * @param loanNo 贷款账号
     * @param period 期次
     * @return 还款计划信息
     */
    public PaymentSchedule getByLoanNoAndPeriod(String loanNo, Integer period) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        if (period == null || period <= 0) {
            throw new BusinessException("期次必须大于0");
        }
        return paymentScheduleMapper.selectByLoanNoAndPeriod(loanNo, period);
    }

    /**
     * 查询未还清的还款计划列表
     * @param loanNo 贷款账号
     * @return 未还清的还款计划列表
     */
    public List<PaymentSchedule> getUnpaidSchedules(String loanNo) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }
        return paymentScheduleMapper.selectUnpaidSchedules(loanNo);
    }

    /**
     * 查询待还款的还款计划
     * @param loanNo 贷款账号
     * @return 还款计划列表
     */
    public List<PaymentSchedule> getPendingByLoanNo(String loanNo) {
        if (loanNo == null || loanNo.isEmpty()) {
            throw new BusinessException("贷款账号不能为空");
        }

        LambdaQueryWrapper<PaymentSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentSchedule::getLoanNo, loanNo);
        wrapper.eq(PaymentSchedule::getStatus, BusinessConstants.SCHEDULE_STATUS_PENDING);
        wrapper.orderByAsc(PaymentSchedule::getPeriod);

        return paymentScheduleMapper.selectList(wrapper);
    }

    /**
     * 更新还款计划状态
     * @param id 还款计划ID
     * @param status 状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        if (id == null) {
            throw new BusinessException("还款计划ID不能为空");
        }
        if (status == null) {
            throw new BusinessException("状态不能为空");
        }

        PaymentSchedule schedule = new PaymentSchedule();
        schedule.setId(id);
        schedule.setStatus(status);

        int rows = paymentScheduleMapper.updateById(schedule);
        return rows > 0;
    }

    /**
     * 更新还款计划已还金额
     * @param id 还款计划ID
     * @param paidPrincipal 已还本金
     * @param paidInterest 已还利息
     * @param paidPenalty 已还罚息
     * @param status 状态
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePaidAmount(Long id, BigDecimal paidPrincipal,
                                    BigDecimal paidInterest, BigDecimal paidPenalty, Integer status) {
        if (id == null) {
            throw new BusinessException("还款计划ID不能为空");
        }

        PaymentSchedule schedule = new PaymentSchedule();
        schedule.setId(id);
        schedule.setPaidPrincipal(paidPrincipal);
        schedule.setPaidInterest(paidInterest);
        schedule.setPaidPenalty(paidPenalty);
        schedule.setStatus(status);

        int rows = paymentScheduleMapper.updateById(schedule);
        return rows > 0;
    }

    /**
     * 重新生成还款计划（提前还款后）
     * @param account 贷款账户
     * @param advanceRepayDate 提前还款日期
     */
    @Transactional(rollbackFor = Exception.class)
    public void regenerateSchedules(LoanAccount account, LocalDate advanceRepayDate) {
        String loanNo = account.getLoanNo();
        log.info("重新生成还款计划，贷款账号：{}", loanNo);

        // 删除旧的未还计划
        List<PaymentSchedule> oldSchedules = getUnpaidSchedules(loanNo);
        if (oldSchedules != null && !oldSchedules.isEmpty()) {
            for (PaymentSchedule schedule : oldSchedules) {
                paymentScheduleMapper.deleteById(schedule.getId());
            }
        }

        // 查询已还的最后一期
        LambdaQueryWrapper<PaymentSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentSchedule::getLoanNo, loanNo);
        wrapper.eq(PaymentSchedule::getStatus, BusinessConstants.SCHEDULE_STATUS_PAID);
        wrapper.orderByDesc(PaymentSchedule::getPeriod);
        wrapper.last("LIMIT 1");
        PaymentSchedule lastPaidSchedule = paymentScheduleMapper.selectOne(wrapper);

        int nextPeriod = 1;
        LocalDate firstRepayDate = advanceRepayDate.plusMonths(1);

        if (lastPaidSchedule != null) {
            nextPeriod = lastPaidSchedule.getPeriod() + 1;
            firstRepayDate = lastPaidSchedule.getRepayDate().plusMonths(1);
        }

        // 计算剩余期数
        int remainingTerm = account.getRemainingTerm();
        if (remainingTerm <= 0) {
            remainingTerm = 1;
        }

        // 生成新的还款计划
        List<PaymentSchedule> newSchedules = ScheduleService.generateSchedule(
            account.getRemainingPrincipal(),
            account.getInterestRate(),
            remainingTerm,
            account.getRepaymentMethod(),
            advanceRepayDate,
            firstRepayDate
        );

        // 保存新计划
        int period = nextPeriod;
        for (PaymentSchedule schedule : newSchedules) {
            schedule.setLoanNo(loanNo);
            schedule.setPeriod(period++);
            paymentScheduleMapper.insert(schedule);
        }

        log.info("重新生成还款计划完成，贷款账号：{}，新计划数：{}", loanNo, newSchedules.size());
    }
}
