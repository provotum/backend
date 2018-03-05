package org.provotum.backend.communication.socket.message.removal;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class ZeroKnowledgeRemovalResponseTest extends TestCase {

    public void testAccessors1() {
        ZeroKnowledgeRemovalResponse response = new ZeroKnowledgeRemovalResponse("1", Status.SUCCESS, "message", "transaction");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.ZERO_KNOWLEDGE_REMOVED, response.getResponseType());
    }

    public void testAccessors2() {
        ZeroKnowledgeRemovalResponse response = new ZeroKnowledgeRemovalResponse(Status.SUCCESS, "message", "transaction");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("transaction", response.getTransaction());
        assertEquals(ResponseType.ZERO_KNOWLEDGE_REMOVED, response.getResponseType());
    }
}
