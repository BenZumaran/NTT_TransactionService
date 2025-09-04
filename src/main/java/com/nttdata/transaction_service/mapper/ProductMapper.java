package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import com.nttdata.transaction_service.model.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductMapper {

    // Validate and Converts ProductEntity to Product
    public static Product productEntityToProduct(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        if (productEntity.getType() != null)
            product.setType(ProductType.fromValue(productEntity.getType()));
        if (productEntity.getNumber() != null)
            product.setNumber(productEntity.getNumber());
        if (productEntity.getBalance() > -1)
            product.setBalance(BigDecimal.valueOf(productEntity.getBalance()));
        if (productEntity.getLimit() > -1)
            product.setLimit(BigDecimal.valueOf(productEntity.getLimit()));
        return product;
    }

    // Validate Converts Product to ProductEntity
    public static ProductEntity productToProductEntity(Product product) {
        ProductEntity productEntity = ProductEntity.builder().build();
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
        }
        return productEntity;
    }


    public static ProductEntity creditResponseDtoToProductEntity(CreditResponseDTO creditResponseDTO) {
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
        }
        return productEntity;
    }


}
