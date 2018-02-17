package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionResponse {

    private String ciphertext;
    private String proof;

    public EncryptionResponse(@JsonProperty("ciphertext") String ciphertext, @JsonProperty("proof") String proof) {
        this.ciphertext = ciphertext;
        this.proof = proof;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public String getProof() {
        return proof;
    }
}
