package org.provotum.backend.communication.rest.message.deployment;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.communication.message.base.ARequest;
import org.provotum.backend.communication.message.partial.Addresses;
import org.provotum.backend.communication.message.partial.Election;

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
