package org.provotum.ethereum.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

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
    private static final String BINARY = "6060604052341561000f57600080fd5b60008054600160a060020a033316600160a060020a03199091161790556101048061003b6000396000f30060606040526004361060485763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632640df778114604d57806383197ef014607c575b600080fd5b3415605757600080fd5b60686004803560248101910135608e565b604051901515815260200160405180910390f35b3415608657600080fd5b608c6096565b005b600192915050565b6000543373ffffffffffffffffffffffffffffffffffffffff90811691161460bd57600080fd5b60005473ffffffffffffffffffffffffffffffffffffffff16ff00a165627a7a72305820e80b7382ce5ae629160cae269a3722e7ece2d7bcf3331d92eb75d519b6f0b0d70029";

    protected ZeroKnowledgeVerificator(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ZeroKnowledgeVerificator(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Boolean> verifyProof(String vote) {
        Function function = new Function("verifyProof", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(vote)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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
}
