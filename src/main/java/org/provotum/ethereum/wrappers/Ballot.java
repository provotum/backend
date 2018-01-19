package org.provotum.ethereum.wrappers;

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
import org.web3j.tuples.generated.Tuple2;
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
    private static final String BINARY = "606060405234156200001057600080fd5b60405162000d3438038062000d34833981016040528080518201919060200180516000805460a060020a60ff02191690559150600490508280516200005a929160200190620000a5565b5060006200006a6003826200012a565b506000600181905560058054600160a060020a0319908116600160a060020a0394851617909155815416339092169190911790555062000202565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000e857805160ff191683800117855562000118565b8280016001018555821562000118579182015b8281111562000118578251825591602001919060010190620000fb565b50620001269291506200015e565b5090565b81548183558181151162000159576002028160020283600052602060002091820191016200015991906200017e565b505050565b6200017b91905b8082111562000126576000815560010162000165565b90565b6200017b91905b8082111562000126578054600160a060020a03191681556000620001ad6001830182620001b7565b5060020162000185565b50805460018160011615610100020316600290046000825580601f10620001df5750620001ff565b601f016020900490600052602060002090810190620001ff91906200015e565b50565b610b2280620002126000396000f3006060604052600436106100825763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313ca4fb581146100875780635a55c1f01461011157806383197ef0146101ae5780639a0e7d66146101c3578063affc0670146101e8578063c631b292146101fb578063fc36e15b1461020e575b600080fd5b341561009257600080fd5b61009a61026f565b60405160208082528190810183818151815260200191508051906020019080838360005b838110156100d65780820151838201526020016100be565b50505050905090810190601f1680156101035780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561011c57600080fd5b610127600435610318565b604051600160a060020a038316815260406020820181815290820183818151815260200191508051906020019080838360005b8381101561017257808201518382015260200161015a565b50505050905090810190601f16801561019f5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b34156101b957600080fd5b6101c1610411565b005b34156101ce57600080fd5b6101d661043a565b60405190815260200160405180910390f35b34156101f357600080fd5b6101c1610440565b341561020657600080fd5b6101c1610492565b341561021957600080fd5b61022c60048035602481019101356104cd565b604051821515815260406020820181815290820183818151815260200191508051906020019080838360008381101561017257808201518382015260200161015a565b610277610951565b60048054600260001961010060018416150201909116046020601f8201819004810201604051908101604052809291908181526020018280546001816001161561010002031660029004801561030e5780601f106102e35761010080835404028352916020019161030e565b820191906000526020600020905b8154815290600101906020018083116102f157829003601f168201915b5050505050905090565b6000610322610951565b600380548490811061033057fe5b600091825260209091206002909102015460038054600160a060020a03909216918590811061035b57fe5b9060005260206000209060020201600101808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104015780601f106103d657610100808354040283529160200191610401565b820191906000526020600020905b8154815290600101906020018083116103e457829003601f168201915b5050505050905091509150915091565b60005433600160a060020a0390811691161461042c57600080fd5b600054600160a060020a0316ff5b60015490565b60005433600160a060020a0390811691161461045b57600080fd5b6000805474ff0000000000000000000000000000000000000000191674010000000000000000000000000000000000000000179055565b60005433600160a060020a039081169116146104ad57600080fd5b6000805474ff000000000000000000000000000000000000000019169055565b60006104d7610951565b6000806104e2610963565b60005474010000000000000000000000000000000000000000900460ff1615156105aa5733600160a060020a0316600080516020610ad7833981519152600060405190151581526040602082018190526010818301527f566f74696e6720697320636c6f7365640000000000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601081527f566f74696e6720697320636c6f7365640000000000000000000000000000000060208201529095509350610947565b600160a060020a03331660009081526002602052604090205460ff16925082156106725733600160a060020a0316600080516020610ad7833981519152600060405190151581526040602082018190526013818301527f566f74657220616c726561647920766f7465640000000000000000000000000060608301526080909101905180910390a2600060408051908101604052601381527f566f74657220616c726561647920766f7465640000000000000000000000000060208201529095509350610947565b600554600160a060020a0316632640df7788886000604051602001526040517c010000000000000000000000000000000000000000000000000000000063ffffffff85160281526020600482019081526024820183905290819060440184848082843782019150509350505050602060405180830381600087803b15156106f857600080fd5b6102c65a03f1151561070957600080fd5b50505060405180519250508115156107bf5733600160a060020a0316600080516020610ad783398151915260006040519015158152604060208201819052601c818301527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060608301526080909101905180910390a2600060408051908101604052601c81527f496e76616c6964207a65726f206b6e6f776c656467652070726f6f660000000060208201529095509350610947565b604080519081016040528033600160a060020a0316815260200188888080601f016020809104026020016040519081016040528181529291906020840183838082843750505092909352505050600160a060020a0333166000908152600260205260409020805460ff1916600190811790915560038054929350919081016108478382610982565b600092835260209092208391600202018151815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039190911617815560208201518160010190805161089b9291602001906109b3565b50506001805481018155600160a060020a0333169250600080516020610ad783398151915291506040519015158152604060208201819052600d818301527f416363657074656420766f74650000000000000000000000000000000000000060608301526080909101905180910390a2600160408051908101604052600d81527f416363657074656420766f746500000000000000000000000000000000000000602082015290955093505b5050509250929050565b60206040519081016040526000815290565b60408051908101604052600081526020810161097d610951565b905290565b8154818355818115116109ae576002028160020283600052602060002091820191016109ae9190610a31565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109f457805160ff1916838001178555610a21565b82800160010185558215610a21579182015b82811115610a21578251825591602001919060010190610a06565b50610a2d929150610a75565b5090565b610a7291905b80821115610a2d57805473ffffffffffffffffffffffffffffffffffffffff191681556000610a696001830182610a8f565b50600201610a37565b90565b610a7291905b80821115610a2d5760008155600101610a7b565b50805460018160011615610100020316600290046000825580601f10610ab55750610ad3565b601f016020900490600052602060002090810190610ad39190610a75565b50560089430c5ae9dcf84a1dd7d4126d450cab68c1a206c3b5f96e74deb4efba920a7ea165627a7a7230582023053c56f99f4930d551406efd7443ab5213066ec396828d945909e873937f350029";

    protected Ballot(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ballot(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<VoteResultEventResponse> getVoteResultEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("VoteResult", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<VoteResultEventResponse> responses = new ArrayList<VoteResultEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            VoteResultEventResponse typedResponse = new VoteResultEventResponse();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VoteResultEventResponse> voteResultEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("VoteResult", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, VoteResultEventResponse>() {
            @Override
            public VoteResultEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                VoteResultEventResponse typedResponse = new VoteResultEventResponse();
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

    public RemoteCall<Tuple2<String, String>> getVote(BigInteger index) {
        final Function function = new Function("getVote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);;
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
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

    public RemoteCall<TransactionReceipt> vote(String chosenVote) {
        Function function = new Function(
                "vote", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(chosenVote)), 
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

    public static class VoteResultEventResponse {
        public String _from;

        public Boolean wasSuccessful;

        public String reason;
    }
}
