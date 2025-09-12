package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionPostDTO;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.model.TransactionType;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransactionMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionMapper.class);

    // Converts Transaction to TransactionGet
    public static TransactionGet transactionToTransactionGet(Transaction transaction) throws IllegalArgumentException {
        if (
                transaction.getId() == null ||
                        transaction.getSender() == null ||
                        transaction.getAmount() <= 0
        ) throw new IllegalArgumentException("Transaction should have id, sender, amount");
        TransactionGet transactionGet = new TransactionGet();
        transactionGet.setId(transaction.getId());
        transactionGet.setNumber(transaction.getNumber());
        transactionGet.setSender(ProductMapper.productEntityToProduct(transaction.getSender()));
        transactionGet.setAmount(BigDecimal.valueOf(transaction.getAmount()));
        if (transaction.getReceiver() != null) {
            transactionGet.setReceiver(ProductMapper.productEntityToProduct(transaction.getReceiver()));
        }
        if (transaction.getType() != null) {
            transactionGet.setType(TransactionType.fromValue(transaction.getType()));
        }
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
        if (transaction.getCard() != null) {
            transactionGet.setCard(CardMapper.cardEntityToCard(transaction.getCard()));
        }
        return transactionGet;
    }

    //Converts TransactionPut to Transaction
    public static Transaction transactionPutAndTransactionPreviousToTransaction(Tuple2<TransactionPut, Transaction> transactionPutTransactionTuple2) throws IllegalArgumentException {
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
        if (transactionPutTransactionTuple2.getT1().getCard() != null)
            transactionPutTransactionTuple2.getT2().setCard(
                    CardMapper.cardToCardEntity(
                            transactionPutTransactionTuple2.getT1().getCard()
                    ));

        return transactionPutTransactionTuple2.getT2();

    }

    public static Transaction transactionPostToTransaction(Tuple2<TransactionPost, Integer> tuple2) throws IllegalArgumentException {
        if (tuple2.getT1().getHolder() == null &&
                tuple2.getT1().getSignatory() == null)
            throw new IllegalArgumentException("Transaction must have at least one Signatory or Holder");
        if (
                tuple2.getT1().getSender() == null ||
                        tuple2.getT1().getType() == null ||
                        tuple2.getT1().getAmount() == null
        ) throw new IllegalArgumentException("Transaction must have Sender, Type and Correct Amount");
        Transaction transaction = Transaction.builder().build();
        transaction.setNumber(tuple2.getT2() + 1);
        transaction.setSender(ProductMapper.productToProductEntity(tuple2.getT1().getSender()));
        transaction.setType(tuple2.getT1().getType().getValue());
        transaction.setAmount(tuple2.getT1().getAmount().doubleValue());
        transaction.setCreatedDate(LocalDateTime.now());
        if (tuple2.getT1().getReceiver() != null)
            transaction.setReceiver(ProductMapper.productToProductEntity(tuple2.getT1().getReceiver()));
        if (tuple2.getT1().getHolder() != null)
            transaction.setHolder(PersonMapper.personToPersonEntity(tuple2.getT1().getHolder()));
        if (tuple2.getT1().getSignatory() != null)
            transaction.setSignatory(PersonMapper.personToPersonEntity(tuple2.getT1().getSignatory()));
        if (tuple2.getT1().getCard() != null)
            transaction.setCard(CardMapper.cardToCardEntity(tuple2.getT1().getCard()));

        return transaction;
    }


    public static Transaction updatePersonsFromTransactionAndClientResponseDTO(
            Tuple2<Transaction, ClientResponseDTO> tuple2) {
        if (tuple2.getT1().getHolder() != null) {
            tuple2.getT1().setHolder(PersonMapper.clientResponseDtoToPersonEntity(tuple2.getT2()));
            return tuple2.getT1();
        }
        if (tuple2.getT1().getSignatory() != null) {
            tuple2.getT1().setSignatory(PersonMapper.clientResponseDtoToPersonEntity(tuple2.getT2()));
            return tuple2.getT1();
        }
        return tuple2.getT1();
    }

    public static TransactionPost transactionPostDtoToTransactionPost(TransactionPostDTO transactionPostDTO) {
        if (transactionPostDTO.getType() == null ||
                transactionPostDTO.getSender() == null ||
                transactionPostDTO.getAmount() <= 0
        ) throw new IllegalArgumentException("Transaction must have Sender, Type and Correct Amount");

        TransactionPost transactionPost = new TransactionPost();
        transactionPost.setSender(ProductMapper.transactionProductDtoToProduct(transactionPostDTO.getSender()));
        transactionPost.setAmount(BigDecimal.valueOf(transactionPostDTO.getAmount()));
        transactionPost.setType(TransactionType.fromValue(transactionPostDTO.getType()));
        if (transactionPostDTO.getReceiver() != null)
            transactionPost.setReceiver(ProductMapper.transactionProductDtoToProduct(transactionPostDTO.getReceiver()));
        if (transactionPostDTO.getHolder() != null)
            transactionPost.setHolder(PersonMapper.transactionPersonDtoToPerson(transactionPostDTO.getHolder()));
        if (transactionPostDTO.getSignatory() != null)
            transactionPost.setSignatory(PersonMapper.transactionPersonDtoToPerson(transactionPostDTO.getSignatory()));
        if (transactionPostDTO.getType() != null)
            transactionPost.setType(TransactionType.fromValue(transactionPostDTO.getType().toLowerCase()));
        if (transactionPostDTO.getCard() != null)
            transactionPost.setCard(CardMapper.transactionCardDtoToCard(transactionPostDTO.getCard()));

        return transactionPost;
    }
}
