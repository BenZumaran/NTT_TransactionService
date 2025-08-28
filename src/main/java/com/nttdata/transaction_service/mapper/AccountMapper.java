package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.ProductType;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountMapper {

    public static Product accountResponseCreateDtoToProduct(AccountResponseCreateDTO accountResponseCreateDTO){
        Product product = new Product();
        product.setId(accountResponseCreateDTO.getId());
        product.setBalance(BigDecimal.valueOf(accountResponseCreateDTO.getBalance()));
        switch (accountResponseCreateDTO.getAccountType()){
            //[ SAVINGS, CHECKING, FIXED_TERM ]
            //savings_account, checking_account, fixed_term_account
            case "SAVINGS":
                product.setType(ProductType.fromValue("savings_account"));
                break;
            case "CHECKING":
                product.setType(ProductType.fromValue("checking_account"));
                break;
            case "FIXED_TERM":
                product.setType(ProductType.fromValue("fixed_term_account"));
                break;
            default:
                throw  new IllegalArgumentException("No account product type " + accountResponseCreateDTO.getAccountType()
                        + "valid types: [ personal_credit, business_credit, credit_card ]");
        }
        return product;
    }



}
