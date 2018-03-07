package org.provotum.backend.communication.rest.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class VerifySumProofRequest {

    private BigInteger sum;
    private String ciphertext;
    private String proof;

    public VerifySumProofRequest(@JsonProperty("sum") BigInteger sum, @JsonProperty("ciphertext") String ciphertext, @JsonProperty("proof") String proof) {
        this.sum = sum;
        this.ciphertext = ciphertext;
        this.proof = proof;
    }

    public BigInteger getSum() {
        return sum;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public String getProof() {
        return proof;
    }
}
