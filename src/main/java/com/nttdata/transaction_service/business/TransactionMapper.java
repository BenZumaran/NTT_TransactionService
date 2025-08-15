package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.model.Client;
import com.nttdata.transaction_service.model.Person;
import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TransactionMapper {
    public Optional<Transaction> getTransactionOfTransactionGet(Transaction entity){
        Transaction response = new Transaction();
        response.set_id(entity.get_id());
        response.setNumber(entity.getNumber());
        response.setProduct(entity.getProduct());
        response.setType(entity.getType());
        response.setAmount(entity.getAmount());
        response.setCreatedDate(entity.getCreatedDate());
        response.setClient(entity.getClient());
        if(entity.getSignatory() != null)
            response.setSignatory(entity.getSignatory());
        if(entity.getHolder() != null)
            response.setHolder(entity.getHolder());
        return Optional.of(response);
    }

    public ResponseEntity<List<TransactionGet>> getTransasctionListOfTransactionsGetList(List<Transaction> list){
        List<TransactionGet> response = new ArrayList<>();
        response = list.stream().map( transaction -> {
            Product product = new Product();
            product.setId(transaction.getProduct().get_id());

            product.setType(transaction.getProduct().getType().equalsIgnoreCase(String.valueOf(Product.TypeEnum.ACCOUNT)) ?
                    Product.TypeEnum.ACCOUNT : Product.TypeEnum.CREDIT);
            product.setBalance(BigDecimal.valueOf(transaction.getProduct().getBalance()));
            if (transaction.getProduct().getLimit() != 0)
                product.setLimit(BigDecimal.valueOf(transaction.getProduct().getLimit()));
            Client client = new Client();
            client.setId(transaction.getClient().get_id());
            System.out.println(transaction.getClient().get_id());
            client.setType(transaction.getClient().getType().equalsIgnoreCase(String.valueOf(Client.TypeEnum.PERSONAL)) ?
                    Client.TypeEnum.PERSONAL : Client.TypeEnum.BUSINESS);
            client.setDocument(transaction.getClient().getDocument());

            Person personHolder = null;
            if (transaction.getHolder() != null) {
                personHolder = new Person();
                personHolder.setDocument(transaction.getHolder().getDocument());
                personHolder.setSignature(transaction.getHolder().getSignature());
                personHolder.setFullName(transaction.getHolder().getFullName());
            }
            Person personSignatory = null;
            if (transaction.getSignatory() != null) {
                personSignatory = new Person();
                personSignatory.setDocument(transaction.getSignatory().getDocument());
                personSignatory.setSignature(transaction.getSignatory().getSignature());
                personSignatory.setFullName(transaction.getSignatory().getFullName());
            }

            TransactionGet transactionGet = new TransactionGet();
            transactionGet.setId(transaction.get_id());
            transactionGet.setNumber(transaction.getNumber());
            transactionGet.setProduct(product);
            transactionGet.setType(TransactionGet.TypeEnum.valueOf(transaction.getType().toUpperCase()));
            transactionGet.setAmount(BigDecimal.valueOf(transaction.getAmount()));

            transactionGet.setCreatedDate(Date.from(transaction.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant()));
            transactionGet.client(client);
            if (personHolder != null) transactionGet.setHolder(personHolder);
            if (personSignatory != null) transactionGet.setSignatory(personSignatory);

            return transactionGet;
        }).collect(Collectors.toList());

        return new ResponseEntity<List<TransactionGet>>(response, HttpStatus.OK);

    }
}
