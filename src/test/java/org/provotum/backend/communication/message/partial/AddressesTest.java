package org.provotum.backend.communication.message.partial;

import junit.framework.TestCase;

public class AddressesTest extends TestCase{


    public void testAccessors() {
        Addresses a = new Addresses("zero-knowledge");

        assertEquals("zero-knowledge", a.getZeroKnowledge());
    }
}
