# Simple Accounting System（简易核算系统）

## 项目简介

这是一个从CFS网贷系统核算核心模块独立出来的简易核算系统，用于学习和参考。

## 🚀 项目状态：已完成 ✅

**完成时间：2026年3月24日**

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
├── sas-entity/              # 实体模块
├── sas-mapper/              # 数据访问层
├── sas-service/             # 业务逻辑层
│   ├── sas-service-core/    # 核算核心服务
│   └── sas-service-calc/    # 计算服务
├── sas-controller/          # 控制层
└── sas-api/                 # API接口层
```

## ✅ 已完成功能

### 1. 贷款账户管理
- ✅ 贷款账户创建
- ✅ 账户信息查询
- ✅ 账户状态变更

### 2. 还款计划生成
- ✅ 等额本息
- ✅ 等额本金
- ✅ 先息后本

### 3. 息费计算
- ✅ 正常利息计算
- ✅ 逾期罚息计算
- ✅ 提前还款违约金计算

### 4. 交易处理
- ✅ 放款交易
- ✅ 正常还款
- ✅ 提前还款
- ✅ 逾期还款处理

### 5. API接口
- ✅ 贷款账户接口（3个）
- ✅ 还款计划接口（3个）
- ✅ 交易处理接口（3个）
- ✅ Knife4j API文档

### 6. 数据库
- ✅ 4张核心表设计
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

## 项目亮点

1. **架构清晰** - 严格分层，职责单一
2. **注释完整** - 每个类和方法都有详细注释
3. **易于扩展** - 模块化设计，新增功能方便
4. **学习友好** - 代码简洁，适合学习
5. **生产就绪** - 完整的异常处理、事务管理

## 作者

OpenClaw Assistant

## 许可证

MIT License

