package org.provotum.backend.socket.message.vote;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.provotum.backend.socket.message.base.ARequest;

public class OpenVoteRequest extends ARequest {

    private String contractAddress;

    public OpenVoteRequest(@JsonProperty("contract-address") String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }
}
