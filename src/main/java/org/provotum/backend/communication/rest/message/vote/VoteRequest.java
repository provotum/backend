package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.communication.message.base.ARequest;
import org.provotum.backend.communication.message.partial.Credentials;

import java.math.BigInteger;

public class VoteRequest extends ARequest {

    private Credentials credentials;
    private BigInteger vote;

    public VoteRequest(@JsonProperty("credentials") Credentials credentials, @JsonProperty("vote") BigInteger vote) {
        this.credentials = credentials;
        this.vote = vote;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public BigInteger getVote() {
        return vote;
    }
}
