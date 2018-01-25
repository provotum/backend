package org.provotum.backend.communication.socket.message.deployment;

import org.provotum.backend.communication.message.base.ResponseType;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.message.partial.Contract;

public class ZeroKnowledgeDeploymentResponse extends ADeploymentResponse {

    private final ResponseType responseType = ResponseType.ZERO_KNOWLEDGE_DEPLOYED;

    public ZeroKnowledgeDeploymentResponse(String id, Status status, String message, Contract contract) {
        super(id, status, message, contract);
    }

    public ZeroKnowledgeDeploymentResponse(Status status, String message, Contract contract) {
        super(status, message, contract);
    }

    public ResponseType getResponseType() {
        return responseType;
    }
}
