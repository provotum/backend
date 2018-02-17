package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionRequest {

    private int vote;

    public EncryptionRequest(@JsonProperty("vote") int vote) {
        this.vote = vote;
    }

    public int getVote() {
        return vote;
    }
}
