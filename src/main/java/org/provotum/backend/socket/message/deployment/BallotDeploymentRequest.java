package org.provotum.backend.socket.message.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.socket.message.base.ARequest;
import org.provotum.backend.socket.message.partial.Addresses;
import org.provotum.backend.socket.message.partial.Election;

public class BallotDeploymentRequest extends ARequest {

    private Addresses addresses;
    private Election election;

    public BallotDeploymentRequest(
        @JsonProperty("addresses") Addresses addresses,
        @JsonProperty("election") Election election
    ) {
        this.addresses = addresses;
        this.election = election;
    }

    public Addresses getAddresses() {
        return addresses;
    }

    public Election getElection() {
        return election;
    }
}
