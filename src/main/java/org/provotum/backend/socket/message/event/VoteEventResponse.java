package org.provotum.backend.socket.message.event;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.Status;

public class VoteEventResponse extends AResponse {

    private String senderAddress;

    public VoteEventResponse(String id, Status status, String message, String senderAddress) {
        super(id, status, message);
        this.senderAddress = senderAddress;
    }

    public VoteEventResponse(Status status, String message, String senderAddress) {
        super(status, message);
        this.senderAddress = senderAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }
}
