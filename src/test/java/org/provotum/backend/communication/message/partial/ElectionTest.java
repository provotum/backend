package org.provotum.backend.communication.message.partial;

import junit.framework.TestCase;

public class ElectionTest extends TestCase {

    public void testAccessors() {
        PublicKey p = new PublicKey(1, 2);
        Election e = new Election("question", p);

        assertEquals("question", e.getQuestion());
        assertEquals(p, e.getPublicKey());
    }
}
