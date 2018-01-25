package org.provotum.backend.communication.message.partial;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Election {

    private String question;
    private PublicKey publicKey;

    public Election(@JsonProperty("question") String question, @JsonProperty("public-key") PublicKey publicKey) {
        this.question = question;
        this.publicKey = publicKey;
    }

    public String getQuestion() {
        return question;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
