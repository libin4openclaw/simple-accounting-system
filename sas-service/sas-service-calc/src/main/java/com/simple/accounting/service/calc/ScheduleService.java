package com.simple.accounting.service.calc;

import com.simple.accounting.common.constants.BusinessConstants;
import com.simple.accounting.common.constants.CommonConstants;
import com.simple.accounting.entity.PaymentSchedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 还款计划生成服务
 * 提供等额本息、等额本金、先息后本等还款计划的生成功能
 */
public class ScheduleService {

    /**
     * 生成等额本息还款计划
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @param disbursementDate 放款日期
     * @param firstRepayDate 首次还款日
     * @return 还款计划列表
     */
    public static List<PaymentSchedule> generateEqualPrincipalInterestSchedule(
            BigDecimal principal, BigDecimal annualRate, int term,
            LocalDate disbursementDate, LocalDate firstRepayDate) {

        List<PaymentSchedule> scheduleList = new ArrayList<>();

        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || term <= 0) {
            return scheduleList;
        }

        // 月利率
        BigDecimal monthlyRate = InterestCalcService.calcMonthlyRate(annualRate);

        // 计算每月还款额：月供 = [本金 × 月利率 × (1+月利率)^期数] / [(1+月利率)^期数 - 1]
        BigDecimal temp = BigDecimal.ONE.add(monthlyRate).pow(term);
        BigDecimal monthlyPayment = principal.multiply(monthlyRate).multiply(temp)
                .divide(temp.subtract(BigDecimal.ONE), CommonConstants.AMOUNT_SCALE, RoundingMode.HALF_UP);

        BigDecimal remainingPrincipal = principal;
        LocalDate currentRepayDate = firstRepayDate;

        for (int i = 1; i <= term; i++) {
            PaymentSchedule schedule = new PaymentSchedule();

            // 计算当期利息：剩余本金 × 月利率
            BigDecimal interest = remainingPrincipal.multiply(monthlyRate)
                    .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);

