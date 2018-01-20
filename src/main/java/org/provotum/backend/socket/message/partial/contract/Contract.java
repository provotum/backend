package org.provotum.backend.socket.message.partial.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contract {

    private String type;
    private String address;

    public Contract(@JsonProperty("type") String type, @JsonProperty("address") String address) {
        this.type = type;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}
