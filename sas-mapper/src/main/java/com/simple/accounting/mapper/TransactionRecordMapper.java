package com.simple.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simple.accounting.entity.TransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易记录Mapper接口
 */
@Mapper
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {

    /**
     * 根据交易流水号查询交易记录
     * @param transNo 交易流水号
     * @return 交易记录
     */
    TransactionRecord selectByTransNo(@Param("transNo") String transNo);

    /**
     * 根据贷款账号查询交易记录列表
     * @param loanNo 贷款账号
     * @return 交易记录列表
     */
    List<TransactionRecord> selectByLoanNo(@Param("loanNo") String loanNo);

    /**
     * 根据贷款账号和交易类型查询交易记录列表
     * @param loanNo 贷款账号
     * @param transType 交易类型
     * @return 交易记录列表
     */
    List<TransactionRecord> selectByLoanNoAndTransType(@Param("loanNo") String loanNo, @Param("transType") Integer transType);

    /**
     * 更新交易状态
     * @param transNo 交易流水号
     * @param transStatus 交易状态
     * @return 影响行数
     */
    int updateTransStatus(@Param("transNo") String transNo, @Param("transStatus") Integer transStatus);
}
