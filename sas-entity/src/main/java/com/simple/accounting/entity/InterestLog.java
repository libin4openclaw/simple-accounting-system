package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 利息计提日志实体
 * 记录每日利息计提情况
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sas_interest_log")
public class InterestLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志编号（主键）
     */
    @TableId(value = "log_no", type = IdType.INPUT)
    private String logNo;

    /**
     * 关联贷款账号
     */
    @TableField("loan_no")
    private String loanNo;

    /**
     * 会计日期
     */
    @TableField("account_date")
    private LocalDate accountDate;

    /**
     * 正常本金
     */
    @TableField("normal_principal")
    private BigDecimal normalPrincipal;

    /**
     * 逾期本金
     */
    @TableField("overdue_principal")
    private BigDecimal overduePrincipal;

    /**
     * 正常利息
     */
    @TableField("normal_interest")
    private BigDecimal normalInterest;

    /**
     * 逾期利息
     */
    @TableField("overdue_interest")
    private BigDecimal overdueInterest;

    /**
     * 本金罚息
     */
    @TableField("principal_penalty")
    private BigDecimal principalPenalty;

    /**
     * 利息罚息（复利）
     */
    @TableField("interest_penalty")
    private BigDecimal interestPenalty;

    /**
     * 合计计提金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 计提类型：1-日终计提，2-提前还款计提，3-逾期计提
     */
    @TableField("accrual_type")
    private Integer accrualType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
