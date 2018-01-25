package org.provotum.backend.socket.message.deployment;

import org.provotum.backend.socket.message.base.ResponseType;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.partial.Contract;

public class BallotDeploymentResponse extends ADeploymentResponse {

    private final ResponseType responseType = ResponseType.BALLOT_DEPLOYED;

    public BallotDeploymentResponse(String id, Status status, String message, Contract contract) {
        super(id, status, message, contract);
    }

    public BallotDeploymentResponse(Status status, String message, Contract contract) {
        super(status, message, contract);
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
