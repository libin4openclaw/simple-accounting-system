package com.simple.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.accounting.entity.LoanAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 贷款账户Mapper接口
 */
@Mapper
public interface LoanAccountMapper extends BaseMapper<LoanAccount> {

    /**
     * 根据贷款账号查询贷款账户
     * @param loanNo 贷款账号
     * @return 贷款账户信息
     */
    LoanAccount selectByLoanNo(@Param("loanNo") String loanNo);

    /**
     * 更新贷款账户状态
     * @param loanNo 贷款账号
     * @param loanStatus 贷款状态
     * @return 影响行数
     */
    int updateLoanStatus(@Param("loanNo") String loanNo, @Param("loanStatus") Integer loanStatus);

    /**
     * 更新剩余本金
     * @param loanNo 贷款账号
     * @param remainingPrincipal 剩余本金
     * @return 影响行数
     */
    int updateRemainingPrincipal(@Param("loanNo") String loanNo, @Param("remainingPrincipal") java.math.BigDecimal remainingPrincipal);
}
