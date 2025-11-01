# JYMToken 钱包联动部署指南

## 📋 概述
本指南说明如何将JYMToken合约部署到Potos测试网，并与你的MetaMask钱包联动，实现合约部署到你的钱包地址。

## 🔑 步骤一：配置钱包私钥

### 1.1 导出MetaMask私钥
1. **打开MetaMask钱包**
2. **点击右上角三个点** → **账户详情**
3. **点击"导出私钥"**
4. **输入钱包密码**
5. **复制显示的私钥**（格式：0x开头）

### 1.2 配置项目私钥

**方法A：环境变量（推荐）**
```bash
cd my-first-dapp
export __RUNTIME_DEPLOYER_PRIVATE_KEY=0x你的私钥
```

**方法B：修改配置文件**
编辑 `packages/hardhat/hardhat.config.ts` 第21行：
```typescript
const deployerPrivateKey = "0x你的私钥";  // 替换这行
```

## 💰 步骤二：获取测试网ETH

### 2.1 Potos测试网水龙头
- **访问**：https://faucet.potos.hk/
- **输入**：你的MetaMask钱包地址
- **操作**：申请测试网ETH
- **等待**：确认ETH到账

### 2.2 检查余额
在MetaMask中切换到Potos测试网，确认有足够POT支付部署费用。

## 🚀 步骤三：部署合约

### 3.1 执行部署命令
```bash
cd my-first-dapp
yarn hardhat:deploy --tags ERC20JYM202330550601
```

### 3.2 部署成功验证
控制台应显示类似信息：
```
🚀 开始部署 JYMToken 合约...
✅ JYMToken 合约部署成功！
📍 合约地址: 0x1234...abcd
🔗 交易哈希: 0xabcd...1234
📊 部署者余额: 1000000 JYM
```

## 📱 步骤四：在MetaMask中添加代币

### 4.1 添加代币操作
1. **MetaMask** → **资产** → **导入代币**
2. **代币合约地址**：复制部署成功后的合约地址
3. **代币符号**：JYM
4. **小数位数**：18
5. **点击"添加自定义代币"**

### 4.2 验证代币余额
添加成功后，你应该能在MetaMask中看到：
- **JYM代币余额**：1,000,000 JYM
- **部署地址**：你的MetaMask钱包地址

## 📍 重要文件位置

- **部署脚本**：`packages/hardhat/deploy/00_deploy_jym_token.ts`
- **合约代码**：`packages/hardhat/contracts/JYMToken.sol`
- **配置文件**：`packages/hardhat/hardhat.config.ts`
- **部署信息**：`packages/hardhat/deployments/potos_testnet/JYMToken.json`

## ✅ 验证钱包联动成功

### 成功标志：
1. ✅ **部署地址** = 你的MetaMask地址
2. ✅ **代币余额** = 1,000,000 JYM出现在MetaMask中
3. ✅ **合约地址**可以添加到MetaMask显示余额
4. ✅ **可以在区块浏览器**查询部署交易

### 区块浏览器验证：
- **访问**：https://potos-scan.hk/
- **输入**：合约地址或交易哈希
- **验证**：部署成功，初始铸造1M JYM

## ⚠️ 注意事项

1. **私钥安全**：妥善保管私钥，不要泄露
2. **测试网专用**：仅使用测试网钱包和地址
3. **网络切换**：确保MetaMask切换到Potos测试网
4. **Gas费用**：确保有足够测试网ETH支付部署费用

## 🎯 大作业要求完成情况

- ✅ **ERC20标准合约**：实现所有必需接口
- ✅ **标签规范**：ERC20JYM202330550601
- ✅ **钱包联动**：合约部署到个人钱包地址
- ✅ **代币铸造**：初始铸造1,000,000 JYM
- ✅ **可验证性**：可在MetaMask和区块浏览器验证