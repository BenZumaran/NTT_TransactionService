package com.nttdata.transaction_service.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CardEntity {
    private String id;
    private String cardNumber;
    private String status;
    private boolean isVirtual;
    private LocalDateTime creationDate;
    private String cardType;
    private String brand;

}