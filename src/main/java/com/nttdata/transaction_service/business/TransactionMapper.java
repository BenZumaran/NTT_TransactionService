package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.*;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction getTransactionOfTransactionPost(TransactionPost transactionPost){
        Transaction transaction = new Transaction();

        transaction.setCreatedDate(LocalDateTime.now());

        String type = transactionPost.getType().getValue();
        double amount = transactionPost.getAmount().doubleValue();

        transaction.setType(type);
        transaction.setAmount(amount);

        com.nttdata.transaction_service.model.entity.Client client = new com.nttdata.transaction_service.model.entity.Client();
        client.set_id(transactionPost.getClient().getId());
        client.setType("Personal");
        client.setDocument("98877653");
        transaction.setClient(client);

        com.nttdata.transaction_service.model.entity.Product product = new com.nttdata.transaction_service.model.entity.Product();
        product.set_id(transactionPost.getProduct().getId());
        product.setType(transactionPost.getProduct().getType().getValue());
        product.setNumber("1032765879657");
        product.setLimit(5000);
        product.setBalance(3500);
        transaction.setProduct(product);

        if (transactionPost.getHolder() != null && transactionPost.getHolder().isPresent()) {
            com.nttdata.transaction_service.model.entity.Person holderPerson = new com.nttdata.transaction_service.model.entity.Person();
            holderPerson.setDocument(transactionPost.getHolder().get().getDocument());
            holderPerson.setFullName(transactionPost.getHolder().get().getFullName());
            holderPerson.setSignature(transactionPost.getHolder().get().getSignature());
            transaction.setHolder(holderPerson);
        }
        if (transactionPost.getSignatory() != null && transactionPost.getSignatory().isPresent()) {
            com.nttdata.transaction_service.model.entity.Person signatoryPerson = new com.nttdata.transaction_service.model.entity.Person();
            signatoryPerson.setDocument(transactionPost.getSignatory().get().getDocument());
            signatoryPerson.setFullName(transactionPost.getSignatory().get().getFullName());
            signatoryPerson.setSignature(transactionPost.getSignatory().get().getSignature());
            transaction.setSignatory(signatoryPerson);
        }

        return transaction;
    }

    public Transaction getTransactionOfTransactionPut(TransactionPut transactionPut, Optional<Transaction> previousTransaction) {

        Transaction transaction = previousTransaction.get();
        transaction.set_id(transactionPut.getId());

        if(transactionPut.getType().isPresent())
            transaction.setType(transactionPut.getType().get().getValue());

        if(transactionPut.getAmount().isPresent())
            transaction.setAmount(transactionPut.getAmount().get().doubleValue());

        if(transactionPut.getClient().isPresent()){
            com.nttdata.transaction_service.model.entity.Client client = new com.nttdata.transaction_service.model.entity.Client();
            client.set_id(transactionPut.getClient().get().getId());
            client.setDocument(transactionPut.getClient().get().getDocument());
            client.setType(transactionPut.getClient().get().getType().getValue());
            transaction.setClient(client);
        }
        if(transactionPut.getProduct().isPresent()){
            com.nttdata.transaction_service.model.entity.Product product = new com.nttdata.transaction_service.model.entity.Product();
            product.set_id(transactionPut.getProduct().get().getId());
            product.setType(transactionPut.getProduct().get().getType().getValue());
            product.setBalance(transactionPut.getProduct().get().getBalance().doubleValue());
            product.setLimit(transactionPut.getProduct().get().getLimit().doubleValue());
            if(transactionPut.getProduct().isPresent())
                product.setNumber(transactionPut.getProduct().get().getNumber().get());
            transaction.setProduct(product);
        }

        if (transactionPut.getHolder().isPresent()) {
        com.nttdata.transaction_service.model.entity.Person holderPerson = new com.nttdata.transaction_service.model.entity.Person();
            holderPerson.setDocument(transactionPut.getHolder().get().getDocument());
            holderPerson.setFullName(transactionPut.getHolder().get().getFullName());
            holderPerson.setSignature(transactionPut.getHolder().get().getSignature());
        transaction.setHolder(holderPerson);
        }

        if (transactionPut.getSignatory().isPresent()) {
        com.nttdata.transaction_service.model.entity.Person signatoryPerson = new com.nttdata.transaction_service.model.entity.Person();
            signatoryPerson.setDocument(transactionPut.getSignatory().get().getDocument());
            signatoryPerson.setFullName(transactionPut.getSignatory().get().getFullName());
            signatoryPerson.setSignature(transactionPut.getSignatory().get().getSignature());
            transaction.setSignatory(signatoryPerson);
        }

        return transaction;
    }


    public TransactionGet getTransactionGetOfTransactionOptional(Optional<Transaction> optionalEntity){
        Transaction entity = optionalEntity.get();

        Product product = new Product();
        product.setId(entity.getProduct().get_id());
        product.setType(Product.TypeEnum.valueOf(entity.getProduct().getType().toUpperCase()));
        product.setLimit(BigDecimal.valueOf(entity.getProduct().getLimit()));
        product.setBalance(BigDecimal.valueOf(entity.getProduct().getBalance()));
        if(entity.getProduct().getNumber() != null)
            product.setNumber(JsonNullable.of(entity.getProduct().getNumber()));

        Client client = new Client();
        client.setId(entity.getClient().get_id());
        client.setType(Client.TypeEnum.valueOf(entity.getClient().getType().toUpperCase()));
        client.setDocument(entity.getClient().getDocument());

        TransactionGet response = new TransactionGet();
        response.setId(entity.get_id());
        response.setNumber(entity.getNumber());
        response.setProduct(product);
        response.setType(TransactionGet.TypeEnum.valueOf(entity.getType().toUpperCase()));
        response.setAmount(BigDecimal.valueOf(entity.getAmount()));
        response.setCreatedDate(Date.from(entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
        response.setClient(client);

        if (entity.getHolder() != null) {
            Person personHolder = new Person();
            personHolder.setDocument(entity.getHolder().getDocument());
            personHolder.setSignature(entity.getHolder().getSignature());
            personHolder.setFullName(entity.getHolder().getFullName());
            response.setHolder(JsonNullable.of(personHolder));
        }
        if (entity.getSignatory() != null) {
            Person personSignatory  = new Person();
            personSignatory.setDocument(entity.getSignatory().getDocument());
            personSignatory.setSignature(entity.getSignatory().getSignature());
            personSignatory.setFullName(entity.getSignatory().getFullName());
            response.setSignatory(JsonNullable.of(personSignatory));
        }
        return response;
    }

    public TransactionGet getTransactionGetOfTransaction(Transaction entity){
        Product product = new Product();
        product.setId(entity.getProduct().get_id());
        product.setType(Product.TypeEnum.valueOf(entity.getProduct().getType().toUpperCase()));
        product.setLimit(BigDecimal.valueOf(entity.getProduct().getLimit()));
        product.setBalance(BigDecimal.valueOf(entity.getProduct().getBalance()));
        if(entity.getProduct().getNumber() != null)
            product.setNumber(JsonNullable.of(entity.getProduct().getNumber()));

        Client client = new Client();
        client.setId(entity.getClient().get_id());
        client.setType(Client.TypeEnum.valueOf(entity.getClient().getType().toUpperCase()));
        client.setDocument(entity.getClient().getDocument());

        TransactionGet response = new TransactionGet();
        response.setId(entity.get_id());
        response.setNumber(entity.getNumber());
        response.setProduct(product);
        response.setType(TransactionGet.TypeEnum.valueOf(entity.getType().toUpperCase()));
        response.setAmount(BigDecimal.valueOf(entity.getAmount()));
        response.setCreatedDate(Date.from(entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
        response.setClient(client);

        if (entity.getHolder() != null) {
            Person personHolder = new Person();
            personHolder.setDocument(entity.getHolder().getDocument());
            personHolder.setSignature(entity.getHolder().getSignature());
            personHolder.setFullName(entity.getHolder().getFullName());
            response.setHolder(JsonNullable.of(personHolder));
        }
        if (entity.getSignatory() != null) {
            Person personSignatory  = new Person();
            personSignatory.setDocument(entity.getSignatory().getDocument());
            personSignatory.setSignature(entity.getSignatory().getSignature());
            personSignatory.setFullName(entity.getSignatory().getFullName());
            response.setSignatory(JsonNullable.of(personSignatory));
        }
        return response;
    }

    public List<TransactionGet> getTransasctionGetListOfTransactionsList(List<Transaction> list){
        List<TransactionGet> response = new ArrayList<>();
        response = list.stream().map( transaction -> {
            Product product = new Product();
            product.setId(transaction.getProduct().get_id());
            product.setType(Product.TypeEnum.valueOf(transaction.getProduct().getType().toUpperCase()));
            product.setBalance(BigDecimal.valueOf(transaction.getProduct().getBalance()));
            if(transaction.getProduct().getNumber() != null)
                product.setNumber(JsonNullable.of(transaction.getProduct().getNumber()));

            if (transaction.getProduct().getLimit() != 0)
                product.setLimit(BigDecimal.valueOf(transaction.getProduct().getLimit()));
            Client client = new Client();
            client.setId(transaction.getClient().get_id());
            client.setType(Client.TypeEnum.valueOf(transaction.getClient().getType().toUpperCase()));
            client.setDocument(transaction.getClient().getDocument());

            TransactionGet transactionGet = new TransactionGet();
            transactionGet.setId(transaction.get_id());
            transactionGet.setNumber(transaction.getNumber());
            transactionGet.setProduct(product);
            transactionGet.setType(TransactionGet.TypeEnum.valueOf(transaction.getType().toUpperCase()));
            transactionGet.setAmount(BigDecimal.valueOf(transaction.getAmount()));
            transactionGet.setCreatedDate(Date.from(transaction.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
            transactionGet.client(client);

            if (transaction.getHolder() != null) {
                Person personHolder = new Person();
                personHolder.setDocument(transaction.getHolder().getDocument());
                personHolder.setSignature(transaction.getHolder().getSignature());
                personHolder.setFullName(transaction.getHolder().getFullName());
                transactionGet.setHolder(JsonNullable.of(personHolder));
            }
            if (transaction.getSignatory() != null) {
                Person personSignatory = new Person();
                personSignatory.setDocument(transaction.getSignatory().getDocument());
                personSignatory.setSignature(transaction.getSignatory().getSignature());
                personSignatory.setFullName(transaction.getSignatory().getFullName());
                transactionGet.setSignatory(JsonNullable.of(personSignatory));
            }


            return transactionGet;
        }).collect(Collectors.toList());

        return response;

    }
}
