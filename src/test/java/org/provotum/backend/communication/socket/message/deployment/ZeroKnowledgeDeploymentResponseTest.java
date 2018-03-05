package org.provotum.backend.communication.socket.message.deployment;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.message.partial.Contract;

public class ZeroKnowledgeDeploymentResponseTest extends TestCase {

    public void testAccessors1() {
        Contract c = new Contract("type", "address");
        ZeroKnowledgeDeploymentResponse response = new ZeroKnowledgeDeploymentResponse("1", Status.SUCCESS, "message", c);

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(c, response.getContract());
        assertEquals(ResponseType.ZERO_KNOWLEDGE_DEPLOYED, response.getResponseType());
    }

    public void testAccessors2() {
        Contract c = new Contract("type", "address");
        ZeroKnowledgeDeploymentResponse response = new ZeroKnowledgeDeploymentResponse(Status.SUCCESS, "message", c);

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(c, response.getContract());
        assertEquals(ResponseType.ZERO_KNOWLEDGE_DEPLOYED, response.getResponseType());
    }
}
