package org.provotum.backend.security;

public class CipherTextWrapper {

    private String ciphertext;
    private String proof;
    private byte[] random;

    public CipherTextWrapper(String ciphertext, String proof, byte[] random) {
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

    public byte[] getRandom() {
        return random;
    }
}