            // 计算当期本金：月供 - 利息（最后一期调整）
            BigDecimal principalPart;
            if (i == term) {
                // 最后一期，本金 = 剩余本金
                principalPart = remainingPrincipal;
                // 最后一期月供 = 剩余本金 + 利息
                monthlyPayment = principalPart.add(interest);
            } else {
                principalPart = monthlyPayment.subtract(interest)
                        .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);
            }

            // 设置还款计划信息
            schedule.setPeriod(i);
            schedule.setRepayDate(currentRepayDate);
            schedule.setTotalAmount(monthlyPayment);
            schedule.setPrincipal(principalPart);
            schedule.setInterest(interest);
            schedule.setPenalty(BigDecimal.ZERO);
            schedule.setRemainingPrincipal(remainingPrincipal.subtract(principalPart));
            schedule.setStatus(BusinessConstants.SCHEDULE_STATUS_PENDING);

            scheduleList.add(schedule);

            // 更新剩余本金
            remainingPrincipal = remainingPrincipal.subtract(principalPart);

            // 计算下一期还款日（每月同一天）
            currentRepayDate = currentRepayDate.plusMonths(1);
        }

        return scheduleList;
    }

    /**
     * 生成等额本金还款计划
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @param disbursementDate 放款日期
     * @param firstRepayDate 首次还款日
     * @return 还款计划列表
     */
    public static List<PaymentSchedule> generateEqualPrincipalSchedule(
            BigDecimal principal, BigDecimal annualRate, int term,
            LocalDate disbursementDate, LocalDate firstRepayDate) {

        List<PaymentSchedule> scheduleList = new ArrayList<>();

        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || term <= 0) {
            return scheduleList;
        }

        // 月利率
        BigDecimal monthlyRate = InterestCalcService.calcMonthlyRate(annualRate);

        // 每月应还本金：总本金 / 期数
        BigDecimal monthlyPrincipal = principal.divide(new BigDecimal(term), CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);

        BigDecimal remainingPrincipal = principal;
        LocalDate currentRepayDate = firstRepayDate;

        for (int i = 1; i <= term; i++) {
            PaymentSchedule schedule = new PaymentSchedule();

            // 当期本金（最后一期调整）
            BigDecimal principalPart;
            if (i == term) {
                principalPart = remainingPrincipal;
            } else {
                principalPart = monthlyPrincipal;
            }

            // 当期利息：剩余本金 × 月利率
            BigDecimal interest = remainingPrincipal.multiply(monthlyRate)
                    .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);

            // 当期总金额：本金 + 利息
            BigDecimal totalAmount = principalPart.add(interest);

            // 设置还款计划信息
            schedule.setPeriod(i);
            schedule.setRepayDate(currentRepayDate);
            schedule.setTotalAmount(totalAmount);
            schedule.setPrincipal(principalPart);
            schedule.setInterest(interest);
            schedule.setPenalty(BigDecimal.ZERO);
            schedule.setRemainingPrincipal(remainingPrincipal.subtract(principalPart));
            schedule.setStatus(BusinessConstants.SCHEDULE_STATUS_PENDING);

            scheduleList.add(schedule);

            // 更新剩余本金
            remainingPrincipal = remainingPrincipal.subtract(principalPart);

            // 计算下一期还款日（每月同一天）
            currentRepayDate = currentRepayDate.plusMonths(1);
        }

        return scheduleList;
    }

    /**
     * 生成先息后本还款计划
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @param disbursementDate 放款日期
     * @param firstRepayDate 首次还款日
     * @return 还款计划列表
     */
    public static List<PaymentSchedule> generateInterestFirstSchedule(
            BigDecimal principal, BigDecimal annualRate, int term,
            LocalDate disbursementDate, LocalDate firstRepayDate) {

        List<PaymentSchedule> scheduleList = new ArrayList<>();

        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0
                || annualRate == null || annualRate.compareTo(BigDecimal.ZERO) <= 0
                || term <= 0) {
            return scheduleList;
        }

        // 月利率
        BigDecimal monthlyRate = InterestCalcService.calcMonthlyRate(annualRate);

        // 每月利息
        BigDecimal monthlyInterest = principal.multiply(monthlyRate)
                .setScale(CommonConstants.DEFAULT_SCALE, RoundingMode.HALF_UP);

        BigDecimal remainingPrincipal = principal;
        LocalDate currentRepayDate = firstRepayDate;

        for (int i = 1; i <= term; i++) {
            PaymentSchedule schedule = new PaymentSchedule();

            BigDecimal principalPart;
            BigDecimal totalAmount;

            if (i == term) {
                // 最后一期：还本金 + 利息
                principalPart = principal;
                totalAmount = principalPart.add(monthlyInterest);
            } else {
                // 前几期：只还利息
                principalPart = BigDecimal.ZERO;
                totalAmount = monthlyInterest;
            }

            // 设置还款计划信息
            schedule.setPeriod(i);
            schedule.setRepayDate(currentRepayDate);
            schedule.setTotalAmount(totalAmount);
            schedule.setPrincipal(principalPart);
            schedule.setInterest(monthlyInterest);
            schedule.setPenalty(BigDecimal.ZERO);
            schedule.setRemainingPrincipal(remainingPrincipal.subtract(principalPart));
            schedule.setStatus(BusinessConstants.SCHEDULE_STATUS_PENDING);

            scheduleList.add(schedule);

            // 更新剩余本金
            remainingPrincipal = remainingPrincipal.subtract(principalPart);

            // 计算下一期还款日（每月同一天）
            currentRepayDate = currentRepayDate.plusMonths(1);
        }

        return scheduleList;
    }

    /**
     * 根据还款方式生成还款计划
     * @param principal 本金
     * @param annualRate 年利率
     * @param term 期数（月）
     * @param repaymentMethod 还款方式
     * @param disbursementDate 放款日期
     * @param firstRepayDate 首次还款日
     * @return 还款计划列表
     */
    public static List<PaymentSchedule> generateSchedule(
            BigDecimal principal, BigDecimal annualRate, int term,
            Integer repaymentMethod, LocalDate disbursementDate, LocalDate firstRepayDate) {

        if (repaymentMethod == null) {
            return new ArrayList<>();
        }

        switch (repaymentMethod) {
            case BusinessConstants.REPAYMENT_METHOD_EQUAL_PRINCIPAL_INTEREST:
                return generateEqualPrincipalInterestSchedule(principal, annualRate, term, disbursementDate, firstRepayDate);
            case BusinessConstants.REPAYMENT_METHOD_EQUAL_PRINCIPAL:
                return generateEqualPrincipalSchedule(principal, annualRate, term, disbursementDate, firstRepayDate);
            case BusinessConstants.REPAYMENT_METHOD_INTEREST_FIRST:
                return generateInterestFirstSchedule(principal, annualRate, term, disbursementDate, firstRepayDate);
            default:
                return new ArrayList<>();
        }
    }
}
