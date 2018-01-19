package org.provotum.backend.socket.message.ballot;

import org.provotum.backend.socket.message.contract.Contract;
import org.provotum.backend.socket.message.deployment.ADeploymentResponse;

public class BallotDeploymentResponse extends ADeploymentResponse {

    public BallotDeploymentResponse(String status, String message, Contract contract) {
        super(status, message, contract);
    }
}
