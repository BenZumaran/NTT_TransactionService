package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.credit.CreditCardDTO;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DisplayName("Test Credit Mapper")
class CreditMapperTest {

    CreditResponseDTO creditResponseDTO;

    @BeforeEach
    void setUp() {
        creditResponseDTO = CreditResponseDTO.builder()
                .id("creditid")
                .status("ACTIVE")
                .interestAnnual(15)
                .type("CREDIT_CARD")
                .dueDate(LocalDate.now())
                .limit(100)
                .customerId("customerid")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .card(
                        CreditCardDTO.builder()
                                .id("cardid")
                                .last4("6789")
                                .brand("VISA")
                                .build()
                )
                .balance(0)
                .build();
    }

    @Test
    @DisplayName("Credit Response To Product")
    void creditResponseDtoToProduct() {

        var response = CreditMapper.creditResponseDtoToProduct(creditResponseDTO);

        Assertions.assertInstanceOf(Product.class, response);
        Assertions.assertEquals("creditid", response.getId());
        Assertions.assertEquals(BigDecimal.valueOf(0.0), response.getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), response.getLimit());
        Assertions.assertEquals(ProductType.CREDIT_CARD, response.getType());

        //PERSONAL, BUSINESS, CREDIT_CARD
        creditResponseDTO.setType("PERSONAL");
        response = CreditMapper.creditResponseDtoToProduct(creditResponseDTO);
        Assertions.assertEquals(ProductType.PERSONAL_CREDIT, response.getType());

        creditResponseDTO.setType("BUSINESS");
        response = CreditMapper.creditResponseDtoToProduct(creditResponseDTO);
        Assertions.assertEquals(ProductType.BUSINESS_CREDIT, response.getType());

        creditResponseDTO.setType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                CreditMapper.creditResponseDtoToProduct(creditResponseDTO)
        );

    }
}