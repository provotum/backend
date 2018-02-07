package org.provotum.backend.ethereum.base;

public enum TransactionReceiptStatus {

    SUCCESS("0x1"), ERROR("0x0");

    private String value;

    TransactionReceiptStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
