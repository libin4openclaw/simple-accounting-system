package com.simple.accounting.common.enumtype;

import lombok.Getter;

/**
 * 贷款账户状态枚举
 * 定义贷款账户的所有可能状态
 */
@Getter
public enum LoanAccountStatus {

    /**
     * 正常
     * 贷款账户创建成功，等待放款
     */
    NORMAL(1, "正常", "账户正常"),

    /**
     * 已放款
     * 贷款已成功放款，正常还款中
     */
    DISBURSED(2, "已放款", "已放款，正常还款中"),

    /**
     * 逾期
     * 贷款已逾期，需要催收
     */
    OVERDUE(3, "逾期", "贷款已逾期"),

    /**
     * 结清
     * 贷款已全部结清
     */
    SETTLED(4, "结清", "贷款已结清"),

    /**
     * 提前结清
     * 贷款已提前结清
     */
    EARLY_SETTLED(5, "提前结清", "贷款已提前结清"),

    /**
     * 核销
     * 贷款已核销
     */
    WRITE_OFF(6, "核销", "贷款已核销"),

    /**
     * 冻结
     * 账户被冻结，无法进行还款等操作
     */
    FROZEN(7, "冻结", "账户已冻结"),

    /**
     * 注销
     * 账户已注销
     */
    CANCELLED(8, "注销", "账户已注销");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 状态描述
     */
    private final String description;

    /**
     * 构造函数
     *
     * @param code        编码
     * @param name        名称
     * @param description 描述
     */
    LoanAccountStatus(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * 根据编码获取账户状态
     *
     * @param code 编码
     * @return 账户状态枚举，如果没有找到返回null
     */
    public static LoanAccountStatus getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (LoanAccountStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据名称获取账户状态
     *
     * @param name 名称
     * @return 账户状态枚举，如果没有找到返回null
     */
    public static LoanAccountStatus getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (LoanAccountStatus status : values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断账户是否可以进行还款操作
     *
     * @return true-可以，false-不可以
     */
    public boolean canRepay() {
        return this == NORMAL || this == DISBURSED || this == OVERDUE;
    }

    /**
     * 判断账户是否已结清（包括正常结清和提前结清）
     *
     * @return true-已结清，false-未结清
     */
    public boolean isSettled() {
        return this == SETTLED || this == EARLY_SETTLED;
    }

}
