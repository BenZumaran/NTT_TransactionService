package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionProductDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import com.nttdata.transaction_service.model.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductMapper {

    // Validate and Converts ProductEntity to Product
    public static Product productEntityToProduct(ProductEntity productEntity) throws IllegalArgumentException {
        Product product = new Product();
        if (productEntity.getId() == null)
            throw new IllegalArgumentException("Product should have id");
        product.setId(productEntity.getId());
        if (productEntity.getType() != null)
            product.setType(ProductType.fromValue(productEntity.getType()));
        if (productEntity.getNumber() != null)
            product.setNumber(productEntity.getNumber());
        if (productEntity.getBalance() > 0)
            product.setBalance(BigDecimal.valueOf(productEntity.getBalance()));
        if (productEntity.getLimit() > 0)
            product.setLimit(BigDecimal.valueOf(productEntity.getLimit()));
        return product;
    }

    // Validate Converts Product to ProductEntity
    public static ProductEntity productToProductEntity(Product product) {
        ProductEntity productEntity = ProductEntity.builder().build();
        if (product.getId() == null)
            throw new IllegalArgumentException("Product should have id");
        productEntity.setId(product.getId());
        if (product.getType() != null)
            productEntity.setType(product.getType().getValue());
        if (product.getNumber() != null)
            productEntity.setNumber(product.getNumber());
        if (product.getBalance() != null)
            productEntity.setBalance(product.getBalance().doubleValue());
        if (product.getLimit() != null)
            productEntity.setLimit(product.getLimit().doubleValue());
        return productEntity;
    }

    public static ProductEntity accountResponseDtoToProductEntity(AccountResponseCreateDTO accountResponseCreateDTO) {
        if (
                accountResponseCreateDTO.getId() == null ||
                        accountResponseCreateDTO.getAccountNumber() == null ||
                        accountResponseCreateDTO.getAccountType() == null
        ) throw new IllegalArgumentException("Account should have id, number, balance, type");
        ProductEntity productEntity = ProductEntity.builder().build();
        productEntity.setId(accountResponseCreateDTO.getId());
        productEntity.setNumber(accountResponseCreateDTO.getAccountNumber());
        productEntity.setBalance(accountResponseCreateDTO.getBalance());
        //savings_account, checking_account, fixed_term_account, personal_credit, business_credit, credit_card
        //SAVINGS, CHECKING, FIXED_TERM
        switch (accountResponseCreateDTO.getAccountType()) {
            case "SAVINGS":
                productEntity.setType("savings_account");
                break;
            case "CHECKING":
                productEntity.setType("checking_account");
                break;
            case "FIXED_TERM":
                productEntity.setType("fixed_term_account");
                break;
            default:
                throw new IllegalArgumentException("Account Product Type should be like SAVINGS, CHECKING, FIXED_TERM.");
        }
        return productEntity;
    }


    public static ProductEntity creditResponseDtoToProductEntity(CreditResponseDTO creditResponseDTO) {
        if (
                creditResponseDTO.getId() == null ||
                        creditResponseDTO.getType() == null ||
                        creditResponseDTO.getLimit() <= 0
        ) throw new IllegalArgumentException("Credit should have id, type");
        ProductEntity productEntity = ProductEntity.builder().build();
        productEntity.setId(creditResponseDTO.getId());
        productEntity.setLimit(creditResponseDTO.getLimit());
        productEntity.setBalance(creditResponseDTO.getBalance());
        //personal_credit, business_credit, credit_card
        //PERSONAL, BUSINESS, CREDIT_CARD
        switch (creditResponseDTO.getType()) {
            case "PERSONAL":
                productEntity.setType("personal_credit");
                break;
            case "BUSINESS":
                productEntity.setType("business_credit");
                break;
            case "CREDIT_CARD":
                productEntity.setType("credit_card");
                break;
            default:
                throw new IllegalArgumentException("Credit Product Type should be like PERSONAL, BUSINESS, CREDIT_CARD");
        }
        return productEntity;
    }

    public static Product transactionProductDtoToProduct(TransactionProductDTO transactionProductDTO) {
        if (
                transactionProductDTO.getId() == null ||
                        transactionProductDTO.getType() == null
        ) throw new IllegalArgumentException("Transaction Product should have id, type");
        Product product = new Product();
        product.setId(transactionProductDTO.getId());
        //savings_account, checking_account, fixed_term_account, personal_credit, business_credit, credit_card
        //SAVINGS, CHECKING, FIXED_TERM, PERSONAL, BUSINESS, CREDIT_CARD
        product.setType(ProductType.fromValue(transactionProductDTO.getType()));
        if (transactionProductDTO.getNumber() != null)
            product.setNumber(transactionProductDTO.getNumber());
        if (transactionProductDTO.getBalance() > 0)
            product.setBalance(BigDecimal.valueOf(transactionProductDTO.getBalance()));
        if (transactionProductDTO.getLimit() > 0)
            product.setLimit(BigDecimal.valueOf(transactionProductDTO.getLimit()));
        return product;
    }


}
