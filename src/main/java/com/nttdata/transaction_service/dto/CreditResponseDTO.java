package com.nttdata.transaction_service.dto;

import lombok.Data;

@Data
public class CreditResponseDTO{
    private String id;
    private String customerId;
    private String type; //[PERSONAL, BUSINESS, CREDIT_CARD]
    private double limit;
    private double balance;
    private double interestAnnual;
    private int dueDate;
    private String status; //[ACTIVE, INACTIVE]
    private CardDTO card;
    private String createdAt;
    private String updatedAt;
}