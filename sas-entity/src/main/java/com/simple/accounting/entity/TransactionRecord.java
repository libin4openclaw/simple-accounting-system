package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 交易记录实体类
 * 记录每一笔交易的核心信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_record")
public class TransactionRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 贷款账号
     */
    @TableField("loan_no")
    private String loanNo;

    /**
     * 交易类型：1-放款，2-正常还款，3-提前还款，4-逾期还款
     */
    @TableField("trans_type")
    private Integer transType;

    /**
     * 交易日期
     */
    @TableField("trans_date")
    private LocalDate transDate;

    /**
     * 交易金额
     */
    @TableField("trans_amount")
    private BigDecimal transAmount;

    /**
     * 本金
     */
    @TableField("principal")
    private BigDecimal principal;

    /**
     * 利息
     */
    @TableField("interest")
    private BigDecimal interest;

    /**
     * 罚息
     */
    @TableField("penalty")
    private BigDecimal penalty;

    /**
     * 交易前余额
     */
    @TableField("balance_before")
    private BigDecimal balanceBefore;

    /**
     * 交易后余额
     */
    @TableField("balance_after")
    private BigDecimal balanceAfter;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
