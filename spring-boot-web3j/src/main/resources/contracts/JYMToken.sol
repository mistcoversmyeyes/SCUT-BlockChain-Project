// SPDX-License-Identifier: Apache 2.0
  pragma solidity ^0.8.20;

  import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
  import "@openzeppelin/contracts/token/ERC20/extensions/ERC20Permit.sol";

  contract JYMToken is ERC20, ERC20Permit {
      constructor() ERC20("JYMToken", "JYM") ERC20Permit("JYMToken") {
          // 铸造 100 万个代币给部署者
          _mint(msg.sender, 1000000 * 10**18);
      }

      // 铸造代币功能
      function mint(uint256 value) public {
          _mint(msg.sender, value);
      }

      // 销毁代币功能
      function burn(uint256 value) public {
          _burn(msg.sender, value);
      }

      // 查询代币名称
      function name() public view override returns (string memory) {
          return "JYMToken";
      }

      // 查询代币符号
      function symbol() public view override returns (string memory) {
          return "JYM";
      }
  }