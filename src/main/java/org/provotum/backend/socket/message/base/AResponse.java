package org.provotum.backend.socket.message.base;

import java.util.UUID;

public abstract class AResponse {

    private String id;
    private Status status;
    private String message;

    public AResponse(String id, Status status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

    public AResponse(Status status, String message) {
        this.id = UUID.randomUUID().toString();
        this.status = status;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
