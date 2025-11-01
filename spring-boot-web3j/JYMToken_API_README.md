# JYMToken Spring Boot Backend API 文档

## 项目简介

基于实验 2 的 JYMToken 智能合约，使用 Spring Boot + Web3j 实现的 ERC20 代币后端服务。完全符合区块链课程大作业 2.1 的要求。

## 技术栈

- **Spring Boot 3.2.3** - Java Web 框架
- **Web3j 4.14.0** - 以太坊 Java 库
- **OpenZeppelin 5.0.0** - 智能合约标准库
- **Solidity 0.8.20** - 智能合约语言
- **Gradle 8.13** - 构建工具
- **JDK 21** - Java 开发环境

## JYMToken 代币信息

- **代币名称**: JYMToken
- **代币符号**: JYM
- **小数位数**: 18
- **初始供应量**: 1,000,000 JYM（部署时自动铸造给部署者）
- **基础合约**: ERC20 + ERC20Permit (OpenZeppelin)

## API 接口文档

### 合约管理

#### 1. 部署合约
```bash
POST /api/jym/deploy
```
**说明**: 部署 JYMToken 合约到区块链

**响应示例**:
```json
{
  "contractAddress": "0x1234567890abcdef...",
  "message": "JYMToken contract deployed successfully",
  "initialSupply": "1000000000000000000000000",
  "tokenName": "JYMToken",
  "tokenSymbol": "JYM"
}
```

#### 2. 加载合约
```bash
POST /api/jym/load?address=0x...
```
**参数**: `address` - 合约地址

**响应示例**:
```json
{
  "message": "Contract loaded successfully",
  "contractAddress": "0x..."
}
```

#### 3. 获取合约地址
```bash
GET /api/jym/address
```

**响应示例**:
```json
{
  "contractAddress": "0x..."
}
```

---

### 核心接口（大作业要求）

#### 1. 【必需】铸造代币 - mint
```bash
POST /api/jym/mint?amount=1000
```
**参数**:
- `amount` - 铸造数量（最小单位，1 JYM = 10^18）

**响应示例**:
```json
{
  "transactionHash": "0xabcd...",
  "blockNumber": "12345",
  "gasUsed": "50000",
  "status": "0x1",
  "amount": "1000000000000000000000",
  "message": "Tokens minted to caller address",
  "contractAddress": "0x..."
}
```

#### 2. 【必需】转账 - transfer
```bash
POST /api/jym/transfer?to=0x...&amount=1000
```
**参数**:
- `to` - 接收者地址
- `amount` - 转账数量（最小单位）

#### 3. 【必需】查询余额 - balanceOf
```bash
GET /api/jym/balanceOf?address=0x...
```
**参数**: `address` - 要查询的地址

**响应示例**:
```json
{
  "address": "0x...",
  "balance": "1000000000000000000000",
  "balanceReadable": "1 JYM",
  "contractAddress": "0x..."
}
```

#### 4. 【必需】授权 - approve
```bash
POST /api/jym/approve?spender=0x...&amount=1000
```
**参数**:
- `spender` - 被授权地址
- `amount` - 授权数量

#### 5. 【必需】授权转账 - transferFrom
```bash
POST /api/jym/transferFrom?from=0x...&to=0x...&amount=1000
```
**参数**:
- `from` - 发送者地址
- `to` - 接收者地址
- `amount` - 转账数量

---

### 额外功能

#### 销毁代币 - burn
```bash
POST /api/jym/burn?amount=1000
```

#### 查询授权额度 - allowance
```bash
GET /api/jym/allowance?owner=0x...&spender=0x...
```

#### 获取总供应量 - totalSupply
```bash
GET /api/jym/totalSupply
```

#### 获取代币信息 - info
```bash
GET /api/jym/info
```

**响应示例**:
```json
{
  "name": "JYMToken",
  "symbol": "JYM",
  "decimals": "18",
  "contractAddress": "0x..."
}
```

## 使用示例

### 完整测试流程

