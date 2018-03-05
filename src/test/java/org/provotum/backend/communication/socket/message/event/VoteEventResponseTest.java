package org.provotum.backend.communication.socket.message.event;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class VoteEventResponseTest extends TestCase {

    public void testAccessors1() {
        VoteEventResponse response = new VoteEventResponse("1", Status.SUCCESS, "message", "senderaddress");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.VOTE_EVENT, response.getResponseType());
    }

    public void testAccessors2() {
        VoteEventResponse response = new VoteEventResponse(Status.SUCCESS, "message", "senderaddress");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("senderaddress", response.getSenderAddress());
        assertEquals(ResponseType.VOTE_EVENT, response.getResponseType());
    }
}
