package com.simple.accounting.service.calc;

import com.simple.accounting.common.constants.CommonConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 息费计算服务
 * 提供利息、罚息、违约金等计算功能
 */
public class InterestCalcService {

    /**
     * 计算月利率
     * @param annualRate 年利率
     * @return 月利率
     */
    public static BigDecimal calcMonthlyRate(BigDecimal annualRate) {
        if (annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return annualRate.divide(BigDecimal.valueOf(12), CommonConstants.CALC_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算日利率
     * @param annualRate 年利率
     * @return 日利率
     */
    public static BigDecimal calcDailyRate(BigDecimal annualRate) {
        if (annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return annualRate.divide(BigDecimal.valueOf(360), CommonConstants.CALC_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算利息
     * @param principal 本金
     * @param annualRate 年利率
     * @param days 天数
     * @return 利息
     */
    public static BigDecimal calcInterest(BigDecimal principal, BigDecimal annualRate, int days) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || days <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal dailyRate = calcDailyRate(annualRate);
        return principal.multiply(dailyRate).multiply(BigDecimal.valueOf(days))
                .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算月利息
     * @param principal 本金
     * @param annualRate 年利率
     * @return 月利息
     */
    public static BigDecimal calcMonthlyInterest(BigDecimal principal, BigDecimal annualRate) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyRate = calcMonthlyRate(annualRate);
        return principal.multiply(monthlyRate)
                .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算逾期罚息
     * @param overduePrincipal 逾期本金
     * @param overdueRate 逾期罚息年利率
     * @param overdueDays 逾期天数
     * @return 逾期罚息
     */
    public static BigDecimal calcOverduePenalty(BigDecimal overduePrincipal, BigDecimal overdueRate, int overdueDays) {
        if (overduePrincipal == null || overduePrincipal.compareTo(BigDecimal.ZERO) <= 0
                || overdueRate == null || overdueRate.compareTo(BigDecimal.ZERO) <= 0
                || overdueDays <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal dailyRate = calcDailyRate(overdueRate);
        return overduePrincipal.multiply(dailyRate).multiply(BigDecimal.valueOf(overdueDays))
                .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算提前还款违约金
     * @param remainingPrincipal 剩余本金
     * @param penaltyRate 违约金比例
     * @return 提前还款违约金
     */
    public static BigDecimal calcPrepaymentPenalty(BigDecimal remainingPrincipal, BigDecimal penaltyRate) {
        if (remainingPrincipal == null || remainingPrincipal.compareTo(BigDecimal.ZERO) <= 0
                || penaltyRate == null || penaltyRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return remainingPrincipal.multiply(penaltyRate)
                .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算等额本息月供
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @return 月供金额
     */
    public static BigDecimal calcEqualPrincipalInterestPayment(BigDecimal principal, BigDecimal annualRate, int term) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || term <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyRate = calcMonthlyRate(annualRate);

        // 公式：月供 = [本金 × 月利率 × (1+月利率)^期数] / [(1+月利率)^期数 - 1]
        BigDecimal temp = BigDecimal.ONE.add(monthlyRate).pow(term);
        BigDecimal numerator = principal.multiply(monthlyRate).multiply(temp);
        BigDecimal denominator = temp.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 计算等额本金首月月供
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @return 首月月供金额
     */
    public static BigDecimal calcEqualPrincipalFirstPayment(BigDecimal principal, BigDecimal annualRate, int term) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || term <= 0) {
            return BigDecimal.ZERO;
        }

        // 每月应还本金
        BigDecimal monthlyPrincipal = principal.divide(BigDecimal.valueOf(term),
                CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);

        // 首月利息
        BigDecimal firstMonthInterest = calcMonthlyInterest(principal, annualRate);

        return monthlyPrincipal.add(firstMonthInterest);
    }

    /**
     * 计算先息后本首月月供（利息）
     * @param principal 本金
     * @param annualRate 年利率
     * @return 首月月供金额（利息）
     */
    public static BigDecimal calcInterestFirstPayment(BigDecimal principal, BigDecimal annualRate) {
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        return calcMonthlyInterest(principal, annualRate);
    }
}
