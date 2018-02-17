package org.provotum.backend.security;

public class CipherTextWrapper {

    private String ciphertext;
    private String proof;

    public CipherTextWrapper(String ciphertext, String proof) {
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
