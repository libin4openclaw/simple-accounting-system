package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 贷款账户实体类
 * 记录贷款账户的基本信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("loan_account")
public class LoanAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 贷款账号
     */
    @TableField("loan_no")
    private String loanNo;

    /**
     * 合同编号
     */
    @TableField("contract_no")
    private String contractNo;

    /**
     * 客户ID
     */
    @TableField("customer_id")
    private String customerId;

    /**
     * 客户姓名
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 合同金额（元）
     */
    @TableField("contract_amount")
    private BigDecimal contractAmount;

    /**
     * 实际放款金额（元）
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;

    /**
     * 合同期限（月）
     */
    @TableField("contract_term")
    private Integer contractTerm;

    /**
     * 剩余期限（月）
     */
    @TableField("remaining_term")
    private Integer remainingTerm;

    /**
     * 年利率
     */
    @TableField("interest_rate")
    private BigDecimal interestRate;

    /**
     * 逾期罚息利率
     */
    @TableField("overdue_rate")
    private BigDecimal overdueRate;

    /**
     * 还款方式：1-等额本息，2-等额本金，3-先息后本
     */
    @TableField("repayment_method")
    private Integer repaymentMethod;

    /**
     * 贷款状态：1-正常，2-逾期，3-结清，4-冻结
     */
    @TableField("loan_status")
    private Integer loanStatus;

    /**
     * 放款日期
     */
    @TableField("disbursement_date")
    private LocalDate disbursementDate;

    /**
     * 首次还款日
     */
    @TableField("first_repay_date")
    private LocalDate firstRepayDate;

    /**
     * 到期日期
     */
    @TableField("maturity_date")
    private LocalDate maturityDate;

    /**
     * 结清日期
     */
    @TableField("settlement_date")
    private LocalDate settlementDate;

    /**
     * 剩余本金（元）
     */
    @TableField("remaining_principal")
    private BigDecimal remainingPrincipal;

    /**
     * 已还本金（元）
     */
    @TableField("paid_principal")
    private BigDecimal paidPrincipal;

    /**
     * 已还利息（元）
     */
    @TableField("paid_interest")
    private BigDecimal paidInterest;

    /**
     * 已还罚息（元）
     */
    @TableField("paid_penalty")
    private BigDecimal paidPenalty;

    /**
     * 逾期天数
     */
    @TableField("overdue_days")
    private Integer overdueDays;

    /**
     * 当前逾期金额（元）
     */
    @TableField("overdue_amount")
    private BigDecimal overdueAmount;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
