package org.provotum.backend.socket.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.socket.message.base.ARequest;
import org.provotum.backend.socket.message.partial.Credentials;

import java.math.BigInteger;

public class VoteRequest extends ARequest {

    private String contractAddress;
    private Credentials credentials;
    private BigInteger vote;

    public VoteRequest(@JsonProperty("contract-address") String contractAddress, @JsonProperty("credentials") Credentials credentials, @JsonProperty("vote") BigInteger vote) {
        this.contractAddress = contractAddress;
        this.credentials = credentials;
        this.vote = vote;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public BigInteger getVote() {
        return vote;
    }
}
