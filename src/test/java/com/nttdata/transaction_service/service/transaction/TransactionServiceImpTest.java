package com.nttdata.transaction_service.service.transaction;

import com.nttdata.transaction_service.dto.NumberProjection;
import com.nttdata.transaction_service.dto.client.ClientAddressDTO;
import com.nttdata.transaction_service.dto.client.ClientResponseDTO;
import com.nttdata.transaction_service.model.*;
import com.nttdata.transaction_service.model.entity.PersonEntity;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import com.nttdata.transaction_service.model.entity.Transaction;
import com.nttdata.transaction_service.repository.TransactionRepository;
import com.nttdata.transaction_service.service.client.ClientService;
import com.nttdata.transaction_service.util.TransactionNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@DisplayName("Test Transaction Service")
@ExtendWith(MockitoExtension.class)
class TransactionServiceImpTest {


    @Mock
    TransactionRepository repository;

    @Mock
    ClientService clientService;

    @InjectMocks
    TransactionServiceImp transactionService;

    Flux<Transaction> transactionFlux;
    List<Transaction> transactionList;
    Flux<TransactionGet> transactionGetFlux;
    List<TransactionGet> transactionGetList;
    Flux<Transaction> transaction1Flux;
    Transaction transaction1, transaction2;
    TransactionGet transactionGet1, transactionGet2;
    Mono<Transaction> transaction1Mono, transaction1MonoUpdated, transaction2Mono;
    Mono<TransactionGet> transactionGet1Mono, transactionGet1MonoUpdated, transactionGet2Mono;
    Mono<TransactionPost> transactionPost_HolderDocNumber,
            transactionPost_HolderId,
            transactionPost_SignatoryDocNumber,
            transactionPost_SignatoryId,
            transactionPost_HolderNoDocNoId,
            transactionPost_SignatoryNoDocNoId,
            transactionPost_NoHolderNoSignatory;
    Mono<NumberProjection> numberProjection0, numberProjection1;
    ClientResponseDTO clientResponseDTO;
    Mono<ClientResponseDTO> clientResponseDTOMono;
    Mono<TransactionPut> transactionPutMono;


