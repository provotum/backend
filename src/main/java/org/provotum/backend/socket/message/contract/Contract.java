package org.provotum.backend.socket.message.contract;

public class Contract {

    private String type;
    private String address;

    public Contract(String type, String address) {
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
