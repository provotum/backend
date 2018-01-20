package org.provotum.backend.socket.message.deployment;

import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.partial.contract.Contract;

public class ZeroKnowledgeDeploymentResponse extends ADeploymentResponse {

    public ZeroKnowledgeDeploymentResponse(String id, Status status, String message, Contract contract) {
        super(id, status, message, contract);
    }

    public ZeroKnowledgeDeploymentResponse(Status status, String message, Contract contract) {
        super(status, message, contract);
    }
}
