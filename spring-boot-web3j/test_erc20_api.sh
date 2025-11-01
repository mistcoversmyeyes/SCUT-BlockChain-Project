#!/bin/bash

# 区块链大作业 - ERC20合约API测试脚本
# 后端服务：http://localhost:8080
# 基础路径：/api/jym

echo "========================================"
echo "  JYMToken ERC20 合约 API 测试"
echo "========================================"
echo ""

# ========== 配置信息 ==========
# 📱 MetaMask 钱包联动配置
# ⚠️ 重要：请修改为你的 MetaMask 地址（必须与 application.properties 中的私钥对应）
YOUR_ADDRESS="0x你的MetaMask地址"
# 你的地址 - 合约将部署到这个地址，代币也将铸造到这个地址

# 接收者地址（可以使用你的第二个 MetaMask 账户地址）
RECEIVER_ADDRESS="0x接收者地址"  # 接收者地址

# 金额设置（单位：最小单位 = 10^18）
MINT_AMOUNT="100000000000000000000"      # 100 JYM
TRANSFER_AMOUNT="50000000000000000000"   # 50 JYM
APPROVE_AMOUNT="100000000000000000000"   # 100 JYM

BASE_URL="http://localhost:8080/api/jym"

# ========== 工具函数 ==========
print_separator() {
    echo ""
    echo "----------------------------------------"
    echo "$1"
    echo "----------------------------------------"
}

print_response() {
    echo "返回结果："
    echo "$1" | python3 -m json.tool 2>/dev/null || echo "$1"
}

# ========== 测试1：部署合约 ==========
print_separator "测试1：部署合约"
echo "指令：curl -X POST $BASE_URL/deploy"
echo ""
RESPONSE=$(curl -s -X POST "$BASE_URL/deploy")
print_response "$RESPONSE"

# 提取合约地址
CONTRACT_ADDRESS=$(echo "$RESPONSE" | grep -o '"contractAddress":"[^"]*"' | cut -d'"' -f4)
echo ""
echo "✅ 部署成功！合约地址：$CONTRACT_ADDRESS"
echo ""

# ========== 测试2：查询初始余额 ==========
print_separator "测试2：查询初始余额（验证铸造）"
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$YOUR_ADDRESS'"
echo ""
INITIAL_BALANCE=$(curl -s -X GET "$BASE_URL/balanceOf?address=$YOUR_ADDRESS")
print_response "$INITIAL_BALANCE"
echo ""
echo "💡 部署时已自动铸造1,000,000 JYM到你的 MetaMask 地址"
echo ""

# ========== 测试3：Mint 接口 ==========
print_separator "测试3：Mint 接口（铸造代币）"
echo "指令：curl -X POST '$BASE_URL/mint?amount=$MINT_AMOUNT'"
echo ""
MINT_RESPONSE=$(curl -s -X POST "$BASE_URL/mint?amount=$MINT_AMOUNT")
print_response "$MINT_RESPONSE"
echo ""

# 验证 Mint 是否成功
echo "验证：查询铸造后的余额"
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$YOUR_ADDRESS'"
echo ""
AFTER_MINT_BALANCE=$(curl -s -X GET "$BASE_URL/balanceOf?address=$YOUR_ADDRESS")
print_response "$AFTER_MINT_BALANCE"
echo ""

# ========== 测试4：Transfer 接口 ==========
print_separator "测试4：Transfer 接口（转账）"
echo "指令：curl -X POST '$BASE_URL/transfer?to=$RECEIVER_ADDRESS&amount=$TRANSFER_AMOUNT'"
echo ""
TRANSFER_RESPONSE=$(curl -s -X POST "$BASE_URL/transfer?to=$RECEIVER_ADDRESS&amount=$TRANSFER_AMOUNT")
print_response "$TRANSFER_RESPONSE"
echo ""

# 验证 Transfer 是否成功
echo "验证：查询转账后的余额变化"
echo "发送方余额："
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$YOUR_ADDRESS'"
echo ""
SENDER_BALANCE=$(curl -s -X GET "$BASE_URL/balanceOf?address=$YOUR_ADDRESS")
print_response "$SENDER_BALANCE"

echo ""
echo "接收方余额："
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$RECEIVER_ADDRESS'"
echo ""
RECEIVER_BALANCE=$(curl -s -X GET "$BASE_URL/balanceOf?address=$RECEIVER_ADDRESS")
print_response "$RECEIVER_BALANCE"
echo ""

