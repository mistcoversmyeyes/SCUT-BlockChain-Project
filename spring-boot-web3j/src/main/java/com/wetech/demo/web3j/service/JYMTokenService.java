package com.wetech.demo.web3j.service;

import com.wetech.demo.web3j.contracts.jymtoken.JYMToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;

/**
 * JYMToken Service - 实现 ERC20 代币的业务逻辑
 *
 * 功能列表（按大作业要求）：
 * 1. mint - 铸造代币
 * 2. transfer - 转账
 * 3. balanceOf - 查询余额
 * 4. approve - 授权
 * 5. transferFrom - 授权转账
 *
 * 额外功能：
 * - burn - 销毁代币
 * - allowance - 查询授权额度
 * - totalSupply - 查询总供应量
 * - name/symbol/decimals - 代币元数据
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JYMTokenService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;

    private JYMToken contract;

    @Getter
    private String contractAddress;

    /**
     * 部署 JYMToken 合约
     * 注意：构造函数会自动铸造 1,000,000 JYM (10^24 最小单位) 给部署者
     *
     * @return 合约地址
     */
    public CompletableFuture<String> deployContract() {
        log.info("Deploying JYMToken contract...");
        return JYMToken.deploy(web3j, credentials, gasProvider)
                .sendAsync()
                .thenApply(contract -> {
                    this.contract = contract;
                    this.contractAddress = contract.getContractAddress();
                    log.info("JYMToken deployed to: {}", contractAddress);
                    log.info("Initial supply: 1,000,000 JYM minted to deployer");
                    return contractAddress;
                });
    }

    /**
     * 加载已部署的合约
     *
     * @param contractAddress 合约地址
     */
    public void loadContract(String contractAddress) {
        log.info("Loading JYMToken contract from: {}", contractAddress);
        this.contract = JYMToken.load(contractAddress, web3j, credentials, gasProvider);
        this.contractAddress = contractAddress;
    }

    /**
     * 【必需接口1】mint - 铸造代币
     * 铸造的代币会发送给调用者（当前私钥对应的地址）
     *
     * @param amount 铸造数量（最小单位，需乘以 10^18）
     * @return 交易回执
     */
    public CompletableFuture<TransactionReceipt> mint(BigInteger amount) {
        ensureContractLoaded();
        log.info("Minting {} tokens", amount);
        return contract.mint(amount).sendAsync();
    }

    /**
     * 【必需接口2】transfer - 转账
     *
     * @param to     接收者地址
     * @param amount 转账数量
     * @return 交易回执
     */
    public CompletableFuture<TransactionReceipt> transfer(String to, BigInteger amount) {
        ensureContractLoaded();
        log.info("Transferring {} tokens to {}", amount, to);
        return contract.transfer(to, amount).sendAsync();
    }

    /**
     * 【必需接口3】balanceOf - 查询余额
     *
     * @param address 要查询的地址
     * @return 余额
     */
    public CompletableFuture<BigInteger> balanceOf(String address) {
        ensureContractLoaded();
        log.info("Getting balance of: {}", address);
        return contract.balanceOf(address).sendAsync();
    }

    /**
     * 【必需接口4】approve - 授权
     * 授权指定地址可以花费的代币数量
     *
     * @param spender 被授权的地址
     * @param amount  授权数量
     * @return 交易回执
     */
    public CompletableFuture<TransactionReceipt> approve(String spender, BigInteger amount) {
        ensureContractLoaded();
        log.info("Approving {} tokens for spender: {}", amount, spender);
        return contract.approve(spender, amount).sendAsync();
    }

    /**
     * 【必需接口5】transferFrom - 授权转账
     * 使用授权额度从一个地址转账到另一个地址
     *
     * @param from   发送者地址
     * @param to     接收者地址
     * @param amount 转账数量
     * @return 交易回执
     */
    public CompletableFuture<TransactionReceipt> transferFrom(String from, String to, BigInteger amount) {
        ensureContractLoaded();
        log.info("TransferFrom: {} tokens from {} to {}", amount, from, to);
        return contract.transferFrom(from, to, amount).sendAsync();
    }

    // ========== 辅助功能 ==========

    /**
     * burn - 销毁代币
     *
     * @param amount 销毁数量
     * @return 交易回执
     */
    public CompletableFuture<TransactionReceipt> burn(BigInteger amount) {
        ensureContractLoaded();
        log.info("Burning {} tokens", amount);
        return contract.burn(amount).sendAsync();
    }

    /**
     * allowance - 查询授权额度
     *
     * @param owner   所有者地址
     * @param spender 被授权者地址
     * @return 授权额度
     */
    public CompletableFuture<BigInteger> allowance(String owner, String spender) {
        ensureContractLoaded();
        log.info("Getting allowance: owner={}, spender={}", owner, spender);
        return contract.allowance(owner, spender).sendAsync();
    }

    /**
     * 获取总供应量
     */
    public CompletableFuture<BigInteger> totalSupply() {
        ensureContractLoaded();
        return contract.totalSupply().sendAsync();
    }

    /**
     * 获取代币名称
     */
    public CompletableFuture<String> name() {
        ensureContractLoaded();
        return contract.name().sendAsync();
    }

    /**
     * 获取代币符号
     */
    public CompletableFuture<String> symbol() {
        ensureContractLoaded();
        return contract.symbol().sendAsync();
    }

    /**
     * 获取小数位数
     */
    public CompletableFuture<BigInteger> decimals() {
        ensureContractLoaded();
        return contract.decimals().sendAsync();
    }

    private void ensureContractLoaded() {
        if (contract == null) {
            throw new IllegalStateException("Contract not deployed or loaded");
        }
    }
}
