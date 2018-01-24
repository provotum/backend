package org.provotum.backend.socket.message.vote;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.Status;

public class VoteResponse extends AResponse {

    private String transaction;

    public VoteResponse(String id, Status status, String message, String transaction) {
        super(id, status, message);
        this.transaction = transaction;
    }

    public VoteResponse(Status status, String message, String transaction) {
        super(status, message);
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }
}
