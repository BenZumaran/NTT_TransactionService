package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.credit.*;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.entity.ClientEntity;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditMapper {

    public static CreditCreateDTO transactionPostToCreditCreateDto(TransactionPost transactionPost){
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setId("97qu3ibg9puhgq3a94hq394");
        creditCardDTO.setBrand("VISA");
        creditCardDTO.setLast4("9999");

        CreditCreateDTO creditCreateDTO = new CreditCreateDTO();
        switch (transactionPost.getProduct().getType().getValue()){
            //PERSONAL, BUSINESS, CREDIT_CARD
            //personal_credit, business_credit, credit_card
            case "personal_credit":
                creditCreateDTO.setType("PERSONAL");
                break;
            case "business_credit":
                creditCreateDTO.setType("BUSINESS");
                break;
            case "credit_card":
                creditCreateDTO.setType("CREDIT_CARD");
                break;
            default:
                throw  new IllegalArgumentException("No credit product type " + transactionPost.getType().getValue()
                        + "valid types: [ personal_credit, business_credit, credit_card ]");
        }
        creditCreateDTO.setCard(creditCardDTO);
        creditCreateDTO.setLimit(transactionPost.getAmount().doubleValue());
        creditCreateDTO.setCustomerId(transactionPost.getClient().getId());
        creditCreateDTO.setDueDate(LocalDate.now());
        creditCreateDTO.setInterestAnnual(110.5);

        return creditCreateDTO;
    }

    public static Transaction creditResponseDtoTransactionPostToTransaction(
            CreditResponseDTO creditResponseDTO, TransactionPost transactionPost){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(transactionPost.getClient().getId());
        clientEntity.setType(transactionPost.getClient().getType().getValue());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(creditResponseDTO.getId());
        productEntity.setBalance(creditResponseDTO.getBalance());
        productEntity.setLimit(creditResponseDTO.getLimit());

        Transaction transaction = new Transaction();
        transaction.setType(transactionPost.getType().getValue());
        transaction.setClient(clientEntity);
        transaction.setProduct(productEntity);
        if (transactionPost.getAmount() != null)
            transaction.setAmount(transactionPost.getAmount().doubleValue());
        if(transactionPost.getHolder() != null)
            transaction.setHolder(PersonMapper.personToPersonEntity(transactionPost.getHolder()));
        if(transactionPost.getSignatory() != null)
            transaction.setSignatory(PersonMapper.personToPersonEntity(transactionPost.getSignatory()));
        transaction.setCreatedDate(LocalDateTime.now());

        return transaction;
    }

    public static CreditPaymentDTO transactionPostToCreditPaymentDto(TransactionPost transactionPost){
        CreditPaymentDTO creditPaymentDTO = new CreditPaymentDTO();
        creditPaymentDTO.setAmount(transactionPost.getAmount().doubleValue());
        creditPaymentDTO.setNote("Payment amount: S/." + transactionPost.getAmount()
                + " from cliend ID ---> " + transactionPost.getClient().getId()
                + "at date-time: " + LocalDateTime.now() );
        return creditPaymentDTO;
    }

    public static CreditChargeDTO transactionPostToCreditCharge(TransactionPost transactionPost){
        CreditChargeDTO creditChargeDTO = new CreditChargeDTO();
        creditChargeDTO.setAmount(transactionPost.getAmount().doubleValue());
        creditChargeDTO.setMerchant("VISA");
        creditChargeDTO.setNote("Payment amount: S/." + transactionPost.getAmount()
                + " from cliend ID ---> " + transactionPost.getClient().getId()
                + "at date-time: " + LocalDateTime.now() );
        return creditChargeDTO;
    }

    public static Transaction creditUpdateResponseDtoTransactionPostToTransaction(
            CreditUpdateResponseDTO creditUpdateResponseDTO, TransactionPost transactionPost){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(transactionPost.getClient().getId());
        clientEntity.setType(transactionPost.getClient().getType().getValue());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(transactionPost.getProduct().getId());
        productEntity.setBalance(creditUpdateResponseDTO.getBalance());
        productEntity.setLimit(creditUpdateResponseDTO.getLimit());
        productEntity.setType(transactionPost.getProduct().getType().getValue());

        Transaction transaction = new Transaction();
        transaction.setType(transactionPost.getType().getValue());
        transaction.setClient(clientEntity);
        transaction.setProduct(productEntity);
        if (transactionPost.getAmount() != null)
            transaction.setAmount(transactionPost.getAmount().doubleValue());
        if(transactionPost.getHolder() != null)
            transaction.setHolder(PersonMapper.personToPersonEntity(transactionPost.getHolder()));
        if(transactionPost.getSignatory() != null)
            transaction.setSignatory(PersonMapper.personToPersonEntity(transactionPost.getSignatory()));
        transaction.setCreatedDate(LocalDateTime.now());

        return transaction;
    }

    public static Product creditResponseDtoToProduct(CreditResponseDTO creditResponseDTO){
        Product product = new Product();
        product.setId(creditResponseDTO.getId());
        product.setBalance(BigDecimal.valueOf(creditResponseDTO.getBalance()));
        switch (creditResponseDTO.getType()){
            //PERSONAL, BUSINESS, CREDIT_CARD
            //personal_credit, business_credit, credit_card
            case "PERSONAL":
                product.setType(Product.TypeEnum.valueOf("personal_credit".toUpperCase()));
                break;
            case "BUSINESS":
                product.setType(Product.TypeEnum.valueOf("business_credit".toUpperCase()));
                break;
            case "CREDIT_CARD":
                product.setType(Product.TypeEnum.valueOf("credit_card".toUpperCase()));
                break;
            default:
                throw  new IllegalArgumentException("No credit product type " + creditResponseDTO.getType()
                        + "valid types: [ personal_credit, business_credit, credit_card ]");
        }
        product.setLimit(BigDecimal.valueOf(creditResponseDTO.getLimit()));
        return product;
    }

}
