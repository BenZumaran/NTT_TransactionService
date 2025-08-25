package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.account.AccountCardDTO;
import com.nttdata.transaction_service.dto.account.AccountResponseCreateDTO;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.entity.ClientEntity;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static AccountResponseCreateDTO transactionPostToAcoountResponseCreateDTO(TransactionPost transactionPost){
        AccountResponseCreateDTO accountResponseCreateDTO = new AccountResponseCreateDTO();
        accountResponseCreateDTO.setAccountNumber("00000000000");
        accountResponseCreateDTO.setInterbankNumber("99999000000000009999");
        accountResponseCreateDTO.setHolderDocument(transactionPost.getClient().getId());
        switch (transactionPost.getProduct().getType().getValue()){
            //[ SAVINGS, CHECKING, FIXED_TERM ]
            //savings_account, checking_account, fixed_term_account
            case "savings_account":
                accountResponseCreateDTO.setAccountType("SAVINGS");
                break;
            case "checking_account":
                accountResponseCreateDTO.setAccountType("CHECKING");
                break;
            case "fixed_term_account":
                accountResponseCreateDTO.setAccountType("FIXED_TERM");
                break;
            default:
                throw  new IllegalArgumentException("No credit product type " + transactionPost.getType().getValue()
                        + "valid types: [ savings_account, checking_account, fixed_term_account ]");
        }
        if(transactionPost.getAmount()!= null)
            accountResponseCreateDTO.setBalance(transactionPost.getAmount().doubleValue());
        if(transactionPost.getSignatory() != null)
            accountResponseCreateDTO.setAuthorizedSigners(new String[]{transactionPost.getSignatory().getDocument()});
        accountResponseCreateDTO.setLinkedCard(new AccountCardDTO("2345678909876543"));
        return accountResponseCreateDTO;
    }

    public static Transaction accountResponseCreateDtoTransactionPostToTransaction(AccountResponseCreateDTO accountResponseCreateDTO, TransactionPost transactionPost){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(accountResponseCreateDTO.getId());
        productEntity.setBalance(accountResponseCreateDTO.getBalance());
        productEntity.setType(transactionPost.getProduct().getType().getValue());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(transactionPost.getClient().getId());
        clientEntity.setType(transactionPost.getClient().getType().getValue());


        Transaction transaction = new Transaction();
        transaction.setType(transactionPost.getType().getValue());
        transaction.setProduct(productEntity);
        transaction.setAmount(transactionPost.getAmount().doubleValue());
        transaction.setClient(clientEntity);
        transaction.setCreatedDate(LocalDateTime.now());

        return transaction;
    }

    public static AccountResponseCreateDTO  depositAccountResponseCreateDtoWithTransactionPost(
            AccountResponseCreateDTO previousAccountResponseCreateDTO, TransactionPost transactionPost){

        previousAccountResponseCreateDTO.setBalance(
                previousAccountResponseCreateDTO.getBalance() + transactionPost.getAmount().doubleValue()
        );

        return  previousAccountResponseCreateDTO;

    }

    public static AccountResponseCreateDTO  withdrawalAccountResponseCreateDtoWithTransactionPost(
            AccountResponseCreateDTO previousAccountResponseCreateDTO, TransactionPost transactionPost){

        previousAccountResponseCreateDTO.setBalance(
                previousAccountResponseCreateDTO.getBalance() - transactionPost.getAmount().doubleValue()
        );
        return  previousAccountResponseCreateDTO;
    }

    public static Product accountResponseCreateDtoToProduct(AccountResponseCreateDTO accountResponseCreateDTO){
        Product product = new Product();
        product.setId(accountResponseCreateDTO.getId());
        product.setBalance(BigDecimal.valueOf(accountResponseCreateDTO.getBalance()));
        switch (accountResponseCreateDTO.getAccountType()){
            //[ SAVINGS, CHECKING, FIXED_TERM ]
            //savings_account, checking_account, fixed_term_account
            case "SAVINGS":
                product.setType(Product.TypeEnum.valueOf("savings_account".toUpperCase()));
                break;
            case "CHECKING":
                product.setType(Product.TypeEnum.valueOf("checking_account".toUpperCase()));
                break;
            case "FIXED_TERM":
                product.setType(Product.TypeEnum.valueOf("fixed_term_account".toUpperCase()));
                break;
            default:
                throw  new IllegalArgumentException("No account product type " + accountResponseCreateDTO.getAccountType()
                        + "valid types: [ personal_credit, business_credit, credit_card ]");
        }
        return product;
    }



}
