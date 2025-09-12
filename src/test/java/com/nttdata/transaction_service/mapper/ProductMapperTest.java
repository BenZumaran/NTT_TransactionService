package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductMapperTest {

    @Test
    void productEntityToProduct() {
        ProductEntity productEntity = ProductEntity.builder().build();
        //Test empty
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> ProductMapper.productEntityToProduct(productEntity)
        );
        //Test Class and id
        productEntity.setId("productid");
        var response = ProductMapper.productEntityToProduct(productEntity);
        Assertions.assertInstanceOf(Product.class, response);
        Assertions.assertEquals("productid", response.getId());
        Assertions.assertNull(response.getType());
        Assertions.assertNull(response.getNumber());
        Assertions.assertNull(response.getBalance());
        Assertions.assertNull(response.getLimit());
        //Test type
        productEntity.setType(ProductType.SAVINGS_ACCOUNT.getValue());
        response = ProductMapper.productEntityToProduct(productEntity);
        Assertions.assertEquals(ProductType.SAVINGS_ACCOUNT, response.getType());
        //Test wrong type
        productEntity.setType("other");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> ProductMapper.productEntityToProduct(productEntity)
        );
        productEntity.setType(ProductType.SAVINGS_ACCOUNT.getValue());
        //Test Number
        productEntity.setNumber("1234567890");
        response = ProductMapper.productEntityToProduct(productEntity);
        Assertions.assertEquals("1234567890", response.getNumber());
        //Test Balance
        productEntity.setBalance(1);
        response = ProductMapper.productEntityToProduct(productEntity);
        Assertions.assertEquals(BigDecimal.valueOf(1.0), response.getBalance());
        //Test limit
        productEntity.setLimit(100);
        response = ProductMapper.productEntityToProduct(productEntity);
        Assertions.assertEquals(BigDecimal.valueOf(100.0), response.getLimit());
    }

    @Test
    void productToProductEntity() {
        Product product = new Product();
        //Test empty
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> ProductMapper.productToProductEntity(product)
        );
        //Test Class and id
        product.setId("productid");
        var response = ProductMapper.productToProductEntity(product);
        Assertions.assertInstanceOf(ProductEntity.class, response);
        Assertions.assertEquals("productid", response.getId());
        Assertions.assertNull(response.getType());
        Assertions.assertNull(response.getNumber());
        Assertions.assertEquals(0.0, response.getBalance());
        Assertions.assertEquals(0.0, response.getLimit());
        //Test type
        product.setType(ProductType.SAVINGS_ACCOUNT);
        response = ProductMapper.productToProductEntity(product);
        Assertions.assertEquals(ProductType.SAVINGS_ACCOUNT.getValue(), response.getType());
        //Test Number
        product.setNumber("1234567890");
        response = ProductMapper.productToProductEntity(product);
        Assertions.assertEquals("1234567890", response.getNumber());
        //Test Balance
        product.setBalance(BigDecimal.ONE);
        response = ProductMapper.productToProductEntity(product);
        Assertions.assertEquals(1.0, response.getBalance());
        //Test limit
        product.setLimit(BigDecimal.TEN);
        response = ProductMapper.productToProductEntity(product);
        Assertions.assertEquals(10.0, response.getLimit());
    }

    @Test
    void accountResponseDtoToProductEntity() {
        AccountResponseCreateDTO accountResponseCreateDTO = AccountResponseCreateDTO.builder().build();
        //Test empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO));
        //Test Class and id
        accountResponseCreateDTO.setId("productid");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO));
        accountResponseCreateDTO.setAccountNumber("1234567890");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO));
        accountResponseCreateDTO.setAccountType("SAVINGS");
        var response = ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO);
        Assertions.assertInstanceOf(ProductEntity.class, response);
        Assertions.assertEquals("productid", response.getId());
        Assertions.assertEquals(0.0, response.getLimit());
        Assertions.assertEquals(ProductType.SAVINGS_ACCOUNT.getValue(), response.getType());
        //Test types
        //Checking
        accountResponseCreateDTO.setAccountType("CHECKING");
        response = ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO);
        Assertions.assertEquals(ProductType.CHECKING_ACCOUNT.getValue(), response.getType());
        //Fixed term
        accountResponseCreateDTO.setAccountType("FIXED_TERM");
        response = ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO);
        Assertions.assertEquals(ProductType.FIXED_TERM_ACCOUNT.getValue(), response.getType());
        //Wrong
        accountResponseCreateDTO.setAccountType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.accountResponseDtoToProductEntity(accountResponseCreateDTO));
    }

    @Test
    void creditResponseDtoToProductEntity() {
        CreditResponseDTO creditResponseDTO = CreditResponseDTO.builder().build();
        //Test empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO));
        //Test Class and id
        creditResponseDTO.setId("productid");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO));
        creditResponseDTO.setType("PERSONAL");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO));
        creditResponseDTO.setLimit(100);

        var response = ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO);
        Assertions.assertInstanceOf(ProductEntity.class, response);
        Assertions.assertEquals("productid", response.getId());
        Assertions.assertEquals(100.0, response.getLimit());
        Assertions.assertEquals(0.0, response.getBalance());
        Assertions.assertEquals(ProductType.PERSONAL_CREDIT.getValue(), response.getType());
        //Test types
        //Checking
        creditResponseDTO.setType("BUSINESS");
        response = ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO);
        Assertions.assertEquals(ProductType.BUSINESS_CREDIT.getValue(), response.getType());
        //Fixed term
        creditResponseDTO.setType("CREDIT_CARD");
        response = ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO);
        Assertions.assertEquals(ProductType.CREDIT_CARD.getValue(), response.getType());
        //Wrong
        creditResponseDTO.setType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () -> ProductMapper.creditResponseDtoToProductEntity(creditResponseDTO));
    }
}