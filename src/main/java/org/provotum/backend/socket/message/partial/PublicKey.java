package org.provotum.backend.socket.message.partial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKey {

    private Integer p;
    private Integer q;

    public PublicKey(@JsonProperty("p") Integer p, @JsonProperty("q") Integer q) {
        this.p = p;
        this.q = q;
    }

    public Integer getP() {
        return p;
    }

    public Integer getQ() {
        return q;
    }
}
