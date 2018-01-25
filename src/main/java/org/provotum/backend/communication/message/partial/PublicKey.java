package org.provotum.backend.communication.message.partial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKey {

    private Integer p;
    private Integer g;

    public PublicKey(@JsonProperty("p") Integer p, @JsonProperty("g") Integer g) {
        this.p = p;
        this.g = g;
    }

    public Integer getP() {
        return p;
    }

    public Integer getG() {
        return g;
    }
}
