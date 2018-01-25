package org.provotum.backend.socket.message.vote;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.ResponseType;
import org.provotum.backend.socket.message.base.Status;

public class OpenVoteResponse extends AResponse {

    private String transaction;
    private final ResponseType responseType = ResponseType.OPEN_VOTE;

    public OpenVoteResponse(String id, Status status, String message, String transaction) {
        super(id, status, message);
        this.transaction = transaction;
    }

    public OpenVoteResponse(Status status, String message, String transaction) {
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
