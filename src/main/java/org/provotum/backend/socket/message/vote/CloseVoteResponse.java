package org.provotum.backend.socket.message.vote;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.ResponseType;
import org.provotum.backend.socket.message.base.Status;

public class CloseVoteResponse extends AResponse {

    private String transaction;
    private final ResponseType responseType = ResponseType.CLOSE_VOTE;

    public CloseVoteResponse(String id, Status status, String message, String transaction) {
        super(id, status, message);
        this.transaction = transaction;
    }

    public CloseVoteResponse(Status status, String message, String transaction) {
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
