package org.provotum.backend.communication.socket.message.state;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class OpenEventResponseTest extends TestCase {

    public void testAccessors1() {
        OpenVoteEventResponse response = new OpenVoteEventResponse("1", Status.SUCCESS, "message", "transaction");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.OPEN_VOTE, response.getResponseType());
    }

    public void testAccessors2() {
        OpenVoteEventResponse response = new OpenVoteEventResponse(Status.SUCCESS, "message", "transaction");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.OPEN_VOTE, response.getResponseType());
    }
}
