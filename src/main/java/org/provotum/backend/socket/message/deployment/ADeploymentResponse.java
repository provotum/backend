package org.provotum.backend.socket.message.deployment;

import org.provotum.backend.socket.message.contract.Contract;

import java.util.UUID;

public abstract class ADeploymentResponse {

    private String status;
    private String message;
    private Contract contract;
    private String id;

    public ADeploymentResponse(String status, String message, Contract contract) {
        this.status = status;
        this.message = message;
        this.contract = contract;
        this.id = UUID.randomUUID().toString();
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Contract getContract() {
        return contract;
    }

    public String getId() {
        return id;
    }
}
