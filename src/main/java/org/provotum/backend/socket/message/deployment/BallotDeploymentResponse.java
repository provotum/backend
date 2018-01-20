package org.provotum.backend.socket.message.deployment;

import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.partial.contract.Contract;

public class BallotDeploymentResponse extends ADeploymentResponse {

    public BallotDeploymentResponse(String id, Status status, String message, Contract contract) {
        super(id, status, message, contract);
    }

    public BallotDeploymentResponse(Status status, String message, Contract contract) {
        super(status, message, contract);
    }
}
