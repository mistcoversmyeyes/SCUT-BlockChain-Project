package com.wetech.demo.web3j.contracts.jymtoken;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.CustomError;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes1;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.14.0.
 */
@SuppressWarnings("rawtypes")
public class JYMToken extends Contract {
    public static final String BINARY = "610160604052348015610010575f5ffd5b5060405180604001604052806008815260200167252ca6aa37b5b2b760c11b81525080604051806040016040528060018152602001603160f81b81525060405180604001604052806008815260200167252ca6aa37b5b2b760c11b815250604051806040016040528060038152602001624a594d60e81b815250816003908161009991906103de565b5060046100a682826103de565b506100b691508390506005610174565b610120526100c5816006610174565b61014052815160208084019190912060e052815190820120610100524660a05261015160e05161010051604080517f8b73c3c69bb8fe3d512ecc4cf759cc79239f7b179b0ffacaa9a75d522b39400f60208201529081019290925260608201524660808201523060a08201525f9060c00160405160208183030381529060405280519060200120905090565b60805250503060c0525061016f3369d3c21bcecceda10000006101a6565b61050f565b5f60208351101561018f57610188836101e3565b90506101a0565b8161019a84826103de565b5060ff90505b92915050565b6001600160a01b0382166101d45760405163ec442f0560e01b81525f60048201526024015b60405180910390fd5b6101df5f8383610220565b5050565b5f5f829050601f8151111561020d578260405163305a27a960e01b81526004016101cb9190610498565b8051610218826104cd565b179392505050565b6001600160a01b03831661024a578060025f82825461023f91906104f0565b909155506102ba9050565b6001600160a01b0383165f908152602081905260409020548181101561029c5760405163391434e360e21b81526001600160a01b038516600482015260248101829052604481018390526064016101cb565b6001600160a01b0384165f9081526020819052604090209082900390555b6001600160a01b0382166102d6576002805482900390556102f4565b6001600160a01b0382165f9081526020819052604090208054820190555b816001600160a01b0316836001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8360405161033991815260200190565b60405180910390a3505050565b634e487b7160e01b5f52604160045260245ffd5b600181811c9082168061036e57607f821691505b60208210810361038c57634e487b7160e01b5f52602260045260245ffd5b50919050565b601f8211156103d957805f5260205f20601f840160051c810160208510156103b75750805b601f840160051c820191505b818110156103d6575f81556001016103c3565b50505b505050565b81516001600160401b038111156103f7576103f7610346565b61040b81610405845461035a565b84610392565b6020601f82116001811461043d575f83156104265750848201515b5f19600385901b1c1916600184901b1784556103d6565b5f84815260208120601f198516915b8281101561046c578785015182556020948501946001909201910161044c565b508482101561048957868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b602081525f82518060208401528060208501604085015e5f604082850101526040601f19601f83011684010191505092915050565b8051602080830151919081101561038c575f1960209190910360031b1b16919050565b808201808211156101a057634e487b7160e01b5f52601160045260245ffd5b60805160a05160c05160e051610100516101205161014051610f0c6105605f395f61070c01525f6106df01525f61065001525f61062801525f61058301525f6105ad01525f6105d70152610f0c5ff3fe608060405234801561000f575f5ffd5b50600436106100f0575f3560e01c806370a0823111610093578063a0712d6811610063578063a0712d6814610213578063a9059cbb14610226578063d505accf14610239578063dd62ed3e1461024c575f5ffd5b806370a082311461019e5780637ecebe00146101c657806384b0196e146101d957806395d89b41146101f4575f5ffd5b806323b872dd116100ce57806323b872dd1461015f578063313ce567146101725780633644e5151461018157806342966c6814610189575f5ffd5b806306fdde03146100f4578063095ea7b31461012a57806318160ddd1461014d575b5f5ffd5b604080518082019091526008815267252ca6aa37b5b2b760c11b60208201525b6040516101219190610c71565b60405180910390f35b61013d610138366004610ca5565b610284565b6040519015158152602001610121565b6002545b604051908152602001610121565b61013d61016d366004610ccd565b61029d565b60405160128152602001610121565b6101516102c0565b61019c610197366004610d07565b6102ce565b005b6101516101ac366004610d1e565b6001600160a01b03165f9081526020819052604090205490565b6101516101d4366004610d1e565b6102db565b6101e16102f8565b6040516101219796959493929190610d37565b6040805180820190915260038152624a594d60e81b6020820152610114565b61019c610221366004610d07565b61033a565b61013d610234366004610ca5565b610344565b61019c610247366004610dcd565b610351565b61015161025a366004610e3a565b6001600160a01b039182165f90815260016020908152604080832093909416825291909152205490565b5f3361029181858561048c565b60019150505b92915050565b5f336102aa85828561049e565b6102b585858561051a565b506001949350505050565b5f6102c9610577565b905090565b6102d833826106a0565b50565b6001600160a01b0381165f90815260076020526040812054610297565b5f6060805f5f5f60606103096106d8565b610311610705565b604080515f80825260208201909252600f60f81b9b939a50919850469750309650945092509050565b6102d83382610732565b5f3361029181858561051a565b8342111561037a5760405163313c898160e11b8152600481018590526024015b60405180910390fd5b5f7f6e71edae12b1b97f4d1f60370fef10105fa2faae0126114a169c64845d6126c98888886103c58c6001600160a01b03165f90815260076020526040902080546001810190915590565b6040805160208101969096526001600160a01b0394851690860152929091166060840152608083015260a082015260c0810186905260e0016040516020818303038152906040528051906020012090505f61041f82610766565b90505f61042e82878787610792565b9050896001600160a01b0316816001600160a01b031614610475576040516325c0072360e11b81526001600160a01b0380831660048301528b166024820152604401610371565b6104808a8a8a61048c565b50505050505050505050565b61049983838360016107be565b505050565b6001600160a01b038381165f908152600160209081526040808320938616835292905220545f19811015610514578181101561050657604051637dc7a0d960e11b81526001600160a01b03841660048201526024810182905260448101839052606401610371565b61051484848484035f6107be565b50505050565b6001600160a01b03831661054357604051634b637e8f60e11b81525f6004820152602401610371565b6001600160a01b03821661056c5760405163ec442f0560e01b81525f6004820152602401610371565b610499838383610890565b5f306001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000161480156105cf57507f000000000000000000000000000000000000000000000000000000000000000046145b156105f957507f000000000000000000000000000000000000000000000000000000000000000090565b6102c9604080517f8b73c3c69bb8fe3d512ecc4cf759cc79239f7b179b0ffacaa9a75d522b39400f60208201527f0000000000000000000000000000000000000000000000000000000000000000918101919091527f000000000000000000000000000000000000000000000000000000000000000060608201524660808201523060a08201525f9060c00160405160208183030381529060405280519060200120905090565b6001600160a01b0382166106c957604051634b637e8f60e11b81525f6004820152602401610371565b6106d4825f83610890565b5050565b60606102c97f000000000000000000000000000000000000000000000000000000000000000060056109b6565b60606102c97f000000000000000000000000000000000000000000000000000000000000000060066109b6565b6001600160a01b03821661075b5760405163ec442f0560e01b81525f6004820152602401610371565b6106d45f8383610890565b5f610297610772610577565b8360405161190160f01b8152600281019290925260228201526042902090565b5f5f5f5f6107a288888888610a5f565b9250925092506107b28282610b27565b50909695505050505050565b6001600160a01b0384166107e75760405163e602df0560e01b81525f6004820152602401610371565b6001600160a01b03831661081057604051634a1406b160e11b81525f6004820152602401610371565b6001600160a01b038085165f908152600160209081526040808320938716835292905220829055801561051457826001600160a01b0316846001600160a01b03167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9258460405161088291815260200190565b60405180910390a350505050565b6001600160a01b0383166108ba578060025f8282546108af9190610e6b565b9091555061092a9050565b6001600160a01b0383165f908152602081905260409020548181101561090c5760405163391434e360e21b81526001600160a01b03851660048201526024810182905260448101839052606401610371565b6001600160a01b0384165f9081526020819052604090209082900390555b6001600160a01b03821661094657600280548290039055610964565b6001600160a01b0382165f9081526020819052604090208054820190555b816001600160a01b0316836001600160a01b03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef836040516109a991815260200190565b60405180910390a3505050565b606060ff83146109d0576109c983610bdf565b9050610297565b8180546109dc90610e8a565b80601f0160208091040260200160405190810160405280929190818152602001828054610a0890610e8a565b8015610a535780601f10610a2a57610100808354040283529160200191610a53565b820191905f5260205f20905b815481529060010190602001808311610a3657829003601f168201915b50505050509050610297565b5f80807f7fffffffffffffffffffffffffffffff5d576e7357a4501ddfe92f46681b20a0841115610a9857505f91506003905082610b1d565b604080515f808252602082018084528a905260ff891692820192909252606081018790526080810186905260019060a0016020604051602081039080840390855afa158015610ae9573d5f5f3e3d5ffd5b5050604051601f1901519150506001600160a01b038116610b1457505f925060019150829050610b1d565b92505f91508190505b9450945094915050565b5f826003811115610b3a57610b3a610ec2565b03610b43575050565b6001826003811115610b5757610b57610ec2565b03610b755760405163f645eedf60e01b815260040160405180910390fd5b6002826003811115610b8957610b89610ec2565b03610baa5760405163fce698f760e01b815260048101829052602401610371565b6003826003811115610bbe57610bbe610ec2565b036106d4576040516335e2f38360e21b815260048101829052602401610371565b60605f610beb83610c1c565b6040805160208082528183019092529192505f91906020820181803683375050509182525060208101929092525090565b5f60ff8216601f81111561029757604051632cd44ac360e21b815260040160405180910390fd5b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f610c836020830184610c43565b9392505050565b80356001600160a01b0381168114610ca0575f5ffd5b919050565b5f5f60408385031215610cb6575f5ffd5b610cbf83610c8a565b946020939093013593505050565b5f5f5f60608486031215610cdf575f5ffd5b610ce884610c8a565b9250610cf660208501610c8a565b929592945050506040919091013590565b5f60208284031215610d17575f5ffd5b5035919050565b5f60208284031215610d2e575f5ffd5b610c8382610c8a565b60ff60f81b8816815260e060208201525f610d5560e0830189610c43565b8281036040840152610d678189610c43565b606084018890526001600160a01b038716608085015260a0840186905283810360c0850152845180825260208087019350909101905f5b81811015610dbc578351835260209384019390920191600101610d9e565b50909b9a5050505050505050505050565b5f5f5f5f5f5f5f60e0888a031215610de3575f5ffd5b610dec88610c8a565b9650610dfa60208901610c8a565b95506040880135945060608801359350608088013560ff81168114610e1d575f5ffd5b9699959850939692959460a0840135945060c09093013592915050565b5f5f60408385031215610e4b575f5ffd5b610e5483610c8a565b9150610e6260208401610c8a565b90509250929050565b8082018082111561029757634e487b7160e01b5f52601160045260245ffd5b600181811c90821680610e9e57607f821691505b602082108103610ebc57634e487b7160e01b5f52602260045260245ffd5b50919050565b634e487b7160e01b5f52602160045260245ffdfea2646970667358221220400b8ff74747a8b7b605737f80cbc7246cac823499c8f045598b039d7f7db8c464736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DOMAIN_SEPARATOR = "DOMAIN_SEPARATOR";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURN = "burn";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_EIP712DOMAIN = "eip712Domain";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_NONCES = "nonces";

