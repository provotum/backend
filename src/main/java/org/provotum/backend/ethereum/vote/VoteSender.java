package org.provotum.backend.ethereum.vote;

import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.accessor.BallotContractAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Component
public class VoteSender {

    private BallotContractAccessor ballotContractAccessor;

    @Autowired
    public VoteSender(BallotContractAccessor ballotContractAccessor) {
        this.ballotContractAccessor = ballotContractAccessor;
    }

    /**
     * @param contractAddress The ballot contract address.
     * @param vote            The vote, i.e. 0/1.
     * @param privateKey      The private key on which behalf to vote.
     * @param publicKey       The corresponding public key.
     * @return The transaction hash of the submitted vote.
     * @throws Exception If submitting the vote failed.
     */
    public String vote(String contractAddress, BigInteger vote, String privateKey, String publicKey) throws Exception {
        Credentials credentials = Credentials.create(privateKey, publicKey);

        return this.ballotContractAccessor.vote(contractAddress, vote, credentials);
    }
}
