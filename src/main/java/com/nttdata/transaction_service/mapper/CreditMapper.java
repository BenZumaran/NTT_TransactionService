package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.credit.*;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditMapper {


    public static Product creditResponseDtoToProduct(CreditResponseDTO creditResponseDTO){
        Product product = new Product();
        product.setId(creditResponseDTO.getId());
        product.setBalance(BigDecimal.valueOf(creditResponseDTO.getBalance()));
        switch (creditResponseDTO.getType()){
            //PERSONAL, BUSINESS, CREDIT_CARD
            //personal_credit, business_credit, credit_card
            case "PERSONAL":
                product.setType(ProductType.fromValue("personal_credit"));
                break;
            case "BUSINESS":
                product.setType(ProductType.fromValue("business_credit"));
                break;
            case "CREDIT_CARD":
                product.setType(ProductType.fromValue("credit_card"));
                break;
            default:
                throw  new IllegalArgumentException("No credit product type " + creditResponseDTO.getType()
                        + "valid types: [ personal_credit, business_credit, credit_card ]");
        }
        product.setLimit(BigDecimal.valueOf(creditResponseDTO.getLimit()));
        return product;
    }

}
