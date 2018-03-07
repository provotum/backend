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
import org.web3j.tuples.generated.Tuple3;
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
    private static final String BINARY = "606060405234156200001057600080fd5b6040516200157638038062001576833981016040528080518201919060200180516000805460a060020a60ff02191690559150600490508280516200005a929160200190620000a5565b5060006200006a6003826200012a565b506000600181905560088054600160a060020a0319908116600160a060020a0394851617909155815416339092169190911790555062000222565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e857805160ff191683800117855562000118565b8280016001018555821562000118579182015b8281111562000118578251825591602001919060010190620000fb565b50620001269291506200015e565b5090565b81548183558181151162000159576004028160040283600052602060002091820191016200015991906200017e565b505050565b6200017b91905b8082111562000126576000815560010162000165565b90565b6200017b91905b8082111562000126578054600160a060020a03191681556000620001ad6001830182620001d7565b620001bd600283016000620001d7565b620001cd600383016000620001d7565b5060040162000185565b50805460018160011615610100020316600290046000825580601f10620001ff57506200021f565b601f0160209004906000526020600020908101906200021f91906200015e565b50565b61134480620002326000396000f3006060604052600436106100985763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313ca4fb5811461009d5780631cd270a2146101275780635a55c1f0146101c157806383197ef014610332578063834a743d14610345578063946e9493146103fb5780639a0e7d66146104f6578063affc06701461051b578063c631b2921461052e575b600080fd5b34156100a857600080fd5b6100b0610541565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156100ec5780820151838201526020016100d4565b50505050905090810190601f1680156101195780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561013257600080fd5b6101bf600480359060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506105ea95505050505050565b005b34156101cc57600080fd5b6101d7600435610649565b604051600160a060020a03851681526080602082018181529060408301906060840190840187818151815260200191508051906020019080838360005b8381101561022c578082015183820152602001610214565b50505050905090810190601f1680156102595780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561028f578082015183820152602001610277565b50505050905090810190601f1680156102bc5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156102f25780820151838201526020016102da565b50505050905090810190601f16801561031f5780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b341561033d57600080fd5b6101bf6108d6565b341561035057600080fd5b61037b6024600480358281019290820135918135808301929082013591604435918201910135610975565b604051821515815260406020820181815290820183818151815260200191508051906020019080838360005b838110156103bf5780820151838201526020016103a7565b50505050905090810190601f1680156103ec5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561040657600080fd5b61040e610e9b565b604051808481526020018060200180602001838103835285818151815260200191508051906020019080838360005b8381101561045557808201518382015260200161043d565b50505050905090810190601f1680156104825780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156104b85780820151838201526020016104a0565b50505050905090810190601f1680156104e55780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b341561050157600080fd5b610509610ff7565b60405190815260200160405180910390f35b341561052657600080fd5b6101bf610ffd565b341561053957600080fd5b6101bf6110c5565b610549611176565b60048054600260001961010060018416150201909116046020601f820181900481020160405190810160405280929190818152602001828054600181600116156101000203166002900480156105e05780601f106105b5576101008083540402835291602001916105e0565b820191906000526020600020905b8154815290600101906020018083116105c357829003601f168201915b5050505050905090565b60606040519081016040908152848252602082018490528101829052600581518155602082015181600101908051610626929160200190611188565b50604082015181600201908051610641929160200190611188565b505050505050565b6000610653611176565b61065b611176565b610663611176565b600380548690811061067157fe5b600091825260209091206004909102015460038054600160a060020a03909216918790811061069c57fe5b90600052602060002090600402016001016001600201878154811015156106bf57fe5b90600052602060002090600402016002016001600201888154811015156106e257fe5b9060005260206000209060040201600301828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156107885780601f1061075d57610100808354040283529160200191610788565b820191906000526020600020905b81548152906001019060200180831161076b57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108245780601f106107f957610100808354040283529160200191610824565b820191906000526020600020905b81548152906001019060200180831161080757829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108c05780601f10610895576101008083540402835291602001916108c0565b820191906000526020600020905b8154815290600101906020018083116108a357829003601f168201915b5050505050905093509350935093509193509193565b60005433600160a060020a039081169116146108f157600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc600160405190151581526040602082018190526012818301527f44657374726f79656420636f6e7472616374000000000000000000000000000060608301526080909101905180910390a2600054600160a060020a0316ff5b600061097f611176565b60008054819074010000000000000000000000000000000000000000900460ff161515610a4a5733600160a060020a03166000805160206112f9833981519152600060405190151581526040602082018190526010818301527f566f74696e6720697320636c6f7365640000000000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601081527f566f74696e6720697320636c6f7365640000000000000000000000000000000060208201529094509250610e8e565b600160a060020a03331660009081526002602052604090205460ff1691508115610b125733600160a060020a03166000805160206112f9833981519152600060405190151581526040602082018190526013818301527f566f74657220616c726561647920766f7465640000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601381527f566f74657220616c726561647920766f7465640000000000000000000000000060208201529094509250610e8e565b600854600160a060020a0316632640df7789896000604051602001526040517c010000000000000000000000000000000000000000000000000000000063ffffffff85160281526020600482019081526024820183905290819060440184848082843782019150509350505050602060405180830381600087803b1515610b9857600080fd5b6102c65a03f11515610ba957600080fd5b5050506040518051915050801515610c5f5733600160a060020a03166000805160206112f983398151915260006040519015158152604060208201819052601c818301527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060608301526080909101905180910390a2600060408051908101604052601c81527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060208201529094509250610e8e565b600160a060020a0333166000908152600260205260409020805460ff191660019081179091556003805490918101610c978382611206565b9160005260206000209060040201600060806040519081016040528033600160a060020a031681526020018e8e8080601f016020809104026020016040519081016040528181529291906020840183838082843782019150505050505081526020018c8c8080601f016020809104026020016040519081016040528181529291906020840183838082843782019150505050505081526020018a8a8080601f01602080910402602001604051908101604052818152929190602084018383808284375050509290935250919392508391505051815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101908051610dac929160200190611188565b50604082015181600201908051610dc7929160200190611188565b50606082015181600301908051610de2929160200190611188565b50506001805481018155600160a060020a03331692506000805160206112f983398151915291506040519015158152604060208201819052600d818301527f416363657074656420766f74650000000000000000000000000000000000000060608301526080909101905180910390a2600160408051908101604052600d81527f416363657074656420766f746500000000000000000000000000000000000000602082015290945092505b5050965096945050505050565b6000610ea5611176565b610ead611176565b6005546006805491945090600260001961010060018416150201909116046020601f82018190048102016040519081016040528092919081815260200182805460018160011615610100020316600290048015610f4b5780601f10610f2057610100808354040283529160200191610f4b565b820191906000526020600020905b815481529060010190602001808311610f2e57829003601f168201915b5050505050915060056002018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610feb5780601f10610fc057610100808354040283529160200191610feb565b820191906000526020600020905b815481529060010190602001808311610fce57829003601f168201915b50505050509050909192565b60015490565b60005433600160a060020a0390811691161461101857600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f4f70656e656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff0000000000000000000000000000000000000000191674010000000000000000000000000000000000000000179055565b60005433600160a060020a039081169116146110e057600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f436c6f73656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff000000000000000000000000000000000000000019169055565b60206040519081016040526000815290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106111c957805160ff19168380011785556111f6565b828001600101855582156111f6579182015b828111156111f65782518255916020019190600101906111db565b50611202929150611237565b5090565b815481835581811511611232576004028160040283600052602060002091820191016112329190611254565b505050565b61125191905b80821115611202576000815560010161123d565b90565b61125191905b8082111561120257805473ffffffffffffffffffffffffffffffffffffffff19168155600061128c60018301826112b1565b61129a6002830160006112b1565b6112a86003830160006112b1565b5060040161125a565b50805460018160011615610100020316600290046000825580601f106112d757506112f5565b601f0160209004906000526020600020908101906112f59190611237565b505600364446e2ab0383d45bdafe114e37a6cca17d5ef7a68d494f8c8f640d25f74f92a165627a7a72305820dac934c1f94402695e0c1de254ca65e4aa4b04e182d90b7fe8141e2692b3b0860029";

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

    public RemoteCall<TransactionReceipt> setSumProof(BigInteger sum, String ciphertext, String proof) {
        Function function = new Function(
                "setSumProof", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(sum), 
                new org.web3j.abi.datatypes.Utf8String(ciphertext), 
                new org.web3j.abi.datatypes.Utf8String(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteCall<Tuple3<BigInteger, String, String>> getSumProof() {
        final Function function = new Function("getSumProof", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<BigInteger, String, String>>(
                new Callable<Tuple3<BigInteger, String, String>>() {
                    @Override
                    public Tuple3<BigInteger, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<BigInteger, String, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
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