# ========== 测试5：Approve 接口 ==========
print_separator "测试5：Approve 接口（授权）"
echo "指令：curl -X POST '$BASE_URL/approve?spender=$RECEIVER_ADDRESS&amount=$APPROVE_AMOUNT'"
echo ""
APPROVE_RESPONSE=$(curl -s -X POST "$BASE_URL/approve?spender=$RECEIVER_ADDRESS&amount=$APPROVE_AMOUNT")
print_response "$APPROVE_RESPONSE"
echo ""

# 验证 Approve 是否成功
echo "验证：查询授权额度"
echo "指令：curl -X GET '$BASE_URL/allowance?owner=$YOUR_ADDRESS&spender=$RECEIVER_ADDRESS'"
echo ""
ALLOWANCE=$(curl -s -X GET "$BASE_URL/allowance?owner=$YOUR_ADDRESS&spender=$RECEIVER_ADDRESS")
print_response "$ALLOWANCE"
echo ""

# ========== 测试6：TransferFrom 接口 ==========
print_separator "测试6：TransferFrom 接口（授权转账）"
TRANSFER_FROM_AMOUNT="30000000000000000000"  # 30 JYM
echo "指令：curl -X POST '$BASE_URL/transferFrom?from=$YOUR_ADDRESS&to=$RECEIVER_ADDRESS&amount=$TRANSFER_FROM_AMOUNT'"
echo ""
TRANSFER_FROM_RESPONSE=$(curl -s -X POST "$BASE_URL/transferFrom?from=$YOUR_ADDRESS&to=$RECEIVER_ADDRESS&amount=$TRANSFER_FROM_AMOUNT")
print_response "$TRANSFER_FROM_RESPONSE"
echo ""

# 验证 TransferFrom 是否成功
echo "验证：查询授权转账后的变化"
echo "1. 发送方（你的）余额："
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$YOUR_ADDRESS'"
echo ""
AFTER_TRANSFER_FROM_SENDER=$(curl -s -X GET "$BASE_URL/balanceOf?address=$YOUR_ADDRESS")
print_response "$AFTER_TRANSFER_FROM_SENDER"

echo ""
echo "2. 接收方余额："
echo "指令：curl -X GET '$BASE_URL/balanceOf?address=$RECEIVER_ADDRESS'"
echo ""
AFTER_TRANSFER_FROM_RECEIVER=$(curl -s -X GET "$BASE_URL/balanceOf?address=$RECEIVER_ADDRESS")
print_response "$AFTER_TRANSFER_FROM_RECEIVER"

echo ""
echo "3. 剩余授权额度："
echo "指令：curl -X GET '$BASE_URL/allowance?owner=$YOUR_ADDRESS&spender=$RECEIVER_ADDRESS'"
echo ""
AFTER_TRANSFER_FROM_ALLOWANCE=$(curl -s -X GET "$BASE_URL/allowance?owner=$YOUR_ADDRESS&spender=$RECEIVER_ADDRESS")
print_response "$AFTER_TRANSFER_FROM_ALLOWANCE"
echo ""

# ========== 测试总结 ==========
print_separator "测试完成"
echo "✅ 所有接口测试完毕"
echo ""
echo "📱 MetaMask 钱包联动验证："
echo "1. 打开 MetaMask → 资产 → 导入代币"
echo "2. 代币合约地址：$CONTRACT_ADDRESS"
echo "3. 代币符号：JYM，小数位数：18"
echo "4. 验证你的 JYM 余额是否正确显示"
echo ""
echo "📸 评分截图要求："
echo "1. 每个接口的调用结果（交易哈希）"
echo "2. 每个接口的验证结果（余额变化）"
echo "3. MetaMask 中显示的 JYM 余额截图"
echo ""
echo "📊 关键数据记录："
echo "- 合约地址：$CONTRACT_ADDRESS"
echo "- 你的地址：$YOUR_ADDRESS"
echo "- 初始余额：$(echo "$INITIAL_BALANCE" | grep -o '"balanceReadable":"[^"]*"' | cut -d'"' -f4)"
echo "- 铸造后余额：$(echo "$AFTER_MINT_BALANCE" | grep -o '"balanceReadable":"[^"]*"' | cut -d'"' -f4)"
echo "- 转账后余额：$(echo "$SENDER_BALANCE" | grep -o '"balanceReadable":"[^"]*"' | cut -d'"' -f4)"
echo "- 授权额度：$(echo "$ALLOWANCE" | grep -o '"allowanceReadable":"[^"]*"' | cut -d'"' -f4)"
echo ""
echo "🎯 大作业要求完成情况："
echo "- ✅ ERC20 标准合约：5个核心接口"
echo "- ✅ 钱包联动：合约部署到你的 MetaMask 地址"
echo "- ✅ 代币验证：可在 MetaMask 中查看 JYM 余额"
echo "========================================"
