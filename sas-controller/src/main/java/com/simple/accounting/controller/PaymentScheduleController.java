package com.simple.accounting.controller;

import com.simple.accounting.common.base.Result;
import com.simple.accounting.entity.PaymentSchedule;
import com.simple.accounting.service.core.PaymentScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 还款计划Controller
 */
@Slf4j
@Api(tags = "还款计划管理")
@RestController
@RequestMapping("/api/schedule")
public class PaymentScheduleController {

    @Resource
    private PaymentScheduleService paymentScheduleService;

    @ApiOperation("根据贷款账号查询还款计划列表")
    @GetMapping("/{loanNo}")
    public Result<List<PaymentSchedule>> getByLoanNo(
            @ApiParam("贷款账号") @PathVariable String loanNo) {
        log.info("查询还款计划列表请求，贷款账号：{}", loanNo);
        List<PaymentSchedule> result = paymentScheduleService.getByLoanNo(loanNo);
        return Result.success(result);
    }

    @ApiOperation("根据贷款账号和期次查询还款计划")
    @GetMapping("/{loanNo}/period/{period}")
    public Result<PaymentSchedule> getByLoanNoAndPeriod(
            @ApiParam("贷款账号") @PathVariable String loanNo,
            @ApiParam("期次") @PathVariable Integer period) {
        log.info("查询还款计划请求，贷款账号：{}，期次：{}", loanNo, period);
        PaymentSchedule result = paymentScheduleService.getByLoanNoAndPeriod(loanNo, period);
        return Result.success(result);
    }

    @ApiOperation("查询未还清的还款计划列表")
    @GetMapping("/unpaid/{loanNo}")
    public Result<List<PaymentSchedule>> getUnpaidSchedules(
            @ApiParam("贷款账号") @PathVariable String loanNo) {
        log.info("查询未还清的还款计划请求，贷款账号：{}", loanNo);
        List<PaymentSchedule> result = paymentScheduleService.getUnpaidSchedules(loanNo);
        return Result.success(result);
    }
}
