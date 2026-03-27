package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 贷款账户实体类
 * 基于CFS AcctLoanBase扩展，包含完整的贷款账户信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sas_loan_account")
public class LoanAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    // ==================== 基础信息 ====================
    
    /**
     * 贷款账号（主键）
     */
    @TableId(value = "loan_no", type = IdType.INPUT)
    private String loanNo;

    /**
     * 贷款文本账号
     */
    @TableField("account_no")
    private String accountNo;

    /**
     * 贷款发放号
     */
    @TableField("putout_no")
    private String putoutNo;

    /**
     * 关联合同号
     */
    @TableField("contract_serial_no")
    private String contractSerialNo;

    /**
     * 内部合同号
     */
    @TableField("business_contract_serial_no")
    private String businessContractSerialNo;

    // ==================== 客户信息 ====================

    /**
     * 客户编号
     */
    @TableField("customer_id")
    private String customerId;

    /**
     * 客户名称
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 客户电话号码
     */
    @TableField("customer_phone_no")
    private String customerPhoneNo;

    /**
     * 证件类型
     */
    @TableField("cert_type")
    private String certType;

    /**
     * 证件号
     */
    @TableField("cert_id")
    private String certId;

    /**
     * 核心客户号
     */
    @TableField("core_customer_id")
    private String coreCustomerId;

    // ==================== 产品信息 ====================

    /**
     * 产品编号
     */
    @TableField("product_id")
    private String productId;

    /**
     * 产品规格
     */
    @TableField("specific_id")
    private String specificId;

    /**
     * 业务来源（渠道编号）
     */
    @TableField("system_channel_flag")
    private String systemChannelFlag;

    /**
     * 行业投向
     */
    @TableField("industry_type")
    private String industryType;

    /**
     * 贷款用途
     */
    @TableField("purpose_type")
    private String purposeType;

    /**
     * 担保方式
     */
    @TableField("vouch_type")
    private String vouchType;

    // ==================== 金额期限信息 ====================

    /**
     * 贷款币种
     */
    @TableField("currency")
    private String currency;

    /**
     * 贷款金额（合同金额）
     */
    @TableField("business_sum")
    private BigDecimal businessSum;

    /**
     * 贷款发放日期
     */
    @TableField("putout_date")
    private LocalDate putoutDate;

    /**
     * 贷款到期日
     */
    @TableField("maturity_date")
    private LocalDate maturityDate;

    /**
     * 贷款原始到期日
     */
    @TableField("original_maturity_date")
    private LocalDate originalMaturityDate;

    /**
     * 还款期次数量
     */
    @TableField("total_period")
    private Integer totalPeriod;

    /**
     * 还款期次类型
     */
    @TableField("pay_frequency_type")
    private String payFrequencyType;

    /**
     * 宽限期天数
     */
    @TableField("grace_days")
    private Integer graceDays;

    // ==================== 利率信息 ====================

    /**
     * 贷款利率
     */
    @TableField("business_rate")
    private BigDecimal businessRate;

    /**
     * 罚息利率
     */
    @TableField("fin_business_rate")
    private BigDecimal finBusinessRate;

    /**
     * 税率，单位%
     */
    @TableField("tax_rate")
    private BigDecimal taxRate;

    /**
     * 还款方式
     */
    @TableField("rpt_term_id")
    private String rptTermId;

    // ==================== 机构信息 ====================

    /**
     * 管理机构
     */
    @TableField("manage_org_id")
    private String manageOrgId;

    /**
     * 贷款银行代码
     */
    @TableField("bank_code")
    private String bankCode;

    /**
     * 贷款入账机构
     */
    @TableField("accounting_org_id")
    private String accountingOrgId;

    /**
     * 所属客户经理编号
     */
    @TableField("manage_user_id")
    private String manageUserId;

    // ==================== 状态信息 ====================

    /**
     * 贷款状态
     */
    @TableField("loan_status")
    private String loanStatus;

    /**
     * 原始贷款状态
     */
    @TableField("old_loan_status")
    private String oldLoanStatus;

    /**
     * 业务状态
     */
    @TableField("business_status")
    private String businessStatus;

    /**
     * 锁定标识
     */
    @TableField("lock_flag")
    private String lockFlag;

    /**
     * 核算状态
     */
    @TableField("accounting_type")
    private String accountingType;

    /**
     * 是否应计
     */
    @TableField("is_accrued")
    private String isAccrued;

    /**
     * 结清日期
     */
    @TableField("finish_date")
    private LocalDate finishDate;

    /**
     * 贷款处理日期
     */
    @TableField("business_date")
    private LocalDate businessDate;

    // ==================== 逾期信息 ====================

    /**
     * 逾期天数
     */
    @TableField("overdue_days")
    private Integer overdueDays;

    /**
     * 历史最高逾期天数
     */
    @TableField("max_overdue_days")
    private Integer maxOverdueDays;

    /**
     * 分类结果
     */
    @TableField("classify_result")
    private String classifyResult;

    // ==================== 余额信息（当前） ====================

    /**
     * 正常本金余额
     */
    @TableField("normal_balance")
    private BigDecimal normalBalance;

    /**
     * 逾期本金余额
     */
    @TableField("overdue_balance")
    private BigDecimal overdueBalance;

    /**
     * 计提利息
     */
    @TableField("accrued_interest")
    private BigDecimal accruedInterest;

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
     * 利息罚息
     */
    @TableField("interest_penalty")
    private BigDecimal interestPenalty;

    /**
     * 欠滞纳金金额
     */
    @TableField("overdue_fee")
    private BigDecimal overdueFee;

    /**
     * 欠期次管理费
     */
    @TableField("fee")
    private BigDecimal fee;

    /**
     * 欠服务费金额
     */
    @TableField("service_fee")
    private BigDecimal serviceFee;

    /**
     * 欠渠道费金额
     */
    @TableField("channel_fee")
    private BigDecimal channelFee;

    // ==================== 余额信息（上日） ====================

    /**
     * 上日正常本金余额
     */
    @TableField("lastday_normal_balance")
    private BigDecimal lastdayNormalBalance;

    /**
     * 上日逾期本金余额
     */
    @TableField("lastday_overdue_balance")
    private BigDecimal lastdayOverdueBalance;

    /**
     * 上日计提利息
     */
    @TableField("lastday_accrued_interest")
    private BigDecimal lastdayAccruedInterest;

    /**
     * 上日逾期利息
     */
    @TableField("lastday_overdue_interest")
    private BigDecimal lastdayOverdueInterest;

    /**
     * 上日本金罚息
     */
    @TableField("lastday_principal_penalty")
    private BigDecimal lastdayPrincipalPenalty;

    /**
     * 上日利息罚息
     */
    @TableField("lastday_interest_penalty")
    private BigDecimal lastdayInterestPenalty;

    /**
     * 上日欠滞纳金金额
     */
    @TableField("lastday_overdue_fee")
    private BigDecimal lastdayOverdueFee;

    /**
     * 上日欠期次管理费
     */
    @TableField("lastday_fee")
    private BigDecimal lastdayFee;

    /**
     * 上日欠服务费金额
     */
    @TableField("lastday_service_fee")
    private BigDecimal lastdayServiceFee;

    /**
     * 上日欠渠道费金额
     */
    @TableField("lastday_channel_fee")
    private BigDecimal lastdayChannelFee;

    // ==================== 当日计提信息 ====================

    /**
     * 当日利息
     */
    @TableField("day_accrued_interest")
    private BigDecimal dayAccruedInterest;

    /**
     * 当日罚息
     */
    @TableField("day_principal_penalty")
    private BigDecimal dayPrincipalPenalty;

    /**
     * 当日复利
     */
    @TableField("day_interest_penalty")
    private BigDecimal dayInterestPenalty;

    // ==================== 流水号信息 ====================

    /**
     * 出账流水号
     */
    @TableField("putout_serial_no")
    private String putoutSerialNo;

    /**
     * 审批流水号
     */
    @TableField("approve_serial_no")
    private String approveSerialNo;

    /**
     * 申请流水号
     */
    @TableField("apply_serial_no")
    private String applySerialNo;

    // ==================== 内部户信息 ====================

    /**
     * 放款内部户
     */
    @TableField("loan_acct_no")
    private String loanAcctNo;

    /**
     * 放款内部户名称
     */
    @TableField("loan_acct_name")
    private String loanAcctName;

    /**
     * 还款内部户
     */
    @TableField("repay_acct_no")
    private String repayAcctNo;

    /**
     * 还款内部户名称
     */
    @TableField("repay_acct_name")
    private String repayAcctName;

    // ==================== 出单信息（供数核心） ====================

    /**
     * 出单正常本金
     */
    @TableField("out_normal_balance")
    private BigDecimal outNormalBalance;

    /**
     * 出单计提利息
     */
    @TableField("out_accrued_interest")
    private BigDecimal outAccruedInterest;

    // ==================== 代扣标识 ====================

    /**
     * 协议编码
     */
    @TableField("auth_serial_no")
    private String authSerialNo;

    /**
     * 代扣标识
     */
    @TableField("withhold_flag")
    private String withholdFlag;

    /**
     * 是否计算罚息
     */
    @TableField("is_principal_penalty")
    private String isPrincipalPenalty;

    /**
     * 是否计算复利
     */
    @TableField("is_interest_penalty")
    private String isInterestPenalty;

    // ==================== 代偿信息 ====================

    /**
     * 是否代偿
     */
    @TableField("compensation_flag")
    private String compensationFlag;

    /**
     * 代偿模式
     */
    @TableField("compensation_type")
    private String compensationType;

    /**
     * 代偿天数
     */
    @TableField("compensation_days")
    private Integer compensationDays;

    /**
     * 代偿比例
     */
    @TableField("compensation_proportion")
    private BigDecimal compensationProportion;

    /**
     * 代偿账户
     */
    @TableField("compensation_acct_no")
    private String compensationAcctNo;

    /**
     * 代偿账户名称
     */
    @TableField("compensation_acct_name")
    private String compensationAcctName;

    /**
     * 保单号
     */
    @TableField("guaranty_no")
    private String guarantyNo;

    /**
     * 保险公司标识
     */
    @TableField("insu_ind")
    private String insuInd;

    /**
     * 借据计提总金额
     */
    @TableField("pay_all_insert")
    private BigDecimal payAllInsert;

    // ==================== 资金方信息（联合贷） ====================

    /**
     * 资金方编号
     */
    @TableField("invest_serial_no")
    private String investSerialNo;

    /**
     * 出资类型
     */
    @TableField("invest_type")
    private String investType;

    /**
     * 资金方出资本金
     */
    @TableField("invest_business_sum")
    private BigDecimal investBusinessSum;

    /**
     * 资金方收益类型
     */
    @TableField("invest_profit_type")
    private String investProfitType;

    /**
     * 资金方名称
     */
    @TableField("invest_name")
    private String investName;

    /**
     * 资金方固定收益率
     */
    @TableField("invest_fix_rate")
    private BigDecimal investFixRate;

    /**
     * 资金方罚息利率
     */
    @TableField("invest_fin_rate")
    private BigDecimal investFinRate;

    /**
     * 分润模式
     */
    @TableField("profit_pay_type")
    private String profitPayType;

    /**
     * 出资比例
     */
    @TableField("principal_ratio")
    private BigDecimal principalRatio;

    // ==================== 资金方余额（当前） ====================

    /**
     * 资金方正常本金余额
     */
    @TableField("invest_normal_balance")
    private BigDecimal investNormalBalance;

    /**
     * 资金方逾期本金余额
     */
    @TableField("invest_overdue_balance")
    private BigDecimal investOverdueBalance;

    /**
     * 资金方计提利息
     */
    @TableField("invest_accrued_interest")
    private BigDecimal investAccruedInterest;

    /**
     * 资金方逾期利息
     */
    @TableField("invest_overdue_interest")
    private BigDecimal investOverdueInterest;

    /**
     * 资金方本金罚息
     */
    @TableField("invest_principal_penalty")
    private BigDecimal investPrincipalPenalty;

    /**
     * 资金方利息罚息
     */
    @TableField("invest_interest_penalty")
    private BigDecimal investInterestPenalty;

    /**
     * 资金方应收费用
     */
    @TableField("invest_fee")
    private BigDecimal investFee;

    // ==================== 资金方余额（昨日） ====================

    /**
     * 昨日资金方正常本金余额
     */
    @TableField("lastday_invest_normal_balance")
    private BigDecimal lastdayInvestNormalBalance;

    /**
     * 昨日资金方逾期本金余额
     */
    @TableField("lastday_invest_overdue_balance")
    private BigDecimal lastdayInvestOverdueBalance;

    /**
     * 昨日资金方计提利息
     */
    @TableField("lastday_invest_accrued_interest")
    private BigDecimal lastdayInvestAccruedInterest;

    /**
     * 昨日资金方逾期利息
     */
    @TableField("lastday_invest_overdue_interest")
    private BigDecimal lastdayInvestOverdueInterest;

    /**
     * 昨日资金方本金罚息
     */
    @TableField("lastday_invest_principal_penalty")
    private BigDecimal lastdayInvestPrincipalPenalty;

    /**
     * 昨日资金方利息罚息
     */
    @TableField("lastday_invest_interest_penalty")
    private BigDecimal lastdayInvestInterestPenalty;

    /**
     * 昨日资金方应收费用
     */
    @TableField("lastday_invest_fee")
    private BigDecimal lastdayInvestFee;

    // ==================== 资金方当日计提 ====================

    /**
     * 资金方当日计提利息
     */
    @TableField("invest_day_accrued_interest")
    private BigDecimal investDayAccruedInterest;

    /**
     * 资金方当日计提罚息
     */
    @TableField("invest_day_principal_penalty")
    private BigDecimal investDayPrincipalPenalty;

    /**
     * 资金方当日计提复息
     */
    @TableField("invest_day_interest_penalty")
    private BigDecimal investDayInterestPenalty;

    // ==================== 资金方出单信息 ====================

    /**
     * 资金方出单正常本金
     */
    @TableField("invest_out_normal_balance")
    private BigDecimal investOutNormalBalance;

    /**
     * 资金方出单计提利息
     */
    @TableField("invest_out_accrued_interest")
    private BigDecimal investOutAccruedInterest;

    // ==================== 其他 ====================

    /**
     * 调用支付附言
     */
    @TableField("postscript")
    private String postscript;

    /**
     * 备用字段
     */
    @TableField("spare")
    private String spare;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
