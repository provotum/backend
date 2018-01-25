package org.provotum.backend.communication.socket.message.meta;

import org.provotum.backend.communication.message.base.AResponse;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class GetQuestionResponse extends AResponse {

    private String question;
    private final ResponseType responseType = ResponseType.GET_QUESTION_EVENT;

    public GetQuestionResponse(String id, Status status, String message, String question) {
        super(id, status, message);
        this.question = question;
    }

    public GetQuestionResponse(Status status, String message, String question) {
        super(status, message);
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