    @BeforeEach
    void setUp() {

        transactionPutMono = Mono.just(
                new TransactionPut()
                        .id("transaction1")
                        .amount(BigDecimal.valueOf(5))
                        .sender(
                                new Product()
                                        .id("product1")
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .type(ProductType.fromValue("savings_account"))
                        )
                        .holder(
                                new Person()
                                        .id("person1")
                                        .document("12345678")
                                        .fullName("Person 1")
                                        .type(Person.TypeEnum.fromValue("personal"))
                        )
        );

        transactionPost_HolderNoDocNoId = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).holder(
                                new Person()
                                        .fullName("Holder name")
                        )
        );
        transactionPost_SignatoryNoDocNoId = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).signatory(
                                new Person()
                                        .fullName("Signatory name")
                        )
        );
        transactionPost_NoHolderNoSignatory = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        )
        );

        clientResponseDTO = ClientResponseDTO.builder()
                .id("person1")
                .firstName("Person")
                .lastName("1")
                .email("person1@correo.com")
                .phone("987654321")
                .active("ACTIVO")
                .address(ClientAddressDTO
                        .builder()
                        .country("Peru")
                        .city("Lima")
                        .line1("Avenida")
                        .build())
                .segment("basic")
                .documentNumber("12345678")
                .type("PERSONAL")
                .build();
        clientResponseDTOMono = Mono.just(clientResponseDTO);

        numberProjection0 = Mono.just(() -> 0);
        numberProjection1 = Mono.just(() -> 0);

        transactionPost_HolderId = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).holder(
                                new Person()
                                        .id("person3")
                        )
        );

        transactionPost_HolderDocNumber = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).holder(
                                new Person()
                                        .document("12345678")
                        )
        );

        transactionPost_SignatoryId = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).signatory(
                                new Person()
                                        .id("person4")
                        )
        );

        transactionPost_SignatoryDocNumber = Mono.just(
                new TransactionPost()
                        .amount(BigDecimal.ONE)
                        .type(TransactionType.fromValue("deposit"))
                        .sender(
                                new Product()
                                        .id("product3")
                                        .type(ProductType.fromValue("checking_account"))
                                        .number("1234567890")
                                        .balance(BigDecimal.TEN)
                                        .limit(BigDecimal.TEN)
                        ).signatory(
                                new Person()
                                        .document("12345678")
                        )
        );

        transaction1 = Transaction.builder()
                .id("transaction1")
                .number(1)
                .amount(1)
                .sender(
                        ProductEntity.builder()
                                .id("product1")
                                .balance(10)
                                .limit(10)
                                .type("savings_account")
                                .number("1234567890")
                                .build()
                )
                .holder(
                        PersonEntity.builder()
                                .id("person1")
                                .document("12345678")
                                .type("personal")
                                .fullName("Person 1")
                                .build()
                )
                .type("deposit")
                .createdDate(LocalDateTime.now())
                .build();
        transaction2 = Transaction.builder()
                .id("transaction2")
                .number(2)
                .amount(1)
                .sender(
                        ProductEntity.builder()
                                .id("product2")
                                .balance(10)
                                .limit(10)
                                .type("savings_account")
                                .number("1234567890")
                                .build()
                )
                .holder(
                        PersonEntity.builder()
                                .id("person2")
                                .document("12345678")
                                .type("personal")
                                .fullName("Person 2")
                                .build()
                )
                .type("deposit")
                .createdDate(LocalDateTime.now())
                .build();
        transactionList = List.of(transaction1, transaction2);
        transactionFlux = Flux.fromIterable(transactionList);

        transactionGet1 = new TransactionGet()
                .id("transaction1")
                .number(1)
                .amount(BigDecimal.ONE)
                .sender(
                        new Product()
                                .id("product1")
                                .balance(BigDecimal.TEN)
                                .limit(BigDecimal.TEN)
                                .type(ProductType.fromValue("savings_account"))
                                .number("1234567890")
                )
                .holder(
                        new Person()
                                .id("person1")
                                .document("12345678")
                                .type(Person.TypeEnum.fromValue("personal"))
                                .fullName("Person 1")
                )
                .type(TransactionType.fromValue("deposit"))
                .createdDate(OffsetDateTime.now());
        transactionGet2 = new TransactionGet()
                .id("transaction2")
                .number(2)
                .amount(BigDecimal.ONE)
                .sender(
                        new Product()
                                .id("product2")
                                .balance(BigDecimal.TEN)
                                .limit(BigDecimal.TEN)
                                .type(ProductType.fromValue("savings_account"))
                                .number("1234567890")
                )
                .holder(
                        new Person()
                                .id("person2")
                                .document("12345678")
                                .type(Person.TypeEnum.fromValue("personal"))
                                .fullName("Person 2")
                )
                .type(TransactionType.fromValue("deposit"))
                .createdDate(OffsetDateTime.now());
        transactionGetList = List.of(transactionGet1, transactionGet2);
        transactionGetFlux = Flux.fromIterable(transactionGetList);
        transaction1Mono = Mono.just(transaction1);
        transaction2Mono = Mono.just(transaction2);
        transactionGet1Mono = Mono.just(transactionGet1);
        transactionGet2Mono = Mono.just(transactionGet2);

        transactionGet1.setAmount(BigDecimal.valueOf(5));
        transactionGet1MonoUpdated = Mono.just(transactionGet1);
        transaction1.setAmount(5);
        transaction1MonoUpdated = Mono.just(transaction1);

    }


    @Nested
    @DisplayName("Test Transaction Service Get All Transactions")
    class GetAllTransactions {

        @Test
        @DisplayName("Get All Transactions Correctly")
        void get_all_transactions_correctly() {
            when(repository.findAll()).thenReturn(transactionFlux);
            StepVerifier.create(transactionService.getTransactions())
                    .expectSubscription()
                    .expectNextCount(2)
                    .verifyComplete();
            verify(repository, times(1)).findAll();
            StepVerifier.create(transactionService.getTransactions())
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertEquals("transaction1", entity.getId()))
                    .assertNext(entity -> Assertions.assertEquals("transaction2", entity.getId()))
                    .verifyComplete();
            verify(repository, times(2)).findAll();

        }

        @Test
        @DisplayName("Get Error Finding No Transactions")
        void get_all_transactions_empty_response() {
            when(repository.findAll()).thenReturn(Flux.empty());
            StepVerifier.create(transactionService.getTransactions())
                    .expectSubscription()
                    .expectNextCount(0)
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException &&
                                    throwable.getMessage().equals("Not found transactions for current request.")
                    )
                    .verify();
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Managing Exceptions")
        void get_all_transactions_error() {
            when(repository.findAll()).thenThrow(new RuntimeException("Generated exception"));
            StepVerifier.create(transactionService.getTransactions())
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();

            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Test Transaction Service Get Transaction By Id")
    class GetTransactionById {
        @Test
        @DisplayName("Get Transaction Correctly")
        void get_transaction_id_correctly() {
            when(repository.findById(anyString())).thenReturn(transaction1Mono);
            final String id = "transaction1";
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            verify(repository, times(1)).findById(id);
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertEquals(id, entity.getId()))
                    .verifyComplete();
            verify(repository, times(2)).findById(id);
        }

        @Test
        @DisplayName("Get Transaction Empty")
        void get_transaction_id_empty_response() {
            final String id = "none";
            when(repository.findById(anyString())).thenReturn(Mono.empty());
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyError();
            verify(repository, times(1)).findById(id);
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException
                    )
                    .verify();
            verify(repository, times(2)).findById(id);
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable.getMessage().equals("Transaction with ID " + id + " not found.")
                    )
                    .verify();
            verify(repository, times(3)).findById(id);
        }

        @Test
        @DisplayName("Managing Exception")
        void get_transaction_id_error() {
            final String id = "none";
            when(repository.findById(anyString())).thenThrow(new RuntimeException("Generated exception"));
            StepVerifier.create(transactionService.getTransactionById(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();

            verify(repository, times(1)).findById(anyString());
        }
    }

    @Nested
    @DisplayName("Test Transaction Service Delete Transaction By Id")
    class DeleteTransactionById {
        @Test
        @DisplayName("Get Transaction Correctly")
        void delete_transaction_id_correctly() {
            when(repository.deleteById(anyString())).thenReturn(Mono.empty());
            final String id = "transaction1";
            StepVerifier.create(transactionService.deleteTransactionById(id))
                    .expectSubscription()
                    .verifyComplete();
            verify(repository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Managing Exception")
        void delete_transaction_id_error() {
            final String id = "none";
            when(repository.deleteById(anyString())).thenThrow(new RuntimeException("Generated exception"));
            StepVerifier.create(transactionService.deleteTransactionById(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();

            verify(repository, times(1)).deleteById(anyString());
        }
    }

    @Nested
    @DisplayName("Test Transaction Service Post Transaction By Id")
    class PostTransactionById {
        @Test
        @DisplayName("Post Transaction Correctly")
        void post_transaction_correctly() {

            when(repository.save(any(Transaction.class))).thenReturn(transaction1Mono);
            when(clientService.fetchGetClientById(anyString())).thenReturn(clientResponseDTOMono);
            when(clientService.fetchGetClientByDocument(anyString())).thenReturn(clientResponseDTOMono);
            when(repository.findTopNumberByOrderByNumberDesc()).thenReturn(numberProjection0);

            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderId))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertEquals("transaction1", entity.getId()))
                    .verifyComplete();
            //When Holder with id no document and number 0
            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderId))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            //When Holder with document no id
            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderDocNumber))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertEquals(1, entity.getNumber()))
                    .verifyComplete();

            when(repository.findTopNumberByOrderByNumberDesc()).thenReturn(numberProjection1);
            when(repository.save(any(Transaction.class))).thenReturn(transaction2Mono);
            StepVerifier.create(transactionService.insertTransaction(transactionPost_SignatoryId))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            StepVerifier.create(transactionService.insertTransaction(transactionPost_SignatoryDocNumber))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertEquals(2, entity.getNumber()))
                    .verifyComplete();
            verify(repository, times(5)).save(any(Transaction.class));
            verify(repository, times(5)).findTopNumberByOrderByNumberDesc();
            verify(clientService, times(3)).fetchGetClientById(anyString());
            verify(clientService, times(2)).fetchGetClientByDocument(anyString());

        }

        @Test
        @DisplayName("Post Transaction Error")
        void post_transaction_bad_request() {
            when(repository.findTopNumberByOrderByNumberDesc()).thenReturn(numberProjection0);

            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderNoDocNoId))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable instanceof IllegalArgumentException &&
                                    throwable.getMessage().equals("Holder or Signatory should have at least document or id")
                    )
                    .verify();

            transaction1.setType("incorrect_type");
            when(repository.findTopNumberByOrderByNumberDesc()).thenReturn(numberProjection0);
            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderId))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable instanceof NullPointerException
                    )
                    .verify();

            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderNoDocNoId))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable instanceof IllegalArgumentException &&
                                    throwable.getMessage().equals("Holder or Signatory should have at least document or id")
                    )
                    .verify();

            when(repository.findTopNumberByOrderByNumberDesc()).thenThrow(new RuntimeException("any error"));

            StepVerifier.create(transactionService.insertTransaction(transactionPost_HolderNoDocNoId))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyError();

            verify(repository, times(4)).findTopNumberByOrderByNumberDesc();
        }
    }

    @Nested
    @DisplayName("Test Transaction Service Update Transaction")
    class UpdateTransaction {
        @Test
        @DisplayName("Update Transaction Correctly")
        void update_transaction_correctly() {

            when(repository.findById(anyString())).thenReturn(transaction1Mono);
            when(repository.save(any(Transaction.class))).thenReturn(transaction1MonoUpdated);

            StepVerifier.create(transactionService.updateTransaction(transactionPutMono))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();

            StepVerifier.create(transactionService.updateTransaction(transactionPutMono))
                    .expectSubscription()
                    .assertNext(
                            entity -> Assertions.assertEquals(
                                    BigDecimal.valueOf(5.0), entity.getAmount()
                            ))
                    .verifyComplete();


            verify(repository, times(2)).findById(anyString());
            verify(repository, times(2)).save(any(Transaction.class));

        }

        @Test
        @DisplayName("Update Transaction Error")
        void update_transaction_bad_request() {
            when(repository.findById(anyString())).thenReturn(Mono.empty());

            StepVerifier.create(transactionService.updateTransaction(transactionPutMono))
                    .expectSubscription()
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException &&
                                    throwable.getMessage().equals("Transaction with ID " + "transaction1" + " not found.")
                    )
                    .verify();


            when(repository.findById(anyString())).thenReturn(transaction1Mono);
            when(repository.save(any(Transaction.class))).thenThrow(new RuntimeException("any error"));

            StepVerifier.create(transactionService.updateTransaction(transactionPutMono))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyError();

            when(repository.findById(anyString())).thenThrow(new RuntimeException("any error"));
            StepVerifier.create(transactionService.updateTransaction(transactionPutMono))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyError();

            verify(repository, times(3)).findById(anyString());
            verify(repository, times(1)).save(any(Transaction.class));
        }

    }

    @Nested
    @DisplayName("Test Transaction Service Get By Product Id")
    class GetTransactionsByProductId {
        @Test
        @DisplayName("Get Transactions By Product Correctly")
        void get_transactions_product_id_correctly() {
            final String id = "product1";
            when(repository.findBySenderIdOrderByCreatedDate(anyString())).thenReturn(Flux.just(transaction1));

            StepVerifier.create(transactionService.getTransactionsByProductId(id))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            StepVerifier.create(transactionService.getTransactionsByProductId(id))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertInstanceOf(TransactionGet.class, entity))
                    .verifyComplete();

            verify(repository, times(2))
                    .findBySenderIdOrderByCreatedDate(anyString());
        }

        @Test
        @DisplayName("Get Transactions By Product Error")
        void get_transactions_product_id_error() {
            final String id = "transaction1";
            when(repository.findBySenderIdOrderByCreatedDate(anyString()))
                    .thenReturn(Flux.empty());
            StepVerifier.create(transactionService.getTransactionsByProductId(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException &&
                                    throwable.getMessage().equals("Not found transactions for current request.")
                    )
                    .verify();

            transaction1.setType("incorrect_type");
            when(repository.findBySenderIdOrderByCreatedDate(anyString()))
                    .thenReturn(Flux.just(transaction1));
            StepVerifier.create(transactionService.getTransactionsByProductId(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .expectErrorMatches(
                            throwable -> throwable instanceof IllegalArgumentException
                    )
                    .verify();

            when(repository.findBySenderIdOrderByCreatedDate(anyString()))
                    .thenThrow(new RuntimeException("any error"));
            StepVerifier.create(transactionService.getTransactionsByProductId(id))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();

            verify(repository, times(3)).findBySenderIdOrderByCreatedDate(anyString());
        }

    }

    @Nested
    @DisplayName("Test Transaction Service Get By Client Document")
    class GetTransactionsByClientDocument {
        @Test
        @DisplayName("Get Transactions By Client Correctly")
        void get_transactions_product_id_correctly() {
            final String id = "person1",
                    from = "2025-08-26T20:39:47",
                    to = "2025-08-29T22:16:08";
            when(repository.findByHolderDocumentAndCreatedDateBetween(
                    anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(Flux.just(transaction1));

            StepVerifier.create(transactionService
                            .getTransactionsByClientDocument(id, from, to))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            StepVerifier.create(transactionService
                            .getTransactionsByClientDocument(id, from, to))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertInstanceOf(TransactionGet.class, entity))
                    .verifyComplete();

            verify(repository, times(2))
                    .findByHolderDocumentAndCreatedDateBetween(
                            anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        }

        @Test
        @DisplayName("Get Transactions By Product Error")
        void get_transactions_product_id_error() {
            final String id = "person1",
                    from = "2025-08-26T20:39:47",
                    to = "2025-08-29T22:16:08";
            when(repository.findByHolderDocumentAndCreatedDateBetween(
                    anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(Flux.empty());

            StepVerifier.create(transactionService
                            .getTransactionsByClientDocument(id, from, to))
                    .expectSubscription()
                    .expectNextCount(0)
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException &&
                                    throwable.getMessage().equals("Not found transactions for current request.")
                    )
                    .verify();

            when(repository.findByHolderDocumentAndCreatedDateBetween(
                    anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenThrow(new RuntimeException("any"));

            StepVerifier.create(transactionService
                            .getTransactionsByClientDocument(id, from, to))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();

            verify(repository, times(2))
                    .findByHolderDocumentAndCreatedDateBetween(
                            anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        }

    }

    @Nested
    @DisplayName("Test Transaction Service Get By Type And Product Id")
    class GetTransactionsByTypeAndProductId {
        @Test
        @DisplayName("Get Transactions By Type Correctly")
        void get_transactions_type_product_id_correctly() {
            final String id = "person1",
                    type = "savings_account",
                    from = "2025-08-26T20:39:47",
                    to = "2025-08-29T22:16:08";
            when(repository.findByTypeAndSenderIdAndCreatedDateBetween(
                    anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(Flux.just(transaction1));

            StepVerifier.create(transactionService
                            .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
            StepVerifier.create(transactionService
                            .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to))
                    .expectSubscription()
                    .assertNext(entity -> Assertions.assertInstanceOf(TransactionGet.class, entity))
                    .verifyComplete();

            verify(repository, times(2))
                    .findByTypeAndSenderIdAndCreatedDateBetween(
                            anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        }

        @Test
        @DisplayName("Get Transactions By Type Error")
        void get_transactions_type_product_id_error() {
            final String id = "person1";
            String type = "savings_account",
                    from = "2025-08-26T20:39:47",
                    to = "2025-08-29T22:16:08";

            when(repository.findByTypeAndSenderIdAndCreatedDateBetween(
                    anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(Flux.empty());

            StepVerifier.create(transactionService
                            .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to))
                    .expectSubscription()
                    .expectNextCount(0)
                    .expectErrorMatches(
                            throwable -> throwable instanceof TransactionNotFoundException &&
                                    throwable.getMessage().equals("Not found transactions for current request.")
                    )
                    .verify();
            transaction1.setType("incorrect_type");
            when(repository.findByTypeAndSenderIdAndCreatedDateBetween(
                    anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                    .thenReturn(Flux.just(transaction1));
            StepVerifier.create(transactionService
                            .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyError();
            to = "2025-08-29";
            StepVerifier.create(transactionService
                            .getTransactionsByTypeAndProductIdBetweenDates(id, type, from, to))
                    .expectSubscription()
                    .expectNextCount(0)
                    .verifyComplete();
            verify(repository, times(2))
                    .findByTypeAndSenderIdAndCreatedDateBetween(
                            anyString(), anyString(), any(LocalDateTime.class), any(LocalDateTime.class));

        }

    }

}