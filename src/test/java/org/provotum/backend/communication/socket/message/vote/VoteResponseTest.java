package org.provotum.backend.communication.socket.message.vote;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.socket.message.state.OpenVoteEventResponse;

public class VoteResponseTest extends TestCase {

    public void testAccessors1() {
        VoteResponse response = new VoteResponse("1", Status.SUCCESS, "message", "transaction");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.VOTE, response.getResponseType());
    }

    public void testAccessors2() {
        VoteResponse response = new VoteResponse(Status.SUCCESS, "message", "transaction");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.VOTE, response.getResponseType());
    }
}
