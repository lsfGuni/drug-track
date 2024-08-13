package com.example.blockchain;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.0.
 */
@SuppressWarnings("rawtypes")
public class DataStorage extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610491806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806373d4a13a1461003b578063fb218f5f14610059575b600080fd5b610043610075565b60405161005091906102d9565b60405180910390f35b610073600480360381019061006e919061025f565b610103565b005b60008054610082906103ba565b80601f01602080910402602001604051908101604052809291908181526020018280546100ae906103ba565b80156100fb5780601f106100d0576101008083540402835291602001916100fb565b820191906000526020600020905b8154815290600101906020018083116100de57829003601f168201915b505050505081565b8060009080519060200190610119929190610154565b507ffe3101cc3119e1fe29a9c3464a3ff7e98501e65122abab6937026311367dc5168160405161014991906102d9565b60405180910390a150565b828054610160906103ba565b90600052602060002090601f01602090048101928261018257600085556101c9565b82601f1061019b57805160ff19168380011785556101c9565b828001600101855582156101c9579182015b828111156101c85782518255916020019190600101906101ad565b5b5090506101d691906101da565b5090565b5b808211156101f35760008160009055506001016101db565b5090565b600061020a6102058461032c565b6102fb565b90508281526020810184848401111561022257600080fd5b61022d848285610378565b509392505050565b600082601f83011261024657600080fd5b81356102568482602086016101f7565b91505092915050565b60006020828403121561027157600080fd5b600082013567ffffffffffffffff81111561028b57600080fd5b61029784828501610235565b91505092915050565b60006102ab8261035c565b6102b58185610367565b93506102c5818560208601610387565b6102ce8161044a565b840191505092915050565b600060208201905081810360008301526102f381846102a0565b905092915050565b6000604051905081810181811067ffffffffffffffff821117156103225761032161041b565b5b8060405250919050565b600067ffffffffffffffff8211156103475761034661041b565b5b601f19601f8301169050602081019050919050565b600081519050919050565b600082825260208201905092915050565b82818337600083830152505050565b60005b838110156103a557808201518184015260208101905061038a565b838111156103b4576000848401525b50505050565b600060028204905060018216806103d257607f821691505b602082108114156103e6576103e56103ec565b5b50919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000601f19601f830116905091905056fea26469706673582212201881d925ad2cf16ca6d6581580f9fa6b077d07d3cc1b611cec0db0567f335c3764736f6c63430008000033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DATA = "data";

    public static final String FUNC_STOREDATA = "storeData";

    public static final Event DATASTORED_EVENT = new Event("DataStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("1723444227107", "0x2544546e3A549f3c574FDe3fd3aFFEEb80A671d9");
        _addresses.put("1723450160417", "0x8b1856ba150784dB30f4878F18c256429c33F496");
    }

    @Deprecated
    protected DataStorage(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DataStorage(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DataStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DataStorage(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DataStoredEventResponse> getDataStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DATASTORED_EVENT, transactionReceipt);
        ArrayList<DataStoredEventResponse> responses = new ArrayList<DataStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DataStoredEventResponse typedResponse = new DataStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.data = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DataStoredEventResponse getDataStoredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DATASTORED_EVENT, log);
        DataStoredEventResponse typedResponse = new DataStoredEventResponse();
        typedResponse.log = log;
        typedResponse.data = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DataStoredEventResponse> dataStoredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDataStoredEventFromLog(log));
    }

    public Flowable<DataStoredEventResponse> dataStoredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DATASTORED_EVENT));
        return dataStoredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> call_data() {
        final Function function = new Function(FUNC_DATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> send_data() {
        final Function function = new Function(
                FUNC_DATA, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> send_storeData(String _data) {
        final Function function = new Function(
                FUNC_STOREDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DataStorage load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new DataStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DataStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DataStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DataStorage load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new DataStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DataStorage load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DataStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DataStorage> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DataStorage.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DataStorage> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DataStorage.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<DataStorage> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DataStorage.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DataStorage> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DataStorage.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class DataStoredEventResponse extends BaseEventResponse {
        public String data;
    }
}
