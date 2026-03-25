package com.simple.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.accounting.entity.TransactionFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易流水Mapper接口
 */
@Mapper
public interface TransactionFlowMapper extends BaseMapper<TransactionFlow> {

    /**
     * 根据交易流水号查询流水明细列表
     * @param transNo 交易流水号
     * @return 流水明细列表
     */
    List<TransactionFlow> selectByTransNo(@Param("transNo") String transNo);

    /**
     * 根据流水明细编号查询流水明细
     * @param flowNo 流水明细编号
     * @return 流水明细
     */
    TransactionFlow selectByFlowNo(@Param("flowNo") String flowNo);

    /**
     * 根据贷款账号查询流水明细列表
     * @param loanNo 贷款账号
     * @return 流水明细列表
     */
    List<TransactionFlow> selectByLoanNo(@Param("loanNo") String loanNo);

    /**
     * 批量删除交易流水
     * @param transNo 交易流水号
     * @return 影响行数
     */
    int deleteByTransNo(@Param("transNo") String transNo);
}
