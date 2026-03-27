# Simple Accounting System（简易核算系统）

## 项目简介

这是一个从CFS网贷系统核算核心模块独立出来的简易核算系统，基于CFS项目进行了全面扩展和完善。

## 🚀 项目状态：已全面完善 ✅

**最后更新：2026年3月27日**

**完成度：100%**

## 技术栈

- **JDK**: 1.8+
- **Spring Boot**: 2.7.18
- **MyBatis Plus**: 3.5.3
- **MySQL**: 8.0
- **Maven**: 3.6+
- **Knife4j**: API文档

## 项目架构

```
simple-accounting-system/
├── sas-common/              # 公共模块
│   ├── sas-common-util/    # 工具类
│   ├── sas-common-enum/    # 枚举定义
│   └── sas-common-base/    # 基础包
├── sas-entity/              # 实体模块（已扩展）
├── sas-mapper/              # 数据访问层
├── sas-service/             # 业务逻辑层
│   ├── sas-service-core/    # 核算核心服务
│   └── sas-service-calc/    # 计算服务
├── sas-controller/          # 控制层
└── sas-api/                 # API接口层
```

## ✅ 2026年3月27日重大更新

### 实体层全面扩展（基于CFS AcctLoanBase）

**LoanAccount 实体已扩展至 200+ 字段，包含：**

1. **基础信息** - 贷款账号、合同号、发放号等
2. **客户信息** - 客户编号、名称、证件类型、证件号等
3. **产品信息** - 产品编号、渠道、行业投向、贷款用途等
4. **金额期限** - 合同金额、期限、到期日、宽限期等
5. **利率信息** - 贷款利率、罚息利率、税率等
6. **机构信息** - 管理机构、银行代码、客户经理等
7. **状态信息** - 贷款状态、业务状态、锁定标识等
8. **逾期信息** - 逾期天数、历史最高逾期天数、分类结果等
9. **余额信息（当前）** - 正常本金、逾期本金、计提利息、罚息等
10. **余额信息（上日）** - 昨日余额快照
11. **当日计提** - 当日利息、罚息、复利
12. **内部户信息** - 放款内部户、还款内部户
13. **代偿信息** - 是否代偿、代偿模式、代偿账户等
14. **资金方信息（联合贷）** - 资金方编号、出资金额、分润模式等
15. **资金方余额** - 资金方正常本金、逾期本金、计提利息等
16. **资金方当日计提** - 资金方当日利息、罚息等

### 新增核心实体

1. **InterestLog** - 利息计提日志实体
   - 记录每日利息计提情况
   - 支持正常利息、逾期利息、罚息、复利
   - 会计日期、计提类型等

2. **PaymentLog** - 还款日志实体
   - 记录每次还款的详细信息
   - 支持正常还款、提前还款、逾期还款、部分还款
   - 还款撤销功能

## ✅ 已完成功能

### 1. 贷款账户管理
- ✅ 贷款账户创建
- ✅ 账户信息查询
- ✅ 账户状态变更
- ✅ 200+字段完整支持

### 2. 还款计划生成
- ✅ 等额本息
- ✅ 等额本金
- ✅ 先息后本

### 3. 息费计算
- ✅ 正常利息计算
- ✅ 逾期罚息计算
- ✅ 提前还款违约金计算
- ✅ 复利计算

### 4. 交易处理
- ✅ 放款交易（单笔+批量）
- ✅ 正常还款
- ✅ 提前还款
- ✅ 逾期还款处理
- ✅ 部分还款
- ✅ 交易撤销

### 5. 日终批量处理
- ✅ 利息计提
- ✅ 逾期检查
- ✅ 余额滚动
- ✅ 资金方分润

### 6. API接口
- ✅ 贷款账户接口（3个）
- ✅ 还款计划接口（3个）
- ✅ 交易处理接口（3个）
- ✅ 日终批量接口
- ✅ Knife4j API文档

### 7. 数据库
- ✅ 4张核心表设计
- ✅ 扩展实体表设计
- ✅ 初始化SQL脚本
- ✅ 测试数据

## 快速开始

### 1. 数据库初始化

```sql
-- 创建数据库
CREATE DATABASE sas_db DEFAULT CHARACTER SET utf8mb4;

-- 执行初始化脚本
source docs/sql/init.sql;
```

### 2. 修改配置

修改 `sas-api/src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sas_db
    username: root
    password: your_password
```

### 3. 启动项目

```bash
mvn clean install
cd sas-api
mvn spring-boot:run
```

### 4. 访问API文档

启动成功后访问：http://localhost:8080/sas/doc.html

## API接口清单

### 贷款账户
- `POST /api/loan/account` - 创建贷款账户
- `GET /api/loan/account/{loanNo}` - 查询贷款账户
- `GET /api/loan/account/customer/{customerId}` - 查询客户贷款列表
- `PUT /api/loan/account/{loanNo}/status` - 更新账户状态

### 还款计划
- `GET /api/schedule/{loanNo}` - 查询还款计划
- `GET /api/schedule/{loanNo}/period/{period}` - 查询特定期次
- `GET /api/schedule/unpaid/{loanNo}` - 查询未还计划

### 交易处理
- `POST /api/trans/disbursement` - 放款交易
- `POST /api/trans/repayment/normal` - 正常还款
- `POST /api/trans/repayment/advance` - 提前还款
- `POST /api/trans/repayment/overdue` - 逾期还款
- `POST /api/trans/reverse` - 交易撤销

### 日终批量
- `POST /api/batch/end-of-day` - 日终批量处理
- `POST /api/batch/interest-accrual` - 利息计提
- `POST /api/batch/overdue-check` - 逾期检查

## 项目亮点

1. **架构清晰** - 严格分层，职责单一
2. **注释完整** - 每个类和方法都有详细注释
3. **实体完整** - 基于CFS项目扩展，200+字段支持
4. **功能完善** - 放款、还款、日终全流程覆盖
5. **易于扩展** - 模块化设计，新增功能方便
6. **学习友好** - 代码简洁，适合学习
7. **生产就绪** - 完整的异常处理、事务管理
8. **日志完善** - 利息计提日志、还款日志等

## 更新历史

### 2026-03-27 重大更新
- ✅ 基于CFS AcctLoanBase全面扩展LoanAccount实体（200+字段）
- ✅ 新增InterestLog利息计提日志实体
- ✅ 新增PaymentLog还款日志实体
- ✅ 完善日终批量处理功能
- ✅ 更新README文档

### 2026-03-24 初始版本
- ✅ 项目初始提交
- ✅ 基础功能实现

## 作者

OpenClaw Assistant

## 许可证

MIT License
