package org.provotum.backend.communication.message.partial;

import junit.framework.TestCase;

public class CredentialsTest extends TestCase {

    public void testAccessors() {
        Credentials c = new Credentials("public-key", "private-key");

        assertEquals("public-key", c.getPublicKey());
        assertEquals("private-key", c.getPrivateKey());
    }
}
