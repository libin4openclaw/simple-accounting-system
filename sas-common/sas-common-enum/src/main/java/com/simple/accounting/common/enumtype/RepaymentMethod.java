package com.simple.accounting.common.enumtype;

import lombok.Getter;

/**
 * 还款方式枚举
 * 定义系统支持的所有还款方式
 */
@Getter
public enum RepaymentMethod {

    /**
     * 等额本息
     * 每月还款金额固定，前期利息占比高，后期本金占比高
     * 适合收入稳定的借款人
     */
    EQUAL_PRINCIPAL_INTEREST(1, "等额本息", "每月还款金额固定"),

    /**
     * 等额本金
     * 每月偿还固定本金，利息逐月递减
     * 总利息比等额本息少，但前期还款压力大
     */
    EQUAL_PRINCIPAL(2, "等额本金", "每月偿还固定本金"),

    /**
     * 先息后本
     * 每月只还利息，到期一次性还本
     * 适合短期周转，到期还款压力大
     */
    INTEREST_FIRST(3, "先息后本", "每月还息，到期还本"),

    /**
     * 到期一次性还本付息
     * 到期时一次性偿还本金和利息
     * 适合极短期贷款
     */
    ONE_TIME(4, "到期一次性还本付息", "到期一次性还款");

    /**
     * 还款方式编码
     */
    private final Integer code;

    /**
     * 还款方式名称
     */
    private final String name;

    /**
     * 还款方式描述
     */
    private final String description;

    /**
     * 构造函数
     *
     * @param code        编码
     * @param name        名称
     * @param description 描述
     */
    RepaymentMethod(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * 根据编码获取还款方式
     *
     * @param code 编码
     * @return 还款方式枚举，如果没有找到返回null
     */
    public static RepaymentMethod getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (RepaymentMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 根据名称获取还款方式
     *
     * @param name 名称
     * @return 还款方式枚举，如果没有找到返回null
     */
    public static RepaymentMethod getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        for (RepaymentMethod method : values()) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

}
