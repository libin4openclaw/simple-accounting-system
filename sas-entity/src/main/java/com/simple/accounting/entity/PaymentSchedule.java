package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 还款计划实体类
 * 记录每一期还款计划的详细信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("payment_schedule")
public class PaymentSchedule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款账号
     */
    @TableField("loan_no")
    private String loanNo;

    /**
     * 期次（第X期）
     */
    @TableField("period")
    private Integer period;

    /**
     * 计划还款日期
     */
    @TableField("repay_date")
    private LocalDate repayDate;

    /**
     * 计划还款总额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 计划还本金
     */
    @TableField("principal")
    private BigDecimal principal;

    /**
     * 计划还利息
     */
    @TableField("interest")
    private BigDecimal interest;

    /**
     * 计划罚息
     */
    @TableField("penalty")
    private BigDecimal penalty;

    /**
     * 实际还本金
     */
    @TableField("paid_principal")
    private BigDecimal paidPrincipal;

    /**
     * 实际还利息
     */
    @TableField("paid_interest")
    private BigDecimal paidInterest;

    /**
     * 实际罚息
     */
    @TableField("paid_penalty")
    private BigDecimal paidPenalty;

    /**
     * 剩余本金
     */
    @TableField("remaining_principal")
    private BigDecimal remainingPrincipal;

    /**
     * 状态：0-待还，1-部分还款，2-已还清
     */
    @TableField("status")
    private Integer status;
}
