package com.nttdata.transaction_service.util;

public class TransactionNotFoundException extends RuntimeException {

    private String id = "";

    public TransactionNotFoundException(String id) {
        super("Transaction with ID " + id + " not found.");
        this.id = id;
    }

    public TransactionNotFoundException() {
        super("Not found transactions for current request.");
    }

    public String getUserId() {
        return id;
    }
}
