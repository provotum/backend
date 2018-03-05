package org.provotum.backend.communication.socket.message.event;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class ChangeEventResponseTest extends TestCase {

    public void testAccessors1() {
        ChangeEventResponse response = new ChangeEventResponse("1", Status.SUCCESS, "message", "senderaddress");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.CHANGE_EVENT, response.getResponseType());
    }

    public void testAccessors2() {
        ChangeEventResponse response = new ChangeEventResponse(Status.SUCCESS, "message", "senderaddress");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.CHANGE_EVENT, response.getResponseType());
    }
}
