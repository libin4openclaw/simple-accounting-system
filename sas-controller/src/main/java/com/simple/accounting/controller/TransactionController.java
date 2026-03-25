package com.simple.accounting.controller;

import com.simple.accounting.common.base.Result;
import com.simple.accounting.entity.TransactionRecord;
import com.simple.accounting.service.core.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 交易Controller
 */
@Slf4j
@Api(tags = "交易管理")
@RestController
@RequestMapping("/api/trans")
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    @ApiOperation("放款交易")
    @PostMapping("/disbursement")
    public Result<TransactionRecord> disbursement(
            @ApiParam("贷款账号") @RequestParam String loanNo,
            @ApiParam("放款日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate disbursementDate) {
        log.info("放款交易请求，贷款账号：{}，放款日期：{}", loanNo, disbursementDate);
        TransactionRecord result = transactionService.disbursement(loanNo, disbursementDate);
        return Result.success(result);
    }

    @ApiOperation("正常还款交易")
    @PostMapping("/repayment/normal")
    public Result<TransactionRecord> normalRepayment(
            @ApiParam("贷款账号") @RequestParam String loanNo,
            @ApiParam("还款金额") @RequestParam BigDecimal repayAmount,
            @ApiParam("还款日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate repayDate) {
        log.info("正常还款交易请求，贷款账号：{}，还款金额：{}，还款日期：{}", loanNo, repayAmount, repayDate);
        TransactionRecord result = transactionService.normalRepayment(loanNo, repayAmount, repayDate);
        return Result.success(result);
    }

    @ApiOperation("提前还款交易")
    @PostMapping("/repayment/advance")
    public Result<TransactionRecord> advanceRepayment(
            @ApiParam("贷款账号") @RequestParam String loanNo,
            @ApiParam("还款金额") @RequestParam BigDecimal repayAmount,
            @ApiParam("还款日期") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate repayDate,
            @ApiParam("提前还款违约金比例") @RequestParam BigDecimal prepaymentPenaltyRate) {
        log.info("提前还款交易请求，贷款账号：{}，还款金额：{}，违约金比例：{}", loanNo, repayAmount, prepaymentPenaltyRate);
        TransactionRecord result = transactionService.advanceRepayment(loanNo, repayAmount, repayDate, prepaymentPenaltyRate);
        return Result.success(result);
    }
}
