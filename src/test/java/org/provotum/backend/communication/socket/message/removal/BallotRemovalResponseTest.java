package org.provotum.backend.communication.socket.message.removal;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class BallotRemovalResponseTest extends TestCase {

    public void testAccessors1() {
        BallotRemovalResponse response = new BallotRemovalResponse("1", Status.SUCCESS, "message", "transaction");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.BALLOT_REMOVED, response.getResponseType());
    }

    public void testAccessors2() {
        BallotRemovalResponse response = new BallotRemovalResponse(Status.SUCCESS, "message", "transaction");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.BALLOT_REMOVED, response.getResponseType());
    }
}
