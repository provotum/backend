package org.provotum.backend.communication.socket.message.event;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class ProofEventResponseTest extends TestCase {

    public void testAccessors1() {
        ProofEventResponse response = new ProofEventResponse("1", Status.SUCCESS, "message", "senderaddress");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.PROOF_EVENT, response.getResponseType());
    }

    public void testAccessors2() {
        ProofEventResponse response = new ProofEventResponse(Status.SUCCESS, "message", "senderaddress");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.PROOF_EVENT, response.getResponseType());
    }
}
