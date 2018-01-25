package org.provotum.backend.communication.socket.message.event;

import org.provotum.backend.communication.message.base.AResponse;
import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;

public class OpenVoteEventResponse extends AResponse {

    private String transaction;
    private final ResponseType responseType = ResponseType.OPEN_VOTE;

    public OpenVoteEventResponse(String id, Status status, String message, String transaction) {
        super(id, status, message);
        this.transaction = transaction;
    }

    public OpenVoteEventResponse(Status status, String message, String transaction) {
        super(status, message);
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
