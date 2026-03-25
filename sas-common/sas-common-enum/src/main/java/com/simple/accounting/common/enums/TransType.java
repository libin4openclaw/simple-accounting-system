package com.simple.accounting.common.enums;

import lombok.Getter;

/**
 * 交易类型枚举
 */
@Getter
public enum TransType {

    DISBURSE(1, "放款"),
    NORMAL_REPAY(2, "正常还款"),
    ADVANCE_REPAY(3, "提前还款"),
    OVERDUE_REPAY(4, "逾期还款"),
    INTEREST_ACCURAL(5, "利息计提");

    private final Integer code;
    private final String desc;

    TransType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TransType getByCode(Integer code) {
        for (TransType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
