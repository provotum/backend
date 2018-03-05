package org.provotum.backend.communication.socket.message.deployment;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.message.partial.Contract;

public class BallotDeploymentResponseTest extends TestCase {

    public void testAccessors1() {
        Contract c = new Contract("type", "address");
        BallotDeploymentResponse response = new BallotDeploymentResponse("1", Status.SUCCESS, "message", c);

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(c, response.getContract());
        assertEquals(ResponseType.BALLOT_DEPLOYED, response.getResponseType());
    }

    public void testAccessors2() {
        Contract c = new Contract("type", "address");
        BallotDeploymentResponse response = new BallotDeploymentResponse(Status.SUCCESS, "message", c);

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals(c, response.getContract());
        assertEquals(ResponseType.BALLOT_DEPLOYED, response.getResponseType());
    }
}
