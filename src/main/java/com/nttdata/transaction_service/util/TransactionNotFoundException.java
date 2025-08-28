package com.nttdata.transaction_service.util;

public class TransactionNotFoundException extends RuntimeException {
    private final String id;

    public TransactionNotFoundException(String id) {
        super("Transaction with ID " + id + " not found.");
        this.id = id;
    }

    public String getUserId() {
        return id;
    }
}
