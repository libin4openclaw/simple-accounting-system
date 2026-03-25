package com.simple.accounting.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易流水实体类
 * 记录每一笔交易的资金流向明细
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_flow")
public class TransactionFlow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号（关联transaction_record）
     */
    @TableField("trans_no")
    private String transNo;

    /**
     * 流水明细编号
     */
    @TableField("flow_no")
    private String flowNo;

    /**
     * 流水类型
     */
    @TableField("flow_type")
    private Integer flowType;

    /**
     * 流水状态
     */
    @TableField("flow_status")
    private Integer flowStatus;

    /**
     * 流水金额
     */
    @TableField("flow_amount")
    private BigDecimal flowAmount;

    /**
     * 流水时间
     */
    @TableField("flow_time")
    private LocalDateTime flowTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
