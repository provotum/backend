package org.provotum.backend.socket.message.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("success")
    SUCCESS,

    @JsonProperty("error")
    ERROR
}
