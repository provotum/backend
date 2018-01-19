package org.provotum.backend.socket.message.publickey;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicKey {

    protected Integer p;
    protected Integer q;

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
