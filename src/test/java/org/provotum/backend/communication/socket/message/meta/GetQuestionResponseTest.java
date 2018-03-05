package org.provotum.backend.communication.socket.message.meta;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

import java.math.BigInteger;
import java.util.Map;

public class GetQuestionResponseTest extends TestCase {

    public void testAccessors1() {
        GetResultResponse response = new GetResultResponse("1", Status.SUCCESS, "message", BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE);

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());

        Map<String, Map<String, BigInteger>> votes = response.getVotes();
        assertNotNull(votes.get("votes"));
        assertEquals(votes.get("votes").get("yes"), BigInteger.ONE);
        assertEquals(votes.get("votes").get("no"), BigInteger.ZERO);
        assertEquals(votes.get("votes").get("total"), BigInteger.ONE);

        assertEquals(ResponseType.GET_RESULTS_EVENT, response.getResponseType());
    }

    public void testAccessors2() {
        GetResultResponse response = new GetResultResponse(Status.SUCCESS, "message", BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE);

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());

        Map<String, Map<String, BigInteger>> votes = response.getVotes();
        assertNotNull(votes.get("votes"));
        assertEquals(votes.get("votes").get("yes"), BigInteger.ONE);
        assertEquals(votes.get("votes").get("no"), BigInteger.ZERO);
        assertEquals(votes.get("votes").get("total"), BigInteger.ONE);
    }
}
