import { HardhatRuntimeEnvironment } from "hardhat/types";
import { DeployFunction } from "hardhat-deploy/types";
import { Contract } from "ethers";

/**
 * 部署 JYMToken 合约
 *
 * JYMToken 是一个 ERC20 代币合约，符合大作业要求：
 * - 继承 ERC20 和 ERC20Permit
 * - 实现标准接口：transfer, balanceOf, approve, transferFrom
 * - 额外功能：mint（铸造）, burn（销毁）
 *
 * 部署标签：ERC20JYM202330550601（符合大作业要求格式）
 *
 * @param hre HardhatRuntimeEnvironment object.
 */
const deployJYMToken: DeployFunction = async function (hre: HardhatRuntimeEnvironment) {
  const { deployer } = await hre.getNamedAccounts();
  const { deploy } = hre.deployments;

  console.log("🚀 开始部署 JYMToken 合约...");
  console.log("📝 合约信息：");
  console.log("   - 代币名称: JYMToken");
  console.log("   - 代币符号: JYM");
  console.log("   - 初始供应量: 1,000,000 JYM");
  console.log("   - 小数位数: 18");
  console.log("   - 部署者地址:", deployer);

  // 部署 JYMToken 合约
  // 构造函数会自动铸造 1,000,000 JYM 给部署者
  const jymTokenDeployment = await deploy("JYMToken", {
    from: deployer,
    // JYMToken 构造函数不需要参数（name 和 symbol 在合约内部定义）
    args: [],
    log: true,
    // autoMine: 在本地网络上自动挖矿以加快部署速度
    autoMine: true,
  });

  console.log("✅ JYMToken 合约部署成功！");
  console.log("📍 合约地址:", jymTokenDeployment.address);
  console.log("🔗 交易哈希:", jymTokenDeployment.transactionHash);

  // 获取已部署的合约实例以进行交互测试
  const jymToken = await hre.ethers.getContract<Contract>("JYMToken", deployer);

  try {
    // 验证合约基本信息
    console.log("🔍 验证合约信息...");

    const name = await jymToken.name();
    const symbol = await jymToken.symbol();
    const decimals = await jymToken.decimals();
    const totalSupply = await jymToken.totalSupply();
    const deployerBalance = await jymToken.balanceOf(deployer);

    console.log("📊 合约信息验证结果：");
    console.log("   - 代币名称:", name);
    console.log("   - 代币符号:", symbol);
    console.log("   - 小数位数:", decimals.toString());
    console.log("   - 总供应量:", hre.ethers.formatEther(totalSupply), "JYM");
    console.log("   - 部署者余额:", hre.ethers.formatEther(deployerBalance), "JYM");

    // 验证初始铸造是否成功
    const expectedSupply = hre.ethers.parseEther("1000000"); // 1M JYM
    if (totalSupply.toString() === expectedSupply.toString()) {
      console.log("✅ 初始供应量验证通过");
    } else {
      console.log("❌ 初始供应量验证失败");
    }

    if (deployerBalance.toString() === expectedSupply.toString()) {
      console.log("✅ 部署者余额验证通过");
    } else {
      console.log("❌ 部署者余额验证失败");
    }

    console.log("🎉 JYMToken 合约部署和验证完成！");

  } catch (error) {
    console.error("❌ 合约验证过程中出现错误:", error);
  }
};

export default deployJYMToken;

// 部署标签：符合大作业要求格式 ERC20+名字缩写+学号
deployJYMToken.tags = ["ERC20JYM202330550601"];