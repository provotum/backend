package org.provotum.backend.socket.message.zeroknowledge;

import org.provotum.backend.socket.message.contract.Contract;
import org.provotum.backend.socket.message.deployment.ADeploymentResponse;

public class ZeroKnowledgeDeploymentResponse extends ADeploymentResponse {

    public ZeroKnowledgeDeploymentResponse(String status, String message, Contract contract) {
        super(status, message, contract);
    }
}
