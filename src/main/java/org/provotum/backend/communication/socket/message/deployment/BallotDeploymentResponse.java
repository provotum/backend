package org.provotum.backend.communication.socket.message.deployment;

import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.message.partial.Contract;

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
