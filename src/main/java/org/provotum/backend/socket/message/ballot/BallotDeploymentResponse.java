package org.provotum.backend.socket.message.ballot;

import org.provotum.backend.socket.message.deployment.ADeploymentResponse;

import java.util.Map;

public class BallotDeploymentResponse extends ADeploymentResponse {

    public BallotDeploymentResponse(String address, Map<String, Object> error) {
        super(address, error);
    }

    public BallotDeploymentResponse(String address) {
        super(address);
    }
}
