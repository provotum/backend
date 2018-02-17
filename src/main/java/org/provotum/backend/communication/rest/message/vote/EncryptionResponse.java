package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.DatatypeConverter;

public class EncryptionResponse {

    private String ciphertext;
    private String proof;
    private byte[] random;

    public EncryptionResponse(@JsonProperty("ciphertext") String ciphertext, @JsonProperty("proof") String proof, @JsonProperty("random") byte[] random) {
        this.ciphertext = ciphertext;
        this.proof = proof;
        this.random = random;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public String getProof() {
        return proof;
    }

    public String getRandom() {
        return "0x" + DatatypeConverter.printHexBinary(this.random);
    }
}
