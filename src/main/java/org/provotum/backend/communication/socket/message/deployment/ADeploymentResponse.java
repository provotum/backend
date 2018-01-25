package org.provotum.backend.communication.socket.message.deployment;

import org.provotum.backend.communication.message.base.AResponse;
import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.message.partial.Contract;

public abstract class ADeploymentResponse extends AResponse {

    private Contract contract;

    public ADeploymentResponse(String id, Status status, String message, Contract contract) {
        super(id, status, message);
        this.contract = contract;
    }

    public ADeploymentResponse(Status status, String message, Contract contract) {
        super(status, message);
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }

}
