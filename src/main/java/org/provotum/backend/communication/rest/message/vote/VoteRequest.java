package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.communication.message.base.ARequest;
import org.provotum.backend.communication.message.partial.Credentials;

import java.math.BigInteger;

@Deprecated
public class VoteRequest extends ARequest {

    private Credentials credentials;
    private int vote;

    public VoteRequest(@JsonProperty("credentials") Credentials credentials, @JsonProperty("vote") int vote) {
        this.credentials = credentials;
        this.vote = vote;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public int getVote() {
        return vote;
    }
}
