package org.provotum.backend.socket.message.event;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.Status;

public class ChangeEventResponse extends AResponse {

    private String senderAddress;

    public ChangeEventResponse(String id, Status status, String message, String senderAddress) {
        super(id, status, message);
        this.senderAddress = senderAddress;
    }

    public ChangeEventResponse(Status status, String message, String senderAddress) {
        super(status, message);
        this.senderAddress = senderAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }
}