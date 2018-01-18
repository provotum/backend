package org.provotum.backend.socket.message.zeroknowledge;

import org.provotum.backend.socket.message.deployment.ADeploymentRequest;
import org.provotum.backend.socket.message.publickey.PublicKey;

public class ZeroKnowledgeDeploymentRequest extends ADeploymentRequest {

    protected PublicKey publicKey;

    public ZeroKnowledgeDeploymentRequest(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
