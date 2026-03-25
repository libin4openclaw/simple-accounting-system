package com.simple.accounting.common.enums;

import lombok.Getter;

/**
 * 贷款账户状态枚举
 */
@Getter
public enum LoanStatus {

    NORMAL(1, "正常"),
    OVERDUE(2, "逾期"),
    SETTLED(3, "结清"),
    FROZEN(4, "冻结");

    private final Integer code;
    private final String desc;

    LoanStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoanStatus getByCode(Integer code) {
        for (LoanStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
