package com.simple.accounting.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化器
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    /**
     * 日期时间格式化器
     */
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);

    /**
     * 日期转字符串
     * @param date 日期
     * @return 日期字符串
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    /**
     * 日期时间转字符串
     * @param datetime 日期时间
     * @return 日期时间字符串
     */
    public static String formatDateTime(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        }
        return datetime.format(DATETIME_FORMATTER);
    }

    /**
     * 字符串转日期
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 字符串转日期时间
     * @param datetimeStr 日期时间字符串
     * @return 日期时间
     */
    public static LocalDateTime parseDateTime(String datetimeStr) {
        if (datetimeStr == null || datetimeStr.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(datetimeStr, DATETIME_FORMATTER);
    }

    /**
     * 计算两个日期之间的天数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 天数
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 计算两个日期之间的月数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 月数
     */
    public static int monthsBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return Period.between(startDate, endDate).getMonths();
    }

    /**
     * 判断日期1是否在日期2之后
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否在之后
     */
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.isAfter(date2);
    }

    /**
     * 判断日期1是否在日期2之前
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否在之前
     */
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.isBefore(date2);
    }

    /**
     * 获取当前日期
     * @return 当前日期
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期时间
     * @return 当前日期时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 在日期上增加天数
     * @param date 日期
     * @param days 天数
     * @return 增加后的日期
     */
    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }

    /**
     * 在日期上增加月数
     * @param date 日期
     * @param months 月数
     * @return 增加后的日期
     */
    public static LocalDate addMonths(LocalDate date, int months) {
        if (date == null) {
            return null;
        }
        return date.plusMonths(months);
    }

    /**
     * 判断日期是否在指定范围内
     * @param date 要判断的日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 是否在范围内
     */
    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return (date.isEqual(startDate) || date.isAfter(startDate)) 
            && (date.isEqual(endDate) || date.isBefore(endDate));
    }
}
