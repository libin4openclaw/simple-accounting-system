package com.simple.accounting.controller;

import com.simple.accounting.common.base.Result;
import com.simple.accounting.entity.LoanAccount;
import com.simple.accounting.service.core.LoanAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 贷款账户Controller
 */
@Slf4j
@Api(tags = "贷款账户管理")
@RestController
@RequestMapping("/api/loan/account")
public class LoanAccountController {

    @Resource
    private LoanAccountService loanAccountService;

    @ApiOperation("创建贷款账户")
    @PostMapping
    public Result<LoanAccount> createLoanAccount(@RequestBody LoanAccount loanAccount) {
        log.info("创建贷款账户请求，客户ID：{}", loanAccount.getCustomerId());
        LoanAccount result = loanAccountService.createLoanAccount(loanAccount);
        return Result.success(result);
    }

    @ApiOperation("根据贷款账号查询贷款账户")
    @GetMapping("/{loanNo}")
    public Result<LoanAccount> getByLoanNo(
            @ApiParam("贷款账号") @PathVariable String loanNo) {
        log.info("查询贷款账户请求，贷款账号：{}", loanNo);
        LoanAccount result = loanAccountService.getByLoanNo(loanNo);
        return Result.success(result);
    }

    @ApiOperation("根据客户ID查询贷款账户列表")
    @GetMapping("/customer/{customerId}")
    public Result<List<LoanAccount>> getByCustomerId(
            @ApiParam("客户ID") @PathVariable String customerId) {
        log.info("查询客户贷款账户列表请求，客户ID：{}", customerId);
        List<LoanAccount> result = loanAccountService.getByCustomerId(customerId);
        return Result.success(result);
    }

    @ApiOperation("更新贷款账户状态")
    @PutMapping("/{loanNo}/status")
    public Result<Boolean> updateLoanStatus(
            @ApiParam("贷款账号") @PathVariable String loanNo,
            @ApiParam("贷款状态") @RequestParam Integer loanStatus) {
        log.info("更新贷款账户状态请求，贷款账号：{}，状态：{}", loanNo, loanStatus);
        boolean result = loanAccountService.updateLoanStatus(loanNo, loanStatus);
        return Result.success(result);
    }
}
