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
    private static final String BINARY = "606060405234156200001057600080fd5b6040516200106038038062001060833981016040528080518201919060200180516000805460a060020a60ff02191690559150600490508280516200005a929160200190620000a5565b5060006200006a6003826200012a565b506000600181905560058054600160a060020a0319908116600160a060020a0394851617909155815416339092169190911790555062000212565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e857805160ff191683800117855562000118565b8280016001018555821562000118579182015b8281111562000118578251825591602001919060010190620000fb565b50620001269291506200015e565b5090565b81548183558181151162000159576003028160030283600052602060002091820191016200015991906200017e565b505050565b6200017b91905b8082111562000126576000815560010162000165565b90565b6200017b91905b8082111562000126578054600160a060020a03191681556000620001ad6001830182620001c7565b620001bd600283016000620001c7565b5060030162000185565b50805460018160011615610100020316600290046000825580601f10620001ef57506200020f565b601f0160209004906000526020600020908101906200020f91906200015e565b50565b610e3e80620002226000396000f3006060604052600436106100825763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313ca4fb581146100875780635a55c1f01461011157806383197ef0146102185780639a0e7d661461022d578063affc067014610252578063c631b29214610265578063e8d5940d14610278575b600080fd5b341561009257600080fd5b61009a610322565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156100d65780820151838201526020016100be565b50505050905090810190601f1680156101035780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561011c57600080fd5b6101276004356103cb565b604051600160a060020a0384168152606060208201818152906040830190830185818151815260200191508051906020019080838360005b8381101561017757808201518382015260200161015f565b50505050905090810190601f1680156101a45780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156101da5780820151838201526020016101c2565b50505050905090810190601f1680156102075780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b341561022357600080fd5b61022b61058e565b005b341561023857600080fd5b61024061062d565b60405190815260200160405180910390f35b341561025d57600080fd5b61022b610633565b341561027057600080fd5b61022b6106fb565b341561028357600080fd5b6102a260246004803582810192908201359181359182019101356107ac565b604051821515815260406020820181815290820183818151815260200191508051906020019080838360005b838110156102e65780820151838201526020016102ce565b50505050905090810190601f1680156103135780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b61032a610c7e565b60048054600260001961010060018416150201909116046020601f820181900481020160405190810160405280929190818152602001828054600181600116156101000203166002900480156103c15780601f10610396576101008083540402835291602001916103c1565b820191906000526020600020905b8154815290600101906020018083116103a457829003601f168201915b5050505050905090565b60006103d5610c7e565b6103dd610c7e565b60038054859081106103eb57fe5b60009182526020909120600391820201548154600160a060020a0390911691908690811061041557fe5b906000526020600020906003020160010160016002018681548110151561043857fe5b9060005260206000209060030201600201818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104de5780601f106104b3576101008083540402835291602001916104de565b820191906000526020600020905b8154815290600101906020018083116104c157829003601f168201915b50505050509150808054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561057a5780601f1061054f5761010080835404028352916020019161057a565b820191906000526020600020905b81548152906001019060200180831161055d57829003601f168201915b505050505090509250925092509193909250565b60005433600160a060020a039081169116146105a957600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc600160405190151581526040602082018190526012818301527f44657374726f79656420636f6e7472616374000000000000000000000000000060608301526080909101905180910390a2600054600160a060020a0316ff5b60015490565b60005433600160a060020a0390811691161461064e57600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f4f70656e656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff0000000000000000000000000000000000000000191674010000000000000000000000000000000000000000179055565b60005433600160a060020a0390811691161461071657600080fd5b33600160a060020a03167f34c19dae27a26a14e80ed4fee15359212138891035ac254182dc2f5c439340bc60016040519015158152604060208201819052600d818301527f436c6f73656420766f74696e670000000000000000000000000000000000000060608301526080909101905180910390a26000805474ff000000000000000000000000000000000000000019169055565b60006107b6610c7e565b60008054819074010000000000000000000000000000000000000000900460ff1615156108815733600160a060020a0316600080516020610df3833981519152600060405190151581526040602082018190526010818301527f566f74696e6720697320636c6f7365640000000000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601081527f566f74696e6720697320636c6f7365640000000000000000000000000000000060208201529094509250610c73565b600160a060020a03331660009081526002602052604090205460ff16915081156109495733600160a060020a0316600080516020610df3833981519152600060405190151581526040602082018190526013818301527f566f74657220616c726561647920766f7465640000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601381527f566f74657220616c726561647920766f7465640000000000000000000000000060208201529094509250610c73565b600554600160a060020a0316632640df7787876000604051602001526040517c010000000000000000000000000000000000000000000000000000000063ffffffff85160281526020600482019081526024820183905290819060440184848082843782019150509350505050602060405180830381600087803b15156109cf57600080fd5b6102c65a03f115156109e057600080fd5b5050506040518051915050801515610a965733600160a060020a0316600080516020610df383398151915260006040519015158152604060208201819052601c818301527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060608301526080909101905180910390a2600060408051908101604052601c81527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060208201529094509250610c73565b600160a060020a0333166000908152600260205260409020805460ff191660019081179091556003805490918101610ace8382610c90565b9160005260206000209060030201600060606040519081016040528033600160a060020a031681526020018c8c8080601f016020809104026020016040519081016040528181529291906020840183838082843782019150505050505081526020018a8a8080601f01602080910402602001604051908101604052818152929190602084018383808284375050509290935250919392508391505051815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0391909116178155602082015181600101908051610bac929160200190610cc1565b50604082015181600201908051610bc7929160200190610cc1565b50506001805481018155600160a060020a0333169250600080516020610df383398151915291506040519015158152604060208201819052600d818301527f416363657074656420766f74650000000000000000000000000000000000000060608301526080909101905180910390a2600160408051908101604052600d81527f416363657074656420766f746500000000000000000000000000000000000000602082015290945092505b505094509492505050565b60206040519081016040526000815290565b815481835581811511610cbc57600302816003028360005260206000209182019101610cbc9190610d3f565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d0257805160ff1916838001178555610d2f565b82800160010185558215610d2f579182015b82811115610d2f578251825591602001919060010190610d14565b50610d3b929150610d91565b5090565b610d8e91905b80821115610d3b57805473ffffffffffffffffffffffffffffffffffffffff191681556000610d776001830182610dab565b610d85600283016000610dab565b50600301610d45565b90565b610d8e91905b80821115610d3b5760008155600101610d97565b50805460018160011615610100020316600290046000825580601f10610dd15750610def565b601f016020900490600052602060002090810190610def9190610d91565b505600364446e2ab0383d45bdafe114e37a6cca17d5ef7a68d494f8c8f640d25f74f92a165627a7a7230582055f2c1fd5677608cac19c603a2981deb0e0eac709ac48945d4980d83e4ee85ac0029";

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

    public RemoteCall<Tuple3<String, String, String>> getVote(BigInteger index) {
        final Function function = new Function("getVote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<String, String, String>>(
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
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

    public RemoteCall<TransactionReceipt> vote(String ciphertext, String proof) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(ciphertext), 
                new org.web3j.abi.datatypes.Utf8String(proof)), 
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
