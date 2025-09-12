package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountCardDTO;
import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("Test Account Mapper")
class AccountMapperTest {

    AccountResponseCreateDTO accountResponseCreateDTO;

    @BeforeEach
    void setUp() {
        accountResponseCreateDTO = AccountResponseCreateDTO.builder()
                .id("anyid")
                .monthlyMovementLimit(10)
                .maintenanceFee(10)
                .linkedCard(
                        AccountCardDTO.builder()
                                .id("cardid")
                                .build()
                )
                .interestRate(10)
                .allowedDayOfMonth(15)
                .interbankNumber("54321123456789012345")
                .holderDocument("12345678")
                .creationDate(LocalDate.now())
                .accountType("SAVINGS")
                .authorizedSigners(new String[]{""})
                .active(true)
                .balance(1)
                .build();
    }

    @Test
    @DisplayName("Account Response To Product")
    void account_mapper_test() {
        var response = AccountMapper.accountResponseCreateDtoToProduct(accountResponseCreateDTO);

        Assertions.assertInstanceOf(Product.class, response);
        Assertions.assertEquals("anyid", response.getId());
        Assertions.assertEquals(BigDecimal.valueOf(1.0), response.getBalance());
        Assertions.assertEquals(ProductType.SAVINGS_ACCOUNT, response.getType());

        accountResponseCreateDTO.setAccountNumber("1234567890");
        response = AccountMapper.accountResponseCreateDtoToProduct(accountResponseCreateDTO);
        Assertions.assertEquals("1234567890", response.getNumber());

        //[ SAVINGS, CHECKING, FIXED_TERM ]
        accountResponseCreateDTO.setAccountType("CHECKING");
        response = AccountMapper.accountResponseCreateDtoToProduct(accountResponseCreateDTO);
        Assertions.assertEquals(ProductType.CHECKING_ACCOUNT, response.getType());

        accountResponseCreateDTO.setAccountType("FIXED_TERM");
        response = AccountMapper.accountResponseCreateDtoToProduct(accountResponseCreateDTO);
        Assertions.assertEquals(ProductType.FIXED_TERM_ACCOUNT, response.getType());

        accountResponseCreateDTO.setAccountType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                AccountMapper.accountResponseCreateDtoToProduct(accountResponseCreateDTO)
        );

    }

}