package com.simple.accounting.common.constants;

/**
 * 业务常量定义类
 */
public class BusinessConstants {

    /**
     * 私有构造函数，防止实例化
     */
    private BusinessConstants() {
    }

    /**
     * 还款方式：1-等额本息
     */
    public static final Integer REPAYMENT_METHOD_EQUAL_PRINCIPAL_INTEREST = 1;

    /**
     * 还款方式：2-等额本金
     */
    public static final Integer REPAYMENT_METHOD_EQUAL_PRINCIPAL = 2;

    /**
     * 还款方式：3-先息后本
     */
    public static final Integer REPAYMENT_METHOD_INTEREST_FIRST = 3;

    /**
     * 贷款状态：0-正常
     */
    public static final Integer LOAN_STATUS_NORMAL = 0;

    /**
     * 贷款状态：1-放款中
     */
    public static final Integer LOAN_STATUS_ACTIVE = 1;

    /**
     * 贷款状态：2-还款中
     */
    public static final Integer LOAN_STATUS_REPAYING = 2;

    /**
     * 贷款状态：3-已结清
     */
    public static final Integer LOAN_STATUS_SETTLED = 3;

    /**
     * 贷款状态：4-逾期
     */
    public static final Integer LOAN_STATUS_OVERDUE = 4;

    /**
     * 还款计划状态：0-待还
     */
    public static final Integer SCHEDULE_STATUS_PENDING = 0;

    /**
     * 还款计划状态：1-部分还款
     */
    public static final Integer SCHEDULE_STATUS_PARTIAL_PAID = 1;

    /**
     * 还款计划状态：2-已还清
     */
    public static final Integer SCHEDULE_STATUS_PAID = 2;

    /**
     * 交易类型：1-放款
     */
    public static final Integer TRANS_TYPE_DISBURSEMENT = 1;

    /**
     * 交易类型：2-正常还款
     */
    public static final Integer TRANS_TYPE_NORMAL_REPAYMENT = 2;

    /**
     * 交易类型：3-提前还款
     */
    public static final Integer TRANS_TYPE_ADVANCE_REPAYMENT = 3;

    /**
     * 交易类型：4-逾期还款
     */
    public static final Integer TRANS_TYPE_OVERDUE_REPAYMENT = 4;
}
