package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionCardDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionPersonDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionPostDTO;
import com.nttdata.transaction_service.dto.transaction.TransactionProductDTO;
import com.nttdata.transaction_service.model.*;
import com.nttdata.transaction_service.model.entity.CardEntity;
import com.nttdata.transaction_service.model.entity.PersonEntity;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

class TransactionMapperTest {

    @Test
    void transactionToTransactionGet() {
        Transaction transaction = Transaction.builder().build();
        //Test empty
        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionToTransactionGet(transaction));
        //Test Class and id
        transaction.setId("transactionid");
        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionToTransactionGet(transaction));
        transaction.setSender(
                ProductEntity.builder()
                        .id("senderid")
                        .build()
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionToTransactionGet(transaction));
        transaction.setAmount(10);

        var response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertInstanceOf(TransactionGet.class, response);
        Assertions.assertEquals("transactionid", response.getId());
        Assertions.assertNotNull(response.getSender());
        Assertions.assertInstanceOf(Product.class, response.getSender());
        Assertions.assertEquals("senderid", response.getSender().getId());
        Assertions.assertEquals(0, response.getNumber());
        Assertions.assertEquals(BigDecimal.valueOf(10.0), response.getAmount());
        Assertions.assertNull(response.getReceiver());
        Assertions.assertNull(response.getType());
        Assertions.assertNull(response.getCreatedDate());
        Assertions.assertNull(response.getHolder());
        Assertions.assertNull(response.getSignatory());
        Assertions.assertNull(response.getCard());
        //Test types
        //Checking
        transaction.setType(TransactionType.DEPOSIT.getValue());
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertEquals(TransactionType.DEPOSIT, response.getType());
        //Wrong
        transaction.setType("OTHER");
        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionToTransactionGet(transaction));
        transaction.setType(TransactionType.DEPOSIT.getValue());
        //Test receiver
        transaction.setReceiver(
                ProductEntity.builder()
                        .id("receiverid")
                        .build()
        );
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertNotNull(response.getReceiver());
        Assertions.assertInstanceOf(Product.class, response.getReceiver());
        //Test Created date
        transaction.setCreatedDate(LocalDateTime.now());
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertNotNull(response.getCreatedDate());
        Assertions.assertInstanceOf(OffsetDateTime.class, response.getCreatedDate());
        //Test Holder
        transaction.setHolder(
                PersonEntity.builder()
                        .id("holderid")
                        .build()
        );
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertNotNull(response.getHolder());
        Assertions.assertInstanceOf(Person.class, response.getHolder());
        Assertions.assertEquals("holderid", response.getHolder().getId());
        //Test Signatory
        transaction.setSignatory(
                PersonEntity.builder()
                        .id("signatoryid")
                        .build()
        );
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertNotNull(response.getSignatory());
        Assertions.assertInstanceOf(Person.class, response.getSignatory());
        Assertions.assertEquals("signatoryid", response.getSignatory().getId());
        //Test Card
        transaction.setCard(
                CardEntity.builder()
                        .id("cardid")
                        .build()
        );
        response = TransactionMapper.transactionToTransactionGet(transaction);
        Assertions.assertNotNull(response.getCard());
        Assertions.assertInstanceOf(Card.class, response.getCard());
        Assertions.assertEquals("cardid", response.getCard().getId());
    }

    @Test
    void transactionPutAndTransactionPreviousToTransaction() {
        Tuple2<TransactionPut, Transaction> tuple2 = Tuples.of(
                new TransactionPut(), Transaction.builder().build()
        );
        //Testing empty
        var response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNull(response.getSender());
        Assertions.assertNull(response.getReceiver());
        Assertions.assertNull(response.getType());
        Assertions.assertEquals(0, response.getAmount());
        Assertions.assertNull(response.getHolder());
        Assertions.assertNull(response.getSignatory());
        Assertions.assertNull(response.getCard());
        //Testing sender
        tuple2.getT1().setSender(
                new Product()
                        .id("senderid")
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getSender());
        Assertions.assertEquals("senderid", response.getSender().getId());
        //Testing receiver
        tuple2.getT1().setReceiver(
                new Product()
                        .id("receiverid")
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getReceiver());
        Assertions.assertEquals("receiverid", response.getReceiver().getId());
        //Testing type
        tuple2.getT1().setType(
                TransactionType.DEPOSIT
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getType());
        Assertions.assertEquals(TransactionType.DEPOSIT.getValue(), response.getType());
        //Testing type
        tuple2.getT1().setAmount(BigDecimal.TEN);
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertEquals(10.0, response.getAmount());
        //Testing holder
        tuple2.getT1().setHolder(
                new Person()
                        .id("personid")
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getHolder());
        Assertions.assertEquals("personid", response.getHolder().getId());
        //Testing signatory
        tuple2.getT1().setSignatory(
                new Person()
                        .id("personid")
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getSignatory());
        Assertions.assertEquals("personid", response.getSignatory().getId());
        //Testing card
        tuple2.getT1().setCard(
                new Card()
                        .id("cardid")
        );
        response = TransactionMapper.transactionPutAndTransactionPreviousToTransaction(tuple2);
        Assertions.assertNotNull(response.getCard());
        Assertions.assertEquals("cardid", response.getCard().getId());
    }

    @Test
    void transactionPostToTransaction() {
        Tuple2<TransactionPost, Integer> tuple2 = Tuples.of(
                new TransactionPost(), 0
        );
        //Testing empty
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> TransactionMapper.transactionPostToTransaction(tuple2)
        );
        //Testing throws
        //With Holder
        tuple2.getT1().setHolder(
                new Person()
                        .id("personid")
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> TransactionMapper.transactionPostToTransaction(tuple2)
        );
        //+ sender
        tuple2.getT1().setSender(
                new Product()
                        .id("senderid")
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> TransactionMapper.transactionPostToTransaction(tuple2)
        );
        //+ type
        tuple2.getT1().setType(
                TransactionType.DEPOSIT
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> TransactionMapper.transactionPostToTransaction(tuple2)
        );
        //+ amount
        tuple2.getT1().setAmount(BigDecimal.TEN);
        //Testing values
        var response = TransactionMapper.transactionPostToTransaction(tuple2);
        //holder
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getHolder());
        Assertions.assertEquals("personid", response.getHolder().getId());
        //sender
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getSender());
        Assertions.assertEquals("senderid", response.getSender().getId());
        //type
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getType());
        Assertions.assertEquals(TransactionType.DEPOSIT.getValue(), response.getType());
        //amount
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertEquals(10.0, response.getAmount());
        //nulls
        Assertions.assertNull(response.getReceiver());
        Assertions.assertNull(response.getSignatory());
        Assertions.assertNull(response.getCard());

        //Testing receiver
        tuple2.getT1().setReceiver(
                new Product()
                        .id("receiverid")
        );
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getReceiver());
        Assertions.assertEquals("receiverid", response.getReceiver().getId());


        //Testing signatory
        tuple2.getT1().setHolder(null);
        tuple2.getT1().setSignatory(
                new Person()
                        .id("personid")
        );
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getSignatory());
        Assertions.assertEquals("personid", response.getSignatory().getId());
        //Testing card
        tuple2.getT1().setCard(
                new Card()
                        .id("cardid")
        );
        response = TransactionMapper.transactionPostToTransaction(tuple2);
        Assertions.assertNotNull(response.getCard());
        Assertions.assertEquals("cardid", response.getCard().getId());
    }

    @Test
    void updatePersonsFromTransactionAndClientResponseDTO() {
        Tuple2<Transaction, ClientResponseDTO> tuple2 = Tuples.of(
                Transaction.builder().build(),
                ClientResponseDTO.builder()
                        .id("personid")
                        .documentNumber("12345678")
                        .firstName("Pedro")
                        .lastName("Perez")
                        .type("PERSONAL")
                        .build()

        );
        var response = TransactionMapper.updatePersonsFromTransactionAndClientResponseDTO(tuple2);
        Assertions.assertNull(response.getSignatory());
        Assertions.assertNull(response.getHolder());

        //Testing Holder
        tuple2.getT1().setHolder(
                PersonEntity.builder()
                        .id("personid")
                        .build()
        );
        response = TransactionMapper.updatePersonsFromTransactionAndClientResponseDTO(tuple2);
        Assertions.assertNotNull(response.getHolder());
        Assertions.assertNull(response.getSignatory());
        Assertions.assertEquals("personid", response.getHolder().getId());
        //Testing signatory
        tuple2.getT1().setHolder(null);
        tuple2.getT1().setSignatory(
                PersonEntity.builder()
                        .id("personid")
                        .build()
        );
        response = TransactionMapper.updatePersonsFromTransactionAndClientResponseDTO(tuple2);
        Assertions.assertNotNull(response.getSignatory());
        Assertions.assertNull(response.getHolder());
        Assertions.assertEquals("personid", response.getSignatory().getId());
    }

    @Test
    void transactionPostDtoToTransactionPost() {
        // Test: missing required fields
        TransactionPostDTO dto = new TransactionPostDTO();
        TransactionProductDTO transactionProductDTO = TransactionProductDTO.builder()
                .id("product1")
                .type(ProductType.SAVINGS_ACCOUNT.getValue())
                .build();
        TransactionPersonDTO transactionPersonDTO = TransactionPersonDTO.builder().id("person1").build();
        TransactionCardDTO transactionCardDTO = TransactionCardDTO.builder().id("card1").build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionPostDtoToTransactionPost(dto));

        // Test: negative amount
        dto.setType("deposit");
        dto.setSender(transactionProductDTO);
        dto.setAmount(-10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> TransactionMapper.transactionPostDtoToTransactionPost(dto));

        // Test: valid minimal DTO
        dto.setAmount(100);
        TransactionPost result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("deposit", result.getType().getValue());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), result.getAmount());
        Assertions.assertNotNull(result.getSender());
        Assertions.assertNull(result.getReceiver());
        Assertions.assertNull(result.getHolder());
        Assertions.assertNull(result.getSignatory());
        Assertions.assertNull(result.getCard());

        // Test: with receiver
        dto.setReceiver(transactionProductDTO);
        result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertNotNull(result.getReceiver());

        // Test: with holder
        dto.setHolder(transactionPersonDTO);
        result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertNotNull(result.getHolder());

        // Test: with signatory
        dto.setSignatory(transactionPersonDTO);
        result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertNotNull(result.getSignatory());

        // Test: with card
        dto.setCard(transactionCardDTO);
        result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertNotNull(result.getCard());

        // Test: type case insensitivity
        dto.setType("deposit");
        result = TransactionMapper.transactionPostDtoToTransactionPost(dto);
        Assertions.assertEquals(TransactionType.DEPOSIT, result.getType());

        dto.setType("WRONG");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> TransactionMapper.transactionPostDtoToTransactionPost(dto)
        );

    }

}