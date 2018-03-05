package org.provotum.backend.communication.socket.message.meta;

import junit.framework.TestCase;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class GetResultResponseTest extends TestCase {

    public void testAccessors1() {
        GetQuestionResponse response = new GetQuestionResponse("1", Status.SUCCESS, "message", "question");

        assertEquals("1", response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("question", response.getQuestion());
        assertEquals(ResponseType.GET_QUESTION_EVENT, response.getResponseType());
    }

    public void testAccessors2() {
        GetQuestionResponse response = new GetQuestionResponse(Status.SUCCESS, "message", "question");

        assertNotNull(response.getId());
        assertEquals(Status.SUCCESS, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("question", response.getQuestion());
        assertEquals(ResponseType.GET_QUESTION_EVENT, response.getResponseType());
    }
}
