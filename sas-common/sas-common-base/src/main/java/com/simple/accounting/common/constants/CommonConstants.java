package com.simple.accounting.common.constants;

import java.math.BigDecimal;

/**
 * 通用常量定义类
 */
public class CommonConstants {

    /**
     * 私有构造函数，防止实例化
     */
    private CommonConstants() {
    }

    /**
     * 数字零
     */
    public static final Integer ZERO = 0;

    /**
     * 数字一
     */
    public static final Integer ONE = 1;

    /**
     * 数字二
     */
    public static final Integer TWO = 2;

    /**
     * 数字三
     */
    public static final Integer THREE = 3;

    /**
     * 数字十
     */
    public static final Integer TEN = 10;

    /**
     * 数字一百
     */
    public static final Integer HUNDRED = 100;

    /**
     * 数字三百六十五
     */
    public static final Integer DAYS_OF_YEAR = 365;

    /**
     * 数字三百六十
     */
    public static final Integer DAYS_OF_YEAR_360 = 360;

    /**
     * BigDecimal 零
     */
    public static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.ZERO;

    /**
     * BigDecimal 一百
     */
    public static final BigDecimal BIG_DECIMAL_HUNDRED = new BigDecimal("100");

    /**
     * 成功状态码
     */
    public static final Integer SUCCESS_CODE = 200;

    /**
     * 失败状态码
     */
    public static final Integer ERROR_CODE = 500;

    /**
     * 成功消息
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 失败消息
     */
    public static final String ERROR_MESSAGE = "操作失败";

    /**
     * 默认字符编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认精度（小数点后2位）
     */
    public static final Integer DEFAULT_SCALE = 2;

    /**
     * 金额精度（小数点后6位）
     */
    public static final Integer AMOUNT_SCALE = 6;

    /**
     * 计算精度（小数点后10位）
     */
    public static final Integer CALC_SCALE = 10;
}