    public static final String FUNC_PERMIT = "permit";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final CustomError ECDSAINVALIDSIGNATURE_ERROR = new CustomError("ECDSAInvalidSignature", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final CustomError ECDSAINVALIDSIGNATURELENGTH_ERROR = new CustomError("ECDSAInvalidSignatureLength", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ECDSAINVALIDSIGNATURES_ERROR = new CustomError("ECDSAInvalidSignatureS", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final CustomError ERC20INSUFFICIENTALLOWANCE_ERROR = new CustomError("ERC20InsufficientAllowance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC20INSUFFICIENTBALANCE_ERROR = new CustomError("ERC20InsufficientBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC20INVALIDAPPROVER_ERROR = new CustomError("ERC20InvalidApprover", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDRECEIVER_ERROR = new CustomError("ERC20InvalidReceiver", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDSENDER_ERROR = new CustomError("ERC20InvalidSender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC20INVALIDSPENDER_ERROR = new CustomError("ERC20InvalidSpender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError ERC2612EXPIREDSIGNATURE_ERROR = new CustomError("ERC2612ExpiredSignature", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final CustomError ERC2612INVALIDSIGNER_ERROR = new CustomError("ERC2612InvalidSigner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final CustomError INVALIDACCOUNTNONCE_ERROR = new CustomError("InvalidAccountNonce", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final CustomError INVALIDSHORTSTRING_ERROR = new CustomError("InvalidShortString", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final CustomError STRINGTOOLONG_ERROR = new CustomError("StringTooLong", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EIP712DOMAINCHANGED_EVENT = new Event("EIP712DomainChanged", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected JYMToken(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected JYMToken(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected JYMToken(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected JYMToken(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<EIP712DomainChangedEventResponse> getEIP712DomainChangedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EIP712DOMAINCHANGED_EVENT, transactionReceipt);
        ArrayList<EIP712DomainChangedEventResponse> responses = new ArrayList<EIP712DomainChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EIP712DomainChangedEventResponse typedResponse = new EIP712DomainChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EIP712DomainChangedEventResponse getEIP712DomainChangedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EIP712DOMAINCHANGED_EVENT, log);
        EIP712DomainChangedEventResponse typedResponse = new EIP712DomainChangedEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<EIP712DomainChangedEventResponse> eIP712DomainChangedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEIP712DomainChangedEventFromLog(log));
    }

    public Flowable<EIP712DomainChangedEventResponse> eIP712DomainChangedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EIP712DOMAINCHANGED_EVENT));
        return eIP712DomainChangedEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> DOMAIN_SEPARATOR() {
        final Function function = new Function(FUNC_DOMAIN_SEPARATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String spender, BigInteger value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burn(BigInteger value) {
        final Function function = new Function(
                FUNC_BURN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<byte[], String, String, BigInteger, String, byte[], List<BigInteger>>> eip712Domain(
            ) {
        final Function function = new Function(FUNC_EIP712DOMAIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes1>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], String, String, BigInteger, String, byte[], List<BigInteger>>>(function,
                new Callable<Tuple7<byte[], String, String, BigInteger, String, byte[], List<BigInteger>>>() {
                    @Override
                    public Tuple7<byte[], String, String, BigInteger, String, byte[], List<BigInteger>> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], String, String, BigInteger, String, byte[], List<BigInteger>>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (byte[]) results.get(5).getValue(), 
                                convertToNative((List<Uint256>) results.get(6).getValue()));
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> mint(BigInteger value) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> nonces(String owner) {
        final Function function = new Function(FUNC_NONCES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> permit(String owner, String spender,
            BigInteger value, BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_PERMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String from, String to,
            BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static JYMToken load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new JYMToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static JYMToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new JYMToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static JYMToken load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new JYMToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static JYMToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new JYMToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<JYMToken> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(JYMToken.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<JYMToken> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(JYMToken.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<JYMToken> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(JYMToken.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<JYMToken> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(JYMToken.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class EIP712DomainChangedEventResponse extends BaseEventResponse {
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}
