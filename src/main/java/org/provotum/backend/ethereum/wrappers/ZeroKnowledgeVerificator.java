package org.provotum.backend.ethereum.wrappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class ZeroKnowledgeVerificator extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60008054600160a060020a033316600160a060020a03199091161790556101948061003b6000396000f30060606040526004361061004b5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632640df77811461005057806383197ef014610082575b600080fd5b341561005b57600080fd5b61006e6004803560248101910135610097565b604051901515815260200160405180910390f35b341561008d57600080fd5b610095610125565b005b60003373ffffffffffffffffffffffffffffffffffffffff167f3ab79b88c50f8ffc33434e138ced1d502481ae1fa32201035b207cdac2bbf2e5600160405190151581526040602082018190526014818301527f50726f6f6620776173207375636365737366756c00000000000000000000000060608301526080909101905180910390a250600192915050565b6000543373ffffffffffffffffffffffffffffffffffffffff90811691161461014d57600080fd5b60005473ffffffffffffffffffffffffffffffffffffffff16ff00a165627a7a7230582048ecee2547de464ffec1b07e225f8d1a9a83726cfdcb0639faf011a191f5982e0029";

    protected ZeroKnowledgeVerificator(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ZeroKnowledgeVerificator(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<ProofEventEventResponse> getProofEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("ProofEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ProofEventEventResponse> responses = new ArrayList<ProofEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ProofEventEventResponse typedResponse = new ProofEventEventResponse();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ProofEventEventResponse> proofEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("ProofEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ProofEventEventResponse>() {
            @Override
            public ProofEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ProofEventEventResponse typedResponse = new ProofEventEventResponse();
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.wasSuccessful = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> verifyProof(String proof) {
        Function function = new Function(
                "verifyProof", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> destroy() {
        Function function = new Function(
                "destroy", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<ZeroKnowledgeVerificator> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ZeroKnowledgeVerificator.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ZeroKnowledgeVerificator> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ZeroKnowledgeVerificator.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ZeroKnowledgeVerificator load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZeroKnowledgeVerificator(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ZeroKnowledgeVerificator load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ZeroKnowledgeVerificator(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ProofEventEventResponse {
        public String _from;

        public Boolean wasSuccessful;

        public String reason;
    }
}
