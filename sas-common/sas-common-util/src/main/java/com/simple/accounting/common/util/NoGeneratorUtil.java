package com.simple.accounting.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 业务编号生成工具类
 */
public class NoGeneratorUtil {

    /**
     * 贷款账号前缀
     */
    public static final String LOAN_NO_PREFIX = "LN";

    /**
     * 合同编号前缀
     */
    public static final String CONTRACT_NO_PREFIX = "CT";

    /**
     * 交易流水号前缀
     */
    public static final String TRANS_NO_PREFIX = "TX";

    /**
     * 流水明细编号前缀
     */
    public static final String FLOW_NO_PREFIX = "FL";

    /**
     * 日期时间格式
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 原子计数器
     */
    private static final AtomicLong COUNTER = new AtomicLong(0);

    /**
     * 生成贷款账号
     * @return 贷款账号
     */
    public static String generateLoanNo() {
        return generate(LOAN_NO_PREFIX);
    }

    /**
     * 生成合同编号
     * @return 合同编号
     */
    public static String generateContractNo() {
        return generate(CONTRACT_NO_PREFIX);
    }

    /**
     * 生成交易流水号
     * @return 交易流水号
     */
    public static String generateTransNo() {
        return generate(TRANS_NO_PREFIX);
    }

    /**
     * 生成流水明细编号
     * @return 流水明细编号
     */
    public static String generateFlowNo() {
        return generate(FLOW_NO_PREFIX);
    }

    /**
     * 生成通用业务编号
     * @param prefix 前缀
     * @return 业务编号
     */
    private static String generate(String prefix) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(FORMATTER);
        long seq = COUNTER.incrementAndGet() % 10000;
        return String.format("%s%s%04d", prefix, timestamp, seq);
    }

    /**
     * 生成UUID
     * @return UUID字符串（去除横线）
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
