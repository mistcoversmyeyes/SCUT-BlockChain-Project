package com.wetech.demo.web3j.controller;

import com.wetech.demo.web3j.service.JYMTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * JYMToken REST API Controller
 *
 * 提供完整的 ERC20 代币操作接口，满足大作业要求：
 * - mint: 铸造代币
 * - transfer: 转账
 * - balanceOf: 查询余额
 * - approve: 授权
 * - transferFrom: 授权转账
 *
 * 额外功能：
 * - burn: 销毁代币
 * - 合约管理：deploy, load
 * - 查询功能：totalSupply, name, symbol, decimals
 */
@Slf4j
@RestController
@RequestMapping("/api/jym")
@RequiredArgsConstructor
public class JYMTokenController {

    private final JYMTokenService jymTokenService;

    /**
     * 部署 JYMToken 合约
     * POST /api/jym/deploy
     *
     * 注意：构造函数自动铸造 1,000,000 JYM 给部署者
     *
     * @return 合约地址和部署信息
     */
    @PostMapping("/deploy")
    public CompletableFuture<ResponseEntity<Map<String, String>>> deployContract() {
        log.info("Deploying JYMToken contract");
        return jymTokenService.deployContract()
                .thenApply(address -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("contractAddress", address);
                    response.put("message", "JYMToken contract deployed successfully");
                    response.put("initialSupply", "1000000000000000000000000"); // 1M * 10^18
                    response.put("tokenName", "JYMToken");
                    response.put("tokenSymbol", "JYM");
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 加载已部署的合约
     * POST /api/jym/load?address=0x...
     *
     * @param address 合约地址
     * @return 加载结果
     */
    @PostMapping("/load")
    public ResponseEntity<Map<String, String>> loadContract(@RequestParam String address) {
        log.info("Loading JYMToken contract from: {}", address);
        jymTokenService.loadContract(address);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contract loaded successfully");
        response.put("contractAddress", address);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取当前合约地址
     * GET /api/jym/address
     *
     * @return 合约地址
     */
    @GetMapping("/address")
    public ResponseEntity<Map<String, String>> getContractAddress() {
        String address = jymTokenService.getContractAddress();
        Map<String, String> response = new HashMap<>();
        if (address != null) {
            response.put("contractAddress", address);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No contract loaded");
            return ResponseEntity.ok(response);
        }
    }

    // ========== 核心接口（大作业要求） ==========

    /**
     * 【必需接口1】铸造代币
     * POST /api/jym/mint?amount=1000
     *
     * @param amount 铸造数量（最小单位）
     * @return 交易回执
     */
    @PostMapping("/mint")
    public CompletableFuture<ResponseEntity<Map<String, String>>> mint(@RequestParam String amount) {
        BigInteger amountBigInt = new BigInteger(amount);
        return jymTokenService.mint(amountBigInt)
                .thenApply(receipt -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("transactionHash", receipt.getTransactionHash());
                    response.put("blockNumber", receipt.getBlockNumber().toString());
                    response.put("gasUsed", receipt.getGasUsed().toString());
                    response.put("status", receipt.getStatus());
                    response.put("amount", amount);
                    response.put("message", "Tokens minted to caller address");
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 【必需接口2】转账
     * POST /api/jym/transfer?to=0x...&amount=1000
     *
     * @param to     接收者地址
     * @param amount 转账数量
     * @return 交易回执
     */
    @PostMapping("/transfer")
    public CompletableFuture<ResponseEntity<Map<String, String>>> transfer(
            @RequestParam String to,
            @RequestParam String amount) {
        BigInteger amountBigInt = new BigInteger(amount);
        return jymTokenService.transfer(to, amountBigInt)
                .thenApply(receipt -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("transactionHash", receipt.getTransactionHash());
                    response.put("blockNumber", receipt.getBlockNumber().toString());
                    response.put("gasUsed", receipt.getGasUsed().toString());
                    response.put("status", receipt.getStatus());
                    response.put("to", to);
                    response.put("amount", amount);
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 【必需接口3】查询余额
     * GET /api/jym/balanceOf?address=0x...
     *
     * @param address 要查询的地址
     * @return 余额信息
     */
    @GetMapping("/balanceOf")
    public CompletableFuture<ResponseEntity<Map<String, String>>> balanceOf(@RequestParam String address) {
        return jymTokenService.balanceOf(address)
                .thenApply(balance -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("address", address);
                    response.put("balance", balance.toString());
                    response.put("balanceReadable", balance.divide(BigInteger.TEN.pow(18)).toString() + " JYM");
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 【必需接口4】授权
     * POST /api/jym/approve?spender=0x...&amount=1000
     *
     * @param spender 被授权地址
     * @param amount  授权数量
     * @return 交易回执
     */
    @PostMapping("/approve")
    public CompletableFuture<ResponseEntity<Map<String, String>>> approve(
            @RequestParam String spender,
            @RequestParam String amount) {
        BigInteger amountBigInt = new BigInteger(amount);
        return jymTokenService.approve(spender, amountBigInt)
                .thenApply(receipt -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("transactionHash", receipt.getTransactionHash());
                    response.put("blockNumber", receipt.getBlockNumber().toString());
                    response.put("gasUsed", receipt.getGasUsed().toString());
                    response.put("status", receipt.getStatus());
                    response.put("spender", spender);
                    response.put("amount", amount);
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 【必需接口5】授权转账
     * POST /api/jym/transferFrom?from=0x...&to=0x...&amount=1000
     *
     * @param from   发送者地址
     * @param to     接收者地址
     * @param amount 转账数量
     * @return 交易回执
     */
    @PostMapping("/transferFrom")
    public CompletableFuture<ResponseEntity<Map<String, String>>> transferFrom(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String amount) {
        BigInteger amountBigInt = new BigInteger(amount);
        return jymTokenService.transferFrom(from, to, amountBigInt)
                .thenApply(receipt -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("transactionHash", receipt.getTransactionHash());
                    response.put("blockNumber", receipt.getBlockNumber().toString());
                    response.put("gasUsed", receipt.getGasUsed().toString());
                    response.put("status", receipt.getStatus());
                    response.put("from", from);
                    response.put("to", to);
                    response.put("amount", amount);
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    // ========== 额外功能 ==========

    /**
     * 销毁代币
     * POST /api/jym/burn?amount=1000
     *
     * @param amount 销毁数量
     * @return 交易回执
     */
    @PostMapping("/burn")
    public CompletableFuture<ResponseEntity<Map<String, String>>> burn(@RequestParam String amount) {
        BigInteger amountBigInt = new BigInteger(amount);
        return jymTokenService.burn(amountBigInt)
                .thenApply(receipt -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("transactionHash", receipt.getTransactionHash());
                    response.put("blockNumber", receipt.getBlockNumber().toString());
                    response.put("gasUsed", receipt.getGasUsed().toString());
                    response.put("status", receipt.getStatus());
                    response.put("amount", amount);
                    response.put("message", "Tokens burned from caller address");
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 查询授权额度
     * GET /api/jym/allowance?owner=0x...&spender=0x...
     *
     * @param owner   所有者地址
     * @param spender 被授权者地址
     * @return 授权额度
     */
    @GetMapping("/allowance")
    public CompletableFuture<ResponseEntity<Map<String, String>>> allowance(
            @RequestParam String owner,
            @RequestParam String spender) {
        return jymTokenService.allowance(owner, spender)
                .thenApply(allowance -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("owner", owner);
                    response.put("spender", spender);
                    response.put("allowance", allowance.toString());
                    response.put("allowanceReadable", allowance.divide(BigInteger.TEN.pow(18)).toString() + " JYM");
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 获取总供应量
     * GET /api/jym/totalSupply
     *
     * @return 总供应量
     */
    @GetMapping("/totalSupply")
    public CompletableFuture<ResponseEntity<Map<String, String>>> getTotalSupply() {
        return jymTokenService.totalSupply()
                .thenApply(totalSupply -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("totalSupply", totalSupply.toString());
                    response.put("totalSupplyReadable", totalSupply.divide(BigInteger.TEN.pow(18)).toString() + " JYM");
                    response.put("contractAddress", jymTokenService.getContractAddress());
                    return ResponseEntity.ok(response);
                });
    }

    /**
     * 获取代币信息
     * GET /api/jym/info
     *
     * @return 代币元数据
     */
    @GetMapping("/info")
    public CompletableFuture<ResponseEntity<Map<String, String>>> getTokenInfo() {
        return jymTokenService.name()
                .thenCompose(name -> jymTokenService.symbol()
                        .thenCompose(symbol -> jymTokenService.decimals()
                                .thenApply(decimals -> {
                                    Map<String, String> response = new HashMap<>();
                                    response.put("name", name);
                                    response.put("symbol", symbol);
                                    response.put("decimals", decimals.toString());
                                    response.put("contractAddress", jymTokenService.getContractAddress());
                                    return ResponseEntity.ok(response);
                                })));
    }
}