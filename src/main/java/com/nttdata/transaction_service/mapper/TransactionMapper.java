package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.model.entity.Transaction;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

public class TransactionMapper {
    // Converts Transaction to TransactionGet
    public static TransactionGet transactionToTransactionGet(Transaction transaction) {
        TransactionGet transactionGet = new TransactionGet();
        transactionGet.setId(transaction.getId());
        transactionGet.setNumber(transaction.getNumber());
        if (transaction.getProduct() != null) {
            transactionGet.setProduct(ProductMapper.productEntityToProduct(transaction.getProduct()));
        }
        if (transaction.getType() != null) {
            transactionGet.setType(TransactionGet.TypeEnum.valueOf(transaction.getType().toUpperCase()));
        }
        if (transaction.getClient() != null) {
            transactionGet.setClient(ClientMapper.clientEntityToClient(transaction.getClient()));
        }
        transactionGet.setAmount(BigDecimal.valueOf(transaction.getAmount()));
        if (transaction.getCreatedDate() != null) {
            transactionGet.setCreatedDate(transaction.getCreatedDate().atZone(ZoneId.systemDefault()).toOffsetDateTime());
        }
        if (transaction.getHolder() != null) {
            transactionGet.setHolder(
                    PersonMapper.personEntityToPerson(transaction.getHolder()));
        }
        if (transaction.getSignatory() != null) {
            transactionGet.setSignatory(
                    PersonMapper.personEntityToPerson(transaction.getSignatory()));
        }
        return transactionGet;
    }

    // Converts TransactionGet to Transaction
    public static Transaction transactionGetToTransaction(TransactionGet transactionGet) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionGet.getId());
        transaction.setNumber(transactionGet.getNumber());
        if (transactionGet.getProduct() != null) {
            transaction.setProduct(ProductMapper.productToProductEntity(transactionGet.getProduct()));
        }
        if (transactionGet.getType() != null) {
            transaction.setType(transactionGet.getType().getValue());
        }
        if (transactionGet.getClient() != null) {
            transaction.setClient(ClientMapper.clientToClientEntity(transactionGet.getClient()));
        }
        transaction.setAmount(transactionGet.getAmount() != null ? transactionGet.getAmount().doubleValue() : 0.0);
        if (transactionGet.getCreatedDate() != null) {
            transaction.setCreatedDate(transactionGet.getCreatedDate().toLocalDateTime());
        }
        if (transactionGet.getHolder() != null) {
            transaction.setHolder(PersonMapper.personToPersonEntity(transactionGet.getHolder()));
        }
        if (transactionGet.getSignatory() != null) {
            transaction.setSignatory(PersonMapper.personToPersonEntity(transactionGet.getSignatory()));
        }
        return transaction;
    }
    //Converts TransactionPut to Transaction
    public static Transaction transactionPutAndTransactionPreviousToTransaction(Tuple2<TransactionPut,Transaction> transactionPutTransactionTuple2) {
        if (transactionPutTransactionTuple2.getT1().getProduct() != null)
            transactionPutTransactionTuple2.getT2().setProduct(
                    ProductMapper.productToProductEntity(
                            transactionPutTransactionTuple2.getT1().getProduct()
                    ));

        if (transactionPutTransactionTuple2.getT1().getType() != null)
            transactionPutTransactionTuple2.getT2().setType(
                    transactionPutTransactionTuple2.getT1().getType().getValue());

        if (transactionPutTransactionTuple2.getT1().getClient() != null)
            transactionPutTransactionTuple2.getT2().setClient(
                    ClientMapper.clientToClientEntity(
                            transactionPutTransactionTuple2.getT1().getClient()
                    ));

        if (transactionPutTransactionTuple2.getT1().getAmount() != null)
            transactionPutTransactionTuple2.getT2().setAmount(
                    transactionPutTransactionTuple2.getT1().getAmount().doubleValue()
            );

        if (transactionPutTransactionTuple2.getT1().getHolder() != null)
            transactionPutTransactionTuple2.getT2().setHolder(
                    PersonMapper.personToPersonEntity(
                            transactionPutTransactionTuple2.getT1().getHolder()
                    ));

        if (transactionPutTransactionTuple2.getT1().getSignatory() != null)
            transactionPutTransactionTuple2.getT2().setSignatory(
                    PersonMapper.personToPersonEntity(
                            transactionPutTransactionTuple2.getT1().getSignatory()
                    ));

        return  transactionPutTransactionTuple2.getT2();

    }

    // Converts TransactionPost to Transaction
    public static TransactionGetClientBalance productAndTransactionGetListToTransactionGetClientBalance(
            Tuple2<List<TransactionGet>,Product> tuple2
    ){
        TransactionGetClientBalance transactionGetClientBalance = new TransactionGetClientBalance();
        transactionGetClientBalance.setProduct(tuple2.getT2());
        transactionGetClientBalance.setTransactions(tuple2.getT1());
        return  transactionGetClientBalance;
    }

}
