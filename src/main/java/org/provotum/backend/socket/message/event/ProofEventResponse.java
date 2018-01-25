package org.provotum.backend.socket.message.event;

import org.provotum.backend.socket.message.base.AResponse;
import org.provotum.backend.socket.message.base.ResponseType;
import org.provotum.backend.socket.message.base.Status;

public class ProofEventResponse extends AResponse {

    private String senderAddress;
    private final ResponseType responseType = ResponseType.PROOF_EVENT;

    public ProofEventResponse(String id, Status status, String message, String senderAddress) {
        super(id, status, message);
        this.senderAddress = senderAddress;
    }

    public ProofEventResponse(Status status, String message, String senderAddress) {
        super(status, message);
        this.senderAddress = senderAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
