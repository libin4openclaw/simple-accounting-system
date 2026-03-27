package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 还款日志实体
 * 记录每次还款的详细信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sas_payment_log")
public class PaymentLog extends BaseEntity {

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
     * 交易流水号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 还款日期
     */
    @TableField("payment_date")
    private LocalDate paymentDate;

    /**
     * 还款期次
     */
    @TableField("period")
    private Integer period;

    /**
     * 还款类型：1-正常还款，2-提前还款，3-逾期还款，4-部分还款
     */
    @TableField("payment_type")
    private Integer paymentType;

    /**
     * 还款总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 偿还本金
     */
    @TableField("principal")
    private BigDecimal principal;

    /**
     * 偿还利息
     */
    @TableField("interest")
    private BigDecimal interest;

    /**
     * 偿还罚息
     */
    @TableField("penalty")
    private BigDecimal penalty;

    /**
     * 偿还复利
     */
    @TableField("compound_interest")
    private BigDecimal compoundInterest;

    /**
     * 偿还费用
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 还款前剩余本金
     */
    @TableField("balance_before")
    private BigDecimal balanceBefore;

    /**
     * 还款后剩余本金
     */
    @TableField("balance_after")
    private BigDecimal balanceAfter;

    /**
     * 是否撤销：0-否，1-是
     */
    @TableField("is_reversed")
    private Integer isReversed;

    /**
     * 撤销时间
     */
    @TableField("reverse_time")
    private LocalDateTime reverseTime;

    /**
     * 撤销备注
     */
    @TableField("reverse_remark")
    private String reverseRemark;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
