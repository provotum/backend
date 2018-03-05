package org.provotum.backend.communication.message.partial;

import junit.framework.TestCase;

public class PublicKeyTest extends TestCase{

    public void testAccessors() {
        PublicKey p = new PublicKey(1, 2);

        assertEquals(1, p.getP().intValue());
        assertEquals(2, p.getG().intValue());
    }
}
