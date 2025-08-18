package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.*;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        client.setId(transactionPost.getClient().getId());
        client.setType("Personal");
        client.setDocument("98877653");
        transaction.setClient(client);

        com.nttdata.transaction_service.model.entity.Product product = new com.nttdata.transaction_service.model.entity.Product();
        product.setId(transactionPost.getProduct().getId());
        product.setType(transactionPost.getProduct().getType().getValue());
        product.setNumber("1032765879657");
        product.setLimit(5000);
        product.setBalance(3500);
        transaction.setProduct(product);

        if (transactionPost.getHolder() != null) {
            com.nttdata.transaction_service.model.entity.Person holderPerson = new com.nttdata.transaction_service.model.entity.Person();
            holderPerson.setFullName(transactionPost.getHolder().getFullName());
            holderPerson.setDocument(transactionPost.getHolder().getDocument());
            holderPerson.setSignature(transactionPost.getHolder().getSignature());
            transaction.setHolder(holderPerson);
        }
        if (transactionPost.getSignatory() != null) {
            com.nttdata.transaction_service.model.entity.Person signatoryPerson = new com.nttdata.transaction_service.model.entity.Person();
            signatoryPerson.setDocument(transactionPost.getSignatory().getDocument());
            signatoryPerson.setFullName(transactionPost.getSignatory().getFullName());
            signatoryPerson.setSignature(transactionPost.getSignatory().getSignature());
            transaction.setSignatory(signatoryPerson);
        }

        return transaction;
    }

    public Mono<Transaction> getTransactionOfTransactionPut(TransactionPut transactionPut, Mono<Transaction> previousTransaction) {
        return previousTransaction.map(transaction -> {
            if(transactionPut.getType() != null)
                transaction.setType(transactionPut.getType().getValue());

            if(transactionPut.getAmount() != null)
                transaction.setAmount(transactionPut.getAmount().doubleValue());

            if(transactionPut.getClient() != null){
                com.nttdata.transaction_service.model.entity.Client client = new com.nttdata.transaction_service.model.entity.Client();
                client.setId(transactionPut.getClient().getId());
                client.setDocument(transactionPut.getClient().getDocument());
                client.setType(transactionPut.getClient().getType().getValue());
                transaction.setClient(client);
            }
            if(transactionPut.getProduct() != null){
                com.nttdata.transaction_service.model.entity.Product product = new com.nttdata.transaction_service.model.entity.Product();
                product.setId(transactionPut.getProduct().getId());
                product.setType(transactionPut.getProduct().getType().toString());
                product.setBalance(transactionPut.getProduct().getBalance().doubleValue());
                product.setLimit(transactionPut.getProduct().getLimit().doubleValue());
                if(transactionPut.getProduct().getNumber() != null)
                    product.setNumber(transactionPut.getProduct().getNumber());
                transaction.setProduct(product);
            }

            if (transactionPut.getHolder() != null) {
            com.nttdata.transaction_service.model.entity.Person holderPerson = new com.nttdata.transaction_service.model.entity.Person();
                holderPerson.setDocument(transactionPut.getHolder().getDocument());
                holderPerson.setFullName(transactionPut.getHolder().getFullName());
                holderPerson.setSignature(transactionPut.getHolder().getSignature());
            transaction.setHolder(holderPerson);
            }

            if (transactionPut.getSignatory() != null) {
            com.nttdata.transaction_service.model.entity.Person signatoryPerson = new com.nttdata.transaction_service.model.entity.Person();
                signatoryPerson.setDocument(transactionPut.getSignatory().getDocument());
                signatoryPerson.setFullName(transactionPut.getSignatory().getFullName());
                signatoryPerson.setSignature(transactionPut.getSignatory().getSignature());
                transaction.setSignatory(signatoryPerson);
            }
            return transaction;
        });
    }


    public Mono<TransactionGet> getTransactionGetOfTransactionOptional(Mono<Transaction> transactionMono){
        return transactionMono.map(entity -> {

            Product product = new Product();
            product.setId(entity.getProduct().getId());
            product.setType(Product.TypeEnum.valueOf(entity.getProduct().getType().toUpperCase()));
            product.setLimit(BigDecimal.valueOf(entity.getProduct().getLimit()));
            product.setBalance(BigDecimal.valueOf(entity.getProduct().getBalance()));
            if(entity.getProduct().getNumber() != null)
                product.setNumber(entity.getProduct().getNumber());

            Client client = new Client();
            client.setId(entity.getClient().getId());
            client.setType(Client.TypeEnum.valueOf(entity.getClient().getType().toUpperCase()));
            client.setDocument(entity.getClient().getDocument());

            TransactionGet response = new TransactionGet();
            response.setId(entity.getId());
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
                response.setHolder(personHolder);
            }
            if (entity.getSignatory() != null) {
                Person personSignatory  = new Person();
                personSignatory.setDocument(entity.getSignatory().getDocument());
                personSignatory.setSignature(entity.getSignatory().getSignature());
                personSignatory.setFullName(entity.getSignatory().getFullName());
                response.setSignatory(personSignatory);
            }
            return response;
        });
    }

    public Mono<TransactionGet> getTransactionGetOfTransaction(Mono<Transaction> entity){
        return entity.map(transaction -> {
            Product product = new Product();
            product.setId(transaction.getProduct().getId());
            product.setType(Product.TypeEnum.valueOf(transaction.getProduct().getType().toUpperCase()));
            product.setLimit(BigDecimal.valueOf(transaction.getProduct().getLimit()));
            product.setBalance(BigDecimal.valueOf(transaction.getProduct().getBalance()));
            if(transaction.getProduct().getNumber() != null)
                product.setNumber(transaction.getProduct().getNumber());

            Client client = new Client();
            client.setId(transaction.getClient().getId());
            client.setType(Client.TypeEnum.valueOf(transaction.getClient().getType().toUpperCase()));
            client.setDocument(transaction.getClient().getDocument());

            TransactionGet response = new TransactionGet();
            response.setId(transaction.getId());
            response.setNumber(transaction.getNumber());
            response.setProduct(product);
            response.setType(TransactionGet.TypeEnum.valueOf(transaction.getType().toUpperCase()));
            response.setAmount(BigDecimal.valueOf(transaction.getAmount()));
            response.setCreatedDate(Date.from(transaction.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
            response.setClient(client);

            if (transaction.getHolder() != null) {
                Person personHolder = new Person();
                personHolder.setDocument(transaction.getHolder().getDocument());
                personHolder.setSignature(transaction.getHolder().getSignature());
                personHolder.setFullName(transaction.getHolder().getFullName());
                response.setHolder(personHolder);
            }
            if (transaction.getSignatory() != null) {
                Person personSignatory  = new Person();
                personSignatory.setDocument(transaction.getSignatory().getDocument());
                personSignatory.setSignature(transaction.getSignatory().getSignature());
                personSignatory.setFullName(transaction.getSignatory().getFullName());
                response.setSignatory(personSignatory);
            }

            return response;
        });
    }

    public Flux<TransactionGet> getTransactionGetListOfTransactionsList(Flux<Transaction> list){
        List<TransactionGet> response = new ArrayList<>();

        return list.map( transaction -> {
            Product product = new Product();
            product.setId(transaction.getProduct().getId());
            product.setType(Product.TypeEnum.valueOf(transaction.getProduct().getType().toUpperCase()));
            product.setBalance(BigDecimal.valueOf(transaction.getProduct().getBalance()));
            if(transaction.getProduct().getNumber() != null)
                product.setNumber(transaction.getProduct().getNumber());

            if (transaction.getProduct().getLimit() != 0)
                product.setLimit(BigDecimal.valueOf(transaction.getProduct().getLimit()));
            Client client = new Client();
            client.setId(transaction.getClient().getId());
            client.setType(Client.TypeEnum.valueOf(transaction.getClient().getType().toUpperCase()));
            client.setDocument(transaction.getClient().getDocument());

            TransactionGet transactionGet = new TransactionGet();
            transactionGet.setId(transaction.getId());
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
                transactionGet.setHolder(personHolder);
            }
            if (transaction.getSignatory() != null) {
                Person personSignatory = new Person();
                personSignatory.setDocument(transaction.getSignatory().getDocument());
                personSignatory.setSignature(transaction.getSignatory().getSignature());
                personSignatory.setFullName(transaction.getSignatory().getFullName());
                transactionGet.setSignatory(personSignatory);
            }


            return transactionGet;
        });

    }


    public Mono<TransactionGetClientBalance> getTransactionGetClientBalanceOfTransactions(Mono<Product> productMono, Flux<TransactionGet> list){
        Product producto = new Product();

        return productMono.map(product -> {
            TransactionGetClientBalance transaction = new TransactionGetClientBalance();

            transaction.setProduct(product);
          transaction.setTransactions(list.collectList().block());
            return transaction;
        });
    }


}
