-- ============================================
-- Simple Accounting System 数据库初始化脚本
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS sas_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sas_db;

-- ============================================
-- 1. 贷款账户表
-- ============================================
DROP TABLE IF EXISTS loan_account;
CREATE TABLE loan_account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    loan_no VARCHAR(32) NOT NULL UNIQUE COMMENT '贷款账号',
    contract_no VARCHAR(32) COMMENT '合同编号',
    customer_id VARCHAR(32) NOT NULL COMMENT '客户ID',
    customer_name VARCHAR(64) NOT NULL COMMENT '客户姓名',
    product_code VARCHAR(32) COMMENT '产品代码',
    contract_amount DECIMAL(18,2) NOT NULL COMMENT '合同金额',
    actual_amount DECIMAL(18,2) COMMENT '实际放款金额',
    contract_term INT NOT NULL COMMENT '合同期限（月）',
    remaining_term INT COMMENT '剩余期限（月）',
    interest_rate DECIMAL(10,6) NOT NULL COMMENT '年利率',
    overdue_rate DECIMAL(10,6) COMMENT '逾期罚息年利率',
    disbursement_date DATE COMMENT '放款日期',
    first_repay_date DATE COMMENT '首次还款日',
    maturity_date DATE COMMENT '到期日期',
    remaining_principal DECIMAL(18,2) COMMENT '剩余本金',
    paid_principal DECIMAL(18,2) DEFAULT 0 COMMENT '已还本金',
    paid_interest DECIMAL(18,2) DEFAULT 0 COMMENT '已还利息',
    paid_penalty DECIMAL(18,2) DEFAULT 0 COMMENT '已还罚息',
    overdue_days INT DEFAULT 0 COMMENT '逾期天数',
    overdue_amount DECIMAL(18,2) DEFAULT 0 COMMENT '逾期金额',
    loan_status TINYINT DEFAULT 0 COMMENT '贷款状态：0-正常，1-放款中，2-还款中，3-已结清，4-逾期',
    repayment_method TINYINT NOT NULL COMMENT '还款方式：1-等额本息，2-等额本金，3-先息后本',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_customer_id (customer_id),
    INDEX idx_loan_status (loan_status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='贷款账户表';

-- ============================================
-- 2. 还款计划表
-- ============================================
DROP TABLE IF EXISTS payment_schedule;
CREATE TABLE payment_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    loan_no VARCHAR(32) NOT NULL COMMENT '贷款账号',
    period INT NOT NULL COMMENT '期次',
    repay_date DATE NOT NULL COMMENT '计划还款日',
    total_amount DECIMAL(18,2) NOT NULL COMMENT '计划还款总额',
    principal DECIMAL(18,2) NOT NULL COMMENT '计划还本金',
    interest DECIMAL(18,2) NOT NULL COMMENT '计划还利息',
    penalty DECIMAL(18,2) DEFAULT 0 COMMENT '计划罚息',
    paid_principal DECIMAL(18,2) DEFAULT 0 COMMENT '实际还本金',
    paid_interest DECIMAL(18,2) DEFAULT 0 COMMENT '实际还利息',
    paid_penalty DECIMAL(18,2) DEFAULT 0 COMMENT '实际罚息',
    remaining_principal DECIMAL(18,2) COMMENT '剩余本金',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待还，1-部分还款，2-已还清',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_loan_period (loan_no, period),
    INDEX idx_loan_no (loan_no),
    INDEX idx_status (status),
    INDEX idx_repay_date (repay_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='还款计划表';

-- ============================================
-- 3. 交易记录表
-- ============================================
DROP TABLE IF EXISTS transaction_record;
CREATE TABLE transaction_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    trans_no VARCHAR(32) NOT NULL UNIQUE COMMENT '交易流水号',
    loan_no VARCHAR(32) NOT NULL COMMENT '贷款账号',
    trans_type TINYINT NOT NULL COMMENT '交易类型：1-放款，2-正常还款，3-提前还款，4-逾期还款',
    trans_date DATE NOT NULL COMMENT '交易日期',
    trans_amount DECIMAL(18,2) NOT NULL COMMENT '交易金额',
    principal DECIMAL(18,2) DEFAULT 0 COMMENT '本金',
    interest DECIMAL(18,2) DEFAULT 0 COMMENT '利息',
    penalty DECIMAL(18,2) DEFAULT 0 COMMENT '罚息',
    balance_before DECIMAL(18,2) COMMENT '交易前余额',
    balance_after DECIMAL(18,2) COMMENT '交易后余额',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_loan_no (loan_no),
    INDEX idx_trans_type (trans_type),
    INDEX idx_trans_date (trans_date),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录表';

-- ============================================
-- 4. 交易流水表
-- ============================================
DROP TABLE IF EXISTS transaction_flow;
CREATE TABLE transaction_flow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    trans_no VARCHAR(32) NOT NULL COMMENT '交易流水号',
    flow_no VARCHAR(32) NOT NULL UNIQUE COMMENT '流水号',
    flow_type TINYINT NOT NULL COMMENT '流水类型',
    flow_status TINYINT NOT NULL COMMENT '流水状态',
    flow_amount DECIMAL(18,2) NOT NULL COMMENT '流水金额',
    flow_time DATETIME NOT NULL COMMENT '流水时间',
    remark VARCHAR(500) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_trans_no (trans_no),
    INDEX idx_flow_type (flow_type),
    INDEX idx_flow_time (flow_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易流水表';

-- ============================================
-- 测试数据
-- ============================================

-- 插入测试贷款账户
INSERT INTO loan_account (
    loan_no, contract_no, customer_id, customer_name, product_code,
    contract_amount, actual_amount, contract_term, remaining_term,
    interest_rate, overdue_rate, disbursement_date, first_repay_date, maturity_date,
    remaining_principal, loan_status, repayment_method
) VALUES (
    'LOAN202603240001', 'CON202603240001', 'CUST001', '张三', 'PROD001',
    100000.00, 100000.00, 12, 12,
    0.120000, 0.180000, '2026-03-24', '2026-04-24', '2027-03-24',
    100000.00, 0, 1
);

SELECT '数据库初始化完成！' AS message;
