package org.provotum.backend.socket.message.zeroknowledge;

import org.provotum.backend.socket.message.deployment.ADeploymentResponse;

import java.util.Map;

public class ZeroKnowledgeDeploymentResponse extends ADeploymentResponse {

    public ZeroKnowledgeDeploymentResponse(String address, Map<String, Object> error) {
        super(address, error);
    }

    public ZeroKnowledgeDeploymentResponse(String address) {
        super(address);
    }
}