```bash
# 1. 启动服务
cd spring-boot-web3j
./gradlew bootRun

# 2. 部署合约
curl -X POST "http://localhost:8080/api/jym/deploy"

# 3. 铸造 100 JYM（100 * 10^18）
curl -X POST "http://localhost:8080/api/jym/mint?amount=100000000000000000000"

# 4. 查询余额
curl -X GET "http://localhost:8080/api/jym/balanceOf?address=0x你的地址"

# 5. 转账
curl -X POST "http://localhost:8080/api/jym/transfer?to=0x接收地址&amount=50000000000000000000"

# 6. 授权
curl -X POST "http://localhost:8080/api/jym/approve?spender=0x被授权地址&amount=200000000000000000000"

# 7. 查询授权额度
curl -X GET "http://localhost:8080/api/jym/allowance?owner=0x你的地址&spender=0x被授权地址"

# 8. 授权转账
curl -X POST "http://localhost:8080/api/jym/transferFrom?from=0x你的地址&to=0x接收地址&amount=100000000000000000000"

# 9. 获取代币信息
curl -X GET "http://localhost:8080/api/jym/info"
```

## 📱 MetaMask 钱包联动配置

### 🔑 步骤一：配置 MetaMask 私钥

#### 1.1 导出 MetaMask 私钥
1. **打开 MetaMask 钱包**
2. **点击右上角三个点** → **账户详情**
3. **点击"导出私钥"**
4. **输入钱包密码**
5. **复制显示的私钥**（格式：0x开头）

#### 1.2 修改配置文件

编辑 `src/main/resources/application.properties`:

```properties
# 服务端口
server.port=8080

# Web3j 配置
web3j.client-address=https://rpc-testnet.potos.hk   # POTOS 测试网
web3j.private-key=0x你的MetaMask私钥                  # 从 MetaMask 获取
web3j.gas-price=20000000000                        # Gas 价格
web3j.gas-limit=6721975                            # Gas 限制
```

**重要修改**：将第6行的 `web3j.private-key` 替换为你从 MetaMask 导出的真实私钥。

### 💰 步骤二：获取测试网 ETH

#### 2.1 POTOS 测试网水龙头
- **访问**：https://faucet.potos.hk/
- **输入**：你的 MetaMask 钱包地址
- **操作**：申请测试网 ETH
- **等待**：确认 ETH 到账

#### 2.2 在 MetaMask 中添加 POTOS 测试网
1. **MetaMask** → **网络** → **添加网络**
2. **网络名称**：POTOS Testnet
3. **RPC URL**：https://rpc-testnet.potos.hk
4. **Chain ID**：60600
5. **货币符号**：ETH

### 🚀 步骤三：验证钱包联动

#### 3.1 启动服务
```bash
cd spring-boot-web3j
./gradlew bootRun
```

#### 3.2 验证配置地址
在服务启动日志中应该显示你的 MetaMask 地址，证明钱包联动成功。

#### 3.3 在 MetaMask 中添加 JYMToken
1. **部署合约后**：MetaMask → **资产** → **导入代币**
2. **代币合约地址**：API 返回的 contractAddress
3. **代币符号**：JYM
4. **小数位数**：18

### ✅ 验证钱包联动成功

**成功标志**：
1. ✅ **服务启动**：无私钥相关错误
2. ✅ **部署地址**：合约部署到你的 MetaMask 地址
3. ✅ **代币余额**：JYMToken 显示在 MetaMask 资产中
4. ✅ **交易记录**：可在 MetaMask 中查看所有 JYMToken 交易

## 🚀 钱包联动快速开始

### 准备工作
1. **MetaMask 钱包**（已安装）
2. **获取私钥**（按上述步骤导出）
3. **测试网 ETH**（从水龙头获取）

### 1. 配置钱包联动
编辑 `src/main/resources/application.properties`：
```properties
web3j.private-key=0x你的MetaMask私钥
```

### 2. 启动后端服务
```bash
cd spring-boot-web3j
./gradlew bootRun
```

### 3. 部署合约到你的钱包
```bash
# 部署 JYMToken 合约
curl -X POST "http://localhost:8080/api/jym/deploy"
```

