package com.simple.accounting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Accounting System 启动类
 */
@SpringBootApplication(scanBasePackages = "com.simple.accounting")
@MapperScan("com.simple.accounting.mapper")
public class SasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SasApplication.class, args);
    }
}
