package org.provotum.backend.communication.rest.message.vote;

import junit.framework.TestCase;

public class VerifyProofRequestTest extends TestCase {

    public void testAccessors() {
        VerifyProofRequest request = new VerifyProofRequest("ciphertext", "proof");

        assertEquals("ciphertext", request.getCiphertext());
        assertEquals("proof", request.getProof());
    }
}
