package org.provotum.backend.socket.message.partial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Addresses {

    private String zeroKnowledge;

    public Addresses(@JsonProperty("zero-knowledge") String zeroKnowledge) {
        this.zeroKnowledge = zeroKnowledge;
    }

    public String getZeroKnowledge() {
        return zeroKnowledge;
    }
}
