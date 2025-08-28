package com.nttdata.transaction_service.api;

import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.Part;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link TransactionsApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-27T20:13:55.820311800-05:00[America/Lima]")
public interface TransactionsApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /transactions : Get List of all transactions
     *
     * @return List of transactions (status code 200)
     * @see TransactionsApi#transactionsGet
     */
    default Mono<ResponseEntity<Flux<TransactionGet>>> transactionsGet(ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * DELETE /transactions/{id} : Delete a transaction
     *
     * @param id  (required)
     * @return Transaction deleted (status code 204)
     * @see TransactionsApi#transactionsIdDelete
     */
    default Mono<ResponseEntity<Void>> transactionsIdDelete(String id,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        return result.then(Mono.empty());

    }

    /**
     * GET /transactions/{id} : Get a transaction by ID
     *
     * @param id  (required)
     * @return A single transaction (status code 200)
     *         or Transaction not found (status code 404)
     * @see TransactionsApi#transactionsIdGet
     */
    default Mono<ResponseEntity<TransactionGet>> transactionsIdGet(String id,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * POST /transactions : Create a new transaction
     *
     * @param transactionPost  (required)
     * @return Transaction Created (status code 201)
     * @see TransactionsApi#transactionsPost
     */
    default Mono<ResponseEntity<TransactionGet>> transactionsPost(Mono<TransactionPost> transactionPost,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * GET /transactions/product/{id} : Get Transactions and Products Balance by Client&#39;s Product
     *
     * @param id  (required)
     * @return Product information found (status code 200)
     * @see TransactionsApi#transactionsProductIdGet
     */
    default Mono<ResponseEntity<TransactionGetClientBalance>> transactionsProductIdGet(String id,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"product\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"transactions\" : [ { \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }, { \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } } ] }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * PUT /transactions : Update a transaction (Fields except id are optionals)
     *
     * @param transactionPut  (required)
     * @return Transaction updated (status code 200)
     * @see TransactionsApi#transactionsPut
     */
    default Mono<ResponseEntity<TransactionGet>> transactionsPut(Mono<TransactionPut> transactionPut,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

    /**
     * GET /transactions/{type}/product/{id} : Get Transactions and Products Balance by Client&#39;s Product
     *
     * @param id  (required)
     * @param type  (required)
     * @param from  (required)
     * @param to  (required)
     * @return List of transactions (status code 200)
     * @see TransactionsApi#transactionsTypeProductIdGet
     */
    default Mono<ResponseEntity<Flux<TransactionGet>>> transactionsTypeProductIdGet(String id,
        String type,
        String from,
        String to,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"number\" : 0, \"amount\" : 5.962133916683182, \"createdDate\" : \"2000-01-23T04:56:07.000+00:00\", \"receiver\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"sender\" : { \"number\" : \"number\", \"balance\" : 6.027456183070403, \"limit\" : 1.4658129805029452, \"id\" : \"id\" }, \"holder\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" }, \"id\" : \"id\", \"signatory\" : { \"document\" : \"document\", \"fullName\" : \"fullName\", \"id\" : \"id\", \"type\" : \"personal\" } }";
                result = ApiUtil.getExampleResponse(exchange, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

}
