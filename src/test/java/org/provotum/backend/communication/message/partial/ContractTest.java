package org.provotum.backend.communication.message.partial;

import junit.framework.TestCase;

public class ContractTest extends TestCase{

    public void testAccessors() {
        Contract c = new Contract("type", "address");

        assertEquals("type", c.getType());
        assertEquals("address", c.getAddress());
    }
}
