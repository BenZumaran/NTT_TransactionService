package com.nttdata.transaction_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountResponseDTO{
    private String id;
    private String accountNumber;
    private String interbankNumber;
    private String holderDocument;
    private List<String> authorizedSigners;
    private String accountType; // [SAVINGS, CHECKING, FIXED_TERM]
    private double balance;
    private double interestRate;
    private int monthlyMovementLimit; // SAVINGS
    private double maintenanceFee; // CHECKING
    private int allowedDayOfMonth; // FIXED_TERM
    private String creationDate;
    private boolean active;
    private CardDTO card;

}