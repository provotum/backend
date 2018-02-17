package org.provotum.backend.ethereum.wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class Ballot extends Contract {
    private static final String BINARY = "606060405234156200001057600080fd5b6040516200121238038062001212833981016040528080518201919060200180516000805460a060020a60ff02191690559150600490508280516200005a929160200190620000a5565b5060006200006a6003826200012a565b506000600181905560058054600160a060020a0319908116600160a060020a0394851617909155815416339092169190911790555062000222565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e857805160ff191683800117855562000118565b8280016001018555821562000118579182015b8281111562000118578251825591602001919060010190620000fb565b50620001269291506200015e565b5090565b81548183558181151162000159576004028160040283600052602060002091820191016200015991906200017e565b505050565b6200017b91905b8082111562000126576000815560010162000165565b90565b6200017b91905b8082111562000126578054600160a060020a03191681556000620001ad6001830182620001d7565b620001bd600283016000620001d7565b620001cd600383016000620001d7565b5060040162000185565b50805460018160011615610100020316600290046000825580601f10620001ff57506200021f565b601f0160209004906000526020600020908101906200021f91906200015e565b50565b610fe080620002326000396000f3006060604052600436106100825763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313ca4fb581146100875780635a55c1f01461011157806383197ef014610282578063834a743d146102975780639a0e7d661461034d578063affc067014610372578063c631b29214610385575b600080fd5b341561009257600080fd5b61009a610398565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156100d65780820151838201526020016100be565b50505050905090810190601f1680156101035780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561011c57600080fd5b610127600435610441565b604051600160a060020a03851681526080602082018181529060408301906060840190840187818151815260200191508051906020019080838360005b8381101561017c578082015183820152602001610164565b50505050905090810190601f1680156101a95780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101df5780820151838201526020016101c7565b50505050905090810190601f16801561020c5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561024257808201518382015260200161022a565b50505050905090810190601f16801561026f5780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b341561028d57600080fd5b6102956106ce565b005b34156102a257600080fd5b6102cd602460048035828101929082013591813580830192908201359160443591820191013561076d565b604051821515815260406020820181815290820183818151815260200191508051906020019080838360005b838110156103115780820151838201526020016102f9565b50505050905090810190601f16801561033e5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561035857600080fd5b610360610c93565b60405190815260200160405180910390f35b341561037d57600080fd5b610295610c99565b341561039057600080fd5b610295610d61565b6103a0610e12565b60048054600260001961010060018416150201909116046020601f820181900481020160405190810160405280929190818152602001828054600181600116156101000203166002900480156104375780601f1061040c57610100808354040283529160200191610437565b820191906000526020600020905b81548152906001019060200180831161041a57829003601f168201915b5050505050905090565b600061044b610e12565b610453610e12565b61045b610e12565b600380548690811061046957fe5b600091825260209091206004909102015460038054600160a060020a03909216918790811061049457fe5b90600052602060002090600402016001016001600201878154811015156104b757fe5b90600052602060002090600402016002016001600201888154811015156104da57fe5b9060005260206000209060040201600301828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105805780601f1061055557610100808354040283529160200191610580565b820191906000526020600020905b81548152906001019060200180831161056357829003601f168201915b50505050509250818054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561061c5780601f106105f15761010080835404028352916020019161061c565b820191906000526020600020905b8154815290600101906020018083116105ff57829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106b85780601f1061068d576101008083540402835291602001916106b8565b820191906000526020600020905b81548152906001019060200180831161069b57829003601f168201915b5050505050905093509350935093509193509193565b60005433600160a060020a039081169116146106e957600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc600160405190151581526040602082018190526012818301527f44657374726f79656420636f6e7472616374000000000000000000000000000060608301526080909101905180910390a2600054600160a060020a0316ff5b6000610777610e12565b60008054819074010000000000000000000000000000000000000000900460ff1615156108425733600160a060020a0316600080516020610f95833981519152600060405190151581526040602082018190526010818301527f566f74696e6720697320636c6f7365640000000000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601081527f566f74696e6720697320636c6f7365640000000000000000000000000000000060208201529094509250610c86565b600160a060020a03331660009081526002602052604090205460ff169150811561090a5733600160a060020a0316600080516020610f95833981519152600060405190151581526040602082018190526013818301527f566f74657220616c726561647920766f7465640000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601381527f566f74657220616c726561647920766f7465640000000000000000000000000060208201529094509250610c86565b600554600160a060020a0316632640df7789896000604051602001526040517c010000000000000000000000000000000000000000000000000000000063ffffffff85160281526020600482019081526024820183905290819060440184848082843782019150509350505050602060405180830381600087803b151561099057600080fd5b6102c65a03f115156109a157600080fd5b5050506040518051915050801515610a575733600160a060020a0316600080516020610f9583398151915260006040519015158152604060208201819052601c818301527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060608301526080909101905180910390a2600060408051908101604052601c81527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060208201529094509250610c86565b600160a060020a0333166000908152600260205260409020805460ff191660019081179091556003805490918101610a8f8382610e24565b9160005260206000209060040201600060806040519081016040528033600160a060020a031681526020018e8e8080601f016020809104026020016040519081016040528181529291906020840183838082843782019150505050505081526020018c8c8080601f016020809104026020016040519081016040528181529291906020840183838082843782019150505050505081526020018a8a8080601f01602080910402602001604051908101604052818152929190602084018383808284375050509290935250919392508391505051815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101908051610ba4929160200190610e55565b50604082015181600201908051610bbf929160200190610e55565b50606082015181600301908051610bda929160200190610e55565b50506001805481018155600160a060020a0333169250600080516020610f9583398151915291506040519015158152604060208201819052600d818301527f416363657074656420766f74650000000000000000000000000000000000000060608301526080909101905180910390a2600160408051908101604052600d81527f416363657074656420766f746500000000000000000000000000000000000000602082015290945092505b5050965096945050505050565b60015490565b60005433600160a060020a03908116911614610cb457600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f4f70656e656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff0000000000000000000000000000000000000000191674010000000000000000000000000000000000000000179055565b60005433600160a060020a03908116911614610d7c57600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f436c6f73656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff000000000000000000000000000000000000000019169055565b60206040519081016040526000815290565b815481835581811511610e5057600402816004028360005260206000209182019101610e509190610ed3565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e9657805160ff1916838001178555610ec3565b82800160010185558215610ec3579182015b82811115610ec3578251825591602001919060010190610ea8565b50610ecf929150610f33565b5090565b610f3091905b80821115610ecf57805473ffffffffffffffffffffffffffffffffffffffff191681556000610f0b6001830182610f4d565b610f19600283016000610f4d565b610f27600383016000610f4d565b50600401610ed9565b90565b610f3091905b80821115610ecf5760008155600101610f39565b50805460018160011615610100020316600290046000825580601f10610f735750610f91565b601f016020900490600052602060002090810190610f919190610f33565b505600364446e2ab0383d45bdafe114e37a6cca17d5ef7a68d494f8c8f640d25f74f92a165627a7a723058205e454cd6c255f6fcc33cc2fdf864d085abb9ad0e8c07435aaaf1dec8007dbf380029";

    protected Ballot(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ballot(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<VoteEventEventResponse> getVoteEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VoteEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VoteEventEventResponse> responses = new ArrayList<VoteEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VoteEventEventResponse typedResponse = new VoteEventEventResponse();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VoteEventEventResponse> voteEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VoteEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VoteEventEventResponse>() {
            @Override
            public VoteEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VoteEventEventResponse typedResponse = new VoteEventEventResponse();
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<ChangeEventEventResponse> getChangeEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ChangeEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ChangeEventEventResponse> responses = new ArrayList<ChangeEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ChangeEventEventResponse typedResponse = new ChangeEventEventResponse();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeEventEventResponse> changeEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ChangeEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeEventEventResponse>() {
            @Override
            public ChangeEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChangeEventEventResponse typedResponse = new ChangeEventEventResponse();
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> getProposedQuestion() {
        Function function = new Function("getProposedQuestion", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<String, String, String, byte[]>> getVote(BigInteger index) {
        final Function function = new Function("getVote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteCall<Tuple4<String, String, String, byte[]>>(
                new Callable<Tuple4<String, String, String, byte[]>>() {
                    @Override
                    public Tuple4<String, String, String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple4<String, String, String, byte[]>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> destroy() {
        Function function = new Function(
                "destroy", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> vote(String ciphertext, String proof, byte[] random) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(ciphertext), 
                new org.web3j.abi.datatypes.Utf8String(proof), 
                new org.web3j.abi.datatypes.DynamicBytes(random)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getTotalVotes() {
        Function function = new Function("getTotalVotes", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> openVoting() {
        Function function = new Function(
                "openVoting", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> closeVoting() {
        Function function = new Function(
                "closeVoting", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String question, String zkVerificator) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(question), 
                new org.web3j.abi.datatypes.Address(zkVerificator)));
        return deployRemoteCall(Ballot.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String question, String zkVerificator) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(question), 
                new org.web3j.abi.datatypes.Address(zkVerificator)));
        return deployRemoteCall(Ballot.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Ballot load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Ballot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class VoteEventEventResponse {
        public String _from;

        public Boolean wasSuccessful;

        public String reason;
    }

    public static class ChangeEventEventResponse {
        public String _from;

        public Boolean wasSuccessful;

        public String reason;
    }
}
