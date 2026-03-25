package com.simple.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.accounting.entity.PaymentSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 还款计划Mapper接口
 */
@Mapper
public interface PaymentScheduleMapper extends BaseMapper<PaymentSchedule> {

    /**
     * 根据贷款账号查询还款计划列表
     * @param loanNo 贷款账号
     * @return 还款计划列表
     */
    List<PaymentSchedule> selectByLoanNo(@Param("loanNo") String loanNo);

    /**
     * 根据贷款账号和期次查询还款计划
     * @param loanNo 贷款账号
     * @param period 期次
     * @return 还款计划
     */
    PaymentSchedule selectByLoanNoAndPeriod(@Param("loanNo") String loanNo, @Param("period") Integer period);

    /**
     * 查询当期还款计划
     * @param loanNo 贷款账号
     * @return 当期还款计划
     */
    PaymentSchedule selectCurrentSchedule(@Param("loanNo") String loanNo);

    /**
     * 查询未还清的还款计划列表
     * @param loanNo 贷款账号
     * @return 未还清的还款计划列表
     */
    List<PaymentSchedule> selectUnpaidSchedules(@Param("loanNo") String loanNo);

    /**
     * 更新还款计划状态
     * @param loanNo 贷款账号
     * @param period 期次
     * @param scheduleStatus 计划状态
     * @return 影响行数
     */
    int updateScheduleStatus(@Param("loanNo") String loanNo, @Param("period") Integer period, @Param("scheduleStatus") Integer scheduleStatus);

    /**
     * 批量删除还款计划
     * @param loanNo 贷款账号
     * @return 影响行数
     */
    int deleteByLoanNo(@Param("loanNo") String loanNo);
}
