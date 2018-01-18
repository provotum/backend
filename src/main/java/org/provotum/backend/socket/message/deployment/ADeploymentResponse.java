package org.provotum.backend.socket.message.deployment;

import java.util.HashMap;
import java.util.Map;

public abstract class ADeploymentResponse {

    protected String address;
    protected Map<String, Object> error;

    public ADeploymentResponse(String address, Map<String, Object> error) {
        this.address = address;
        this.error = error;
    }

    public ADeploymentResponse(String address) {
        this.address = address;
        this.error = new HashMap<>();
    }

    public String getAddress() {
        return address;
    }

    public Map<String, Object> getError() {
        return error;
    }
}
