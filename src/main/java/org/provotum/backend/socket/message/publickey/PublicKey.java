package org.provotum.backend.socket.message.publickey;

public class PublicKey {

    protected Integer p;
    protected Integer q;

    public PublicKey(Integer p, Integer q) {
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
