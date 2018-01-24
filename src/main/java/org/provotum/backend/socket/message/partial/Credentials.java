package org.provotum.backend.socket.message.partial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {

    private String publicKey;
    private String privateKey;

    public Credentials(@JsonProperty("public-key") String publicKey, @JsonProperty("private-key") String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
