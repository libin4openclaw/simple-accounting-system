package com.simple.accounting.common.enums;

import lombok.Getter;

/**
 * 还款方式枚举
 */
@Getter
public enum RepaymentMethod {

    EQUAL_PRINCIPAL_INTEREST(1, "等额本息"),
    EQUAL_PRINCIPAL(2, "等额本金"),
    INTEREST_FIRST(3, "先息后本");

    private final Integer code;
    private final String desc;

    RepaymentMethod(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static RepaymentMethod getByCode(Integer code) {
        for (RepaymentMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }
}
