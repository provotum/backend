package org.provotum.backend.socket.message.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.socket.message.base.ARequest;
import org.provotum.backend.socket.message.partial.PublicKey;


public class ZeroKnowledgeDeploymentRequest extends ARequest {

    private PublicKey publicKey;

    public ZeroKnowledgeDeploymentRequest(@JsonProperty("public-key") PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
