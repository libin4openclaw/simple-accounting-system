package com.simple.accounting.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额计算工具类
 */
public class AmountUtil {

    /**
     * 默认精度（计算时保留10位）
     */
    public static final int CALC_SCALE = 10;

    /**
     * 显示精度（最终结果保留2位）
     */
    public static final int DISPLAY_SCALE = 2;

    /**
     * 默认舍入模式（四舍五入）
     */
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * 加法
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;
        return a.add(b);
    }

    /**
     * 减法
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;
        return a.subtract(b);
    }

    /**
     * 乘法
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return BigDecimal.ZERO;
        }
        return a.multiply(b).setScale(CALC_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 除法
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (a == null || b == null || b.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return a.divide(b, CALC_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 四舍五入保留2位小数
     */
    public static BigDecimal round(BigDecimal amount) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.setScale(DISPLAY_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     * 判断是否大于零
     */
    public static boolean isPositive(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断是否小于等于零
     */
    public static boolean isNonPositive(BigDecimal amount) {
        return !isPositive(amount);
    }

    /**
     * 获取较大值
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * 获取较小值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) <= 0 ? a : b;
    }
}
