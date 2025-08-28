package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.*;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class TransactionMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionMapper.class);

    // Converts Transaction to TransactionGet
    public static TransactionGet transactionToTransactionGet(Transaction transaction) throws Error {

            TransactionGet transactionGet = new TransactionGet();
            transactionGet.setId(transaction.getId());
            transactionGet.setNumber(transaction.getNumber());
            transactionGet.setSender(ProductMapper.productEntityToProduct(transaction.getSender()));
            if (transaction.getReceiver() != null) {
                transactionGet.setReceiver(ProductMapper.productEntityToProduct(transaction.getReceiver()));
            }
            if (transaction.getType() != null) {
                transactionGet.setType(TransactionType.fromValue(transaction.getType()));
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
    //Converts TransactionPut to Transaction
    public static Transaction transactionPutAndTransactionPreviousToTransaction(Tuple2<TransactionPut,Transaction> transactionPutTransactionTuple2) {
        try{

            if (transactionPutTransactionTuple2.getT1().getSender() != null)
                transactionPutTransactionTuple2.getT2().setSender(
                        ProductMapper.productToProductEntity(
                                transactionPutTransactionTuple2.getT1().getSender()
                        ));

            if (transactionPutTransactionTuple2.getT1().getReceiver() != null)
                transactionPutTransactionTuple2.getT2().setReceiver(
                        ProductMapper.productToProductEntity(
                                transactionPutTransactionTuple2.getT1().getReceiver()
                        ));

            if (transactionPutTransactionTuple2.getT1().getType() != null)
                transactionPutTransactionTuple2.getT2().setType(
                        transactionPutTransactionTuple2.getT1().getType().getValue());

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
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return null;
        }

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

    public static Transaction transactionPostToTransaction(Tuple2<TransactionPost,Integer> tuple2){
        try{
            if (tuple2.getT1().getHolder() != null && tuple2.getT1().getSignatory() != null)
                throw new Exception("Transaction must have at least one Signatory or Holder");
            Transaction transaction = new Transaction();
            transaction.setNumber(tuple2.getT2());
            transaction.setSender(ProductMapper.productToProductEntity(tuple2.getT1().getSender()));
            if (tuple2.getT1().getReceiver() != null)
                transaction.setReceiver(ProductMapper.productToProductEntity(tuple2.getT1().getReceiver()));

            transaction.setType(tuple2.getT1().getType().getValue());
            transaction.setCreatedDate(LocalDateTime.now());
            if(tuple2.getT1().getHolder() != null)
                transaction.setHolder(PersonMapper.personToPersonEntity(tuple2.getT1().getHolder()));
            if(tuple2.getT1().getSignatory() != null)
                transaction.setSignatory(PersonMapper.personToPersonEntity(tuple2.getT1().getSignatory()));
            transaction.setAmount(tuple2.getT1().getAmount().doubleValue());



            return transaction;
        }  catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return null;
        }
    }

}