### 4. 在 MetaMask 中添加代币
1. **MetaMask** → **资产** → **导入代币**
2. **代币合约地址**：从部署响应中复制
3. **代币符号**：JYM
4. **小数位数**：18

### 5. 验证钱包联动
```bash
# 查询你的余额（应该显示 1,000,000 JYM）
curl -X GET "http://localhost:8080/api/jym/balanceOf?address=你的MetaMask地址"
```

### 6. 测试转账功能
```bash
# 转账到另一个地址
curl -X POST "http://localhost:8080/api/jym/transfer?to=0x接收地址&amount=100000000000000000000"
```

## ⚠️ 重要提醒

### 私钥安全
- 仅在测试环境使用
- 不要在公共网络传输私钥
- 完成作业后可更换为测试专用私钥

### 大作业要求满足情况
- ✅ **ERC20 标准合约**：实现所有必需接口
- ✅ **钱包联动**：合约部署到个人 MetaMask 地址
- ✅ **API 调用**：通过 REST API 与钱包交互
- ✅ **代币验证**：可在 MetaMask 中查看代币余额和交易

## 测试网信息

- **网络**: POTOS Testnet
- **RPC URL**: https://rpc-testnet.potos.hk
- **Chain ID**: 60600
- **区块浏览器**: https://scan-testnet.potos.hk
- **获取测试币**: 从测试网水龙头获取

## 重要说明

### 单位换算
- 代币小数位: 18
- 1 JYM = 10^18 最小单位
- API 使用最小单位（wei）进行计算

### 交易状态
- `status: "0x1"` - 交易成功
- `status: "0x0"` - 交易失败

### 注意事项
1. 确保账户有足够的 ETH 支付 gas 费用
2. 所有地址必须是有效的以太坊地址格式
3. 交易需要一些时间确认，请耐心等待
4. 检查 POTOS 测试网是否正常运行

## 项目结构

```
spring-boot-web3j/
├── src/main/java/com/wetech/demo/web3j/
│   ├── Application.java              # 应用入口
│   ├── config/
│   │   └── Web3jConfig.java          # Web3j 配置
│   ├── contracts/
│   │   └── jymtoken/
│   │       └── JYMToken.java         # JYMToken Java 封装
│   ├── controller/
│   │   └── JYMTokenController.java   # REST API 控制器
│   └── service/
│       └── JYMTokenService.java      # 业务逻辑服务
├── src/main/resources/
│   ├── application.properties        # 配置文件
│   └── contracts/
│       └── JYMToken.sol             # 原始智能合约
├── package.json                     # Node.js 依赖
└── build.gradle                     # 构建配置
```

## 开发日志

### 2025-10-19 - 完成大作业 2.1 要求
✅ **已完成的工作**:
1. 基于 Spring-boot-web3j 示例代码进行修改
2. 集成实验 2 的 JYMToken 智能合约
3. 实现 5 个必需的 REST 接口:
   - ✅ mint - 铸造代币
   - ✅ transfer - 转账
   - ✅ balanceOf - 查询余额
   - ✅ approve - 授权
   - ✅ transferFrom - 授权转账
4. 项目编译成功，服务正常启动
5. 🆕 **新增功能**: 完整的 MetaMask 钱包联动配置

**技术实现**:
- 使用 OpenZeppelin 标准库确保安全性
- Web3j 实现以太坊交互
- 异步 API 提高性能
- 完整的错误处理和日志记录
- 🆕 **钱包联动**: 支持个人 MetaMask 私钥配置

**测试网准备**:
- 已配置 POTOS 测试网 RPC
- 支持合约部署和交易调用
- 提供完整的 API 测试用例
- 🆕 **钱包验证**: 可在 MetaMask 中查看代币和交易记录

**大作业钱包联动要求**:
- ✅ 合约部署到个人钱包地址
- ✅ 代币铸造到个人钱包
- ✅ API 调用与个人钱包交互
- ✅ MetaMask 中可验证所有操作

## 联系信息

- **项目**: 区块链技术与应用课程大作业
- **版本**: 1.0.0
- **完成日期**: 2025-10-19