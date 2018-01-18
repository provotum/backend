package org.provotum.backend.socket.message.ballot;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.socket.message.deployment.ADeploymentRequest;

import java.util.Map;

public class BallotDeploymentRequest extends ADeploymentRequest {

    protected Map<String, Object> addresses;
    protected Map<String, Object> election;

    public BallotDeploymentRequest(
            @JsonProperty("addresses") Map<String, Object> addresses, @JsonProperty("election") Map<String, Object> election) {
        this.addresses = addresses;
        this.election = election;
    }

    public Map<String, Object> getAddresses() {
        return addresses;
    }

    public Map<String, Object> getElection() {
        return election;
    }
}
