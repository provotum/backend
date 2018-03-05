package org.provotum.backend.communication.rest.message.vote;

import junit.framework.TestCase;

public class EncryptionResponseTest extends TestCase {

    public void testAccessors() {
        byte[] random = new byte[2];
        random[0] = 0x1;
        random[1] = 0x2;

        EncryptionResponse response = new EncryptionResponse("ciphertext", "proof", random);

        assertEquals("ciphertext", response.getCiphertext());
        assertEquals("proof", response.getProof());
        assertEquals("0x0102", response.getRandom());
    }
}
