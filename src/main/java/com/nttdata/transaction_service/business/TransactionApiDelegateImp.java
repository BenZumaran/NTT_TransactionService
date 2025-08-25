package com.nttdata.transaction_service.business;

import com.nttdata.transaction_service.api.TransactionsApiDelegate;
import com.nttdata.transaction_service.business.account.AccountService;
import com.nttdata.transaction_service.business.credit.CreditService;
import com.nttdata.transaction_service.dto.credit.CreditResponseDTO;
import com.nttdata.transaction_service.mapper.AccountMapper;
import com.nttdata.transaction_service.mapper.CreditMapper;
import com.nttdata.transaction_service.mapper.TransactionMapper;
import com.nttdata.transaction_service.model.TransactionGet;
import com.nttdata.transaction_service.model.TransactionGetClientBalance;
import com.nttdata.transaction_service.model.TransactionPost;
import com.nttdata.transaction_service.model.TransactionPut;
import com.nttdata.transaction_service.repository.TransactionRepository;
import org.apache.http.client.methods.HttpPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Service
public class TransactionApiDelegateImp implements TransactionsApiDelegate {

    @Autowired
    TransactionRepository repository;

    @Autowired
    AccountService accountService;

    @Autowired
    CreditService creditService;

    private static final Logger log = LoggerFactory.getLogger(TransactionApiDelegateImp.class);

    @Override
    public Mono<ResponseEntity<Flux<TransactionGet>>> transactionsGet(ServerWebExchange exchange) {
        try{
            return Mono.just(new ResponseEntity<>(repository.findAll()
                    .map(TransactionMapper::transactionToTransactionGet), HttpStatus.OK))
                    .doOnSuccess(nonusing -> log.info("Transactions found."))
                    .doOnError(nonusing2 -> log.error("Error at found transactions --> transactionsGet"));

        } catch (Exception ex){
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return  Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public Mono<ResponseEntity<Void>> transactionsIdDelete(String id, ServerWebExchange exchange) {
        try{
            return repository.deleteById(id)
                    .doOnSuccess(nonusing2 -> log.info("Transaction with id {} was deleted.", id))
                    .doOnError(nonusing3 -> log.error("Error at delete transaction --> transactionsIdDelete."))
                    .map(nonusing ->new ResponseEntity<>(HttpStatus.OK));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return  Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        }
    }

    //To Get specific Transaction by ID
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsIdGet(String id, ServerWebExchange exchange) {
        try{
            //Search in DB
            return repository.findById(id)
                    //Map response to TransactionGet to respond
                    .map(TransactionMapper::transactionToTransactionGet)
                    //Map to ResponseEntity for final return
                    .map(transactionGet ->
                                    new ResponseEntity<>(transactionGet,HttpStatus.OK))
                    //Log success
                    .doOnSuccess(nonusing -> log.info("Transaction with id {} was found.",id))
                    //Log error
                    .doOnError(nonusing -> log.error("Error at found transaction --> transactionsIdGet"));
        } catch (Exception ex){
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return  Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsPost(Mono<TransactionPost> transactionPostMono, ServerWebExchange exchange) {

        return transactionPostMono.flatMap(transactionPost -> {
            //Validar si es create
            if (transactionPost.getType().getValue().equals("create")){
                if (
                        transactionPost.getProduct().getType().getValue().equals("savings_account") ||
                                transactionPost.getProduct().getType().getValue().equals("checking_account") ||
                                transactionPost.getProduct().getType().getValue().equals("fixed_term_account")
                ){
                    //Transform TransactionPost to AccountResponseCreateDTO
                    //Call account to save AccountGetCreateDTO
                    return accountService.fetchInsertAccount(AccountMapper.transactionPostToAcoountResponseCreateDTO(transactionPost))
                    //Transform response AccountResponseCreateDTO, TransactionPost to Transaction
                            .map(accountResponseCreateDTO ->
                                    AccountMapper.accountResponseCreateDtoTransactionPostToTransaction(
                                            accountResponseCreateDTO,transactionPost
                                    ))
                    //Finding last number to asign
                            .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                    //Setting last number to transaction
                            .map(tuple2 -> {
                                tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                                return  tuple2.getT1();
                            });
                }
                if (
                        transactionPost.getProduct().getType().getValue().equals("personal_credit") ||
                                transactionPost.getProduct().getType().getValue().equals("business_credit") ||
                                transactionPost.getProduct().getType().getValue().equals("credit_card")
                ){
                    //Transform TransactionPost to CreditCreateDTO
                    //Call credit Create
                    return creditService.fetchInsertCredit(CreditMapper.transactionPostToCreditCreateDto(transactionPost))
                    //Transform response CreditResponseDTO and TransactionPost to Transaction
                            .map(creditResponseDTO ->
                                    CreditMapper.creditResponseDtoTransactionPostToTransaction(
                                            creditResponseDTO,transactionPost
                                    ))
                            //Finding last number to asign
                            .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                            //Setting last number to transaction
                            .map(tuple2 -> {
                                tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                                return  tuple2.getT1();
                            });
                }
            }
            else {
                switch (transactionPost.getType().getValue()){
                    case "deposit":
                        //Call to account with TransactionPost.product.id
                        return accountService.fetchGetAccountById(transactionPost.getProduct().getId())
                        //Update response (+) AccountGetCreateDTO.balance with TransactionPost.amount
                                .map(accountResponseCreateDTO -> AccountMapper
                                        .depositAccountResponseCreateDtoWithTransactionPost(
                                                accountResponseCreateDTO, transactionPost
                                        ))
                        //Call account to save AccountGetCreateDTO
                                .flatMap(accountResponseCreateDTO -> accountService.fetchUpdateAccount(accountResponseCreateDTO))
                        //Transform response AccountGetCreateDTO to Transaction
                                .map(accountResponseCreateDTO -> AccountMapper.accountResponseCreateDtoTransactionPostToTransaction(
                                        accountResponseCreateDTO, transactionPost
                                ))
                                //Finding last number to asign
                                .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                                //Setting last number to transaction
                                .map(tuple2 -> {
                                    tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                                    return  tuple2.getT1();
                                });
                    case "payment":
                        //Transform TransactionPost to CreditPayment
                        //Call credit payment
                        return creditService.fetchApplyPaymentToCredit(
                                CreditMapper.transactionPostToCreditPaymentDto(transactionPost),
                                transactionPost.getProduct().getId()
                        //Transform response CreditUpdateResponseDTO and TransactionPost to Transaction
                        ).map(creditUpdateResponseDTO -> CreditMapper
                                .creditUpdateResponseDtoTransactionPostToTransaction(
                                        creditUpdateResponseDTO, transactionPost
                                ))
                        //Finding last number to asign
                        .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                        //Setting last number to transaction
                        .map(tuple2 -> {
                            tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                            return  tuple2.getT1();
                        });
                    case "withdrawal":
                    case "purchase":
                    case "charge":
                        if (
                                transactionPost.getProduct().getType().getValue().equals("savings_account") ||
                                        transactionPost.getProduct().getType().getValue().equals("checking_account") ||
                                        transactionPost.getProduct().getType().getValue().equals("fixed_term_account")
                        ){
                            //Call to account with TransactionPost.product.id
                            return accountService.fetchGetAccountById(transactionPost.getProduct().getId())
                            //Update response (-) AccountGetCreateDTO.balance with TransactionPost.amount
                                    .map(accountResponseCreateDTO -> AccountMapper
                                            .withdrawalAccountResponseCreateDtoWithTransactionPost(
                                                    accountResponseCreateDTO, transactionPost
                                            ))
                                    //Call account to save AccountGetCreateDTO
                                    .flatMap(accountResponseCreateDTO -> accountService.fetchUpdateAccount(accountResponseCreateDTO))
                                    //Transform response AccountGetCreateDTO to Transaction
                                    .map(accountResponseCreateDTO -> AccountMapper.accountResponseCreateDtoTransactionPostToTransaction(
                                            accountResponseCreateDTO, transactionPost
                                    ))
                                    //Finding last number to asign
                                    .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                                    //Setting last number to transaction
                                    .map(tuple2 -> {
                                        tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                                        return  tuple2.getT1();
                                    });
                        }
                        if (
                                transactionPost.getProduct().getType().getValue().equals("personal_credit") ||
                                        transactionPost.getProduct().getType().getValue().equals("business_credit") ||
                                        transactionPost.getProduct().getType().getValue().equals("credit_card")
                        ){
                            //Call credit payment
                            return creditService.fetchApplyChargeToCredit(
                            //Transform TransactionPost to CreditCharge
                                    CreditMapper.transactionPostToCreditCharge(transactionPost),
                                    transactionPost.getProduct().getId()
                                    //Transform response CreditUpdateResponseDTO and TransactionPost to Transaction
                            ).map(creditUpdateResponseDTO -> CreditMapper
                                    .creditUpdateResponseDtoTransactionPostToTransaction(
                                            creditUpdateResponseDTO, transactionPost
                                    ))
                            //Finding last number to asign
                            .zipWhen(transaction -> repository.findTopNumberByOrderByNumberDesc())
                            //Setting last number to transaction
                            .map(tuple2 -> {
                                tuple2.getT1().setNumber(tuple2.getT2().getNumber()+1);
                                return  tuple2.getT1();
                            });
                        }
                }
            }
            return Mono.error(new Error("Transaction Type not Found. Valid Types: [ deposit, withdrawal, payment, purchase, charge, create ]"));
        })
                .flatMap(repository::save).map(TransactionMapper::transactionToTransactionGet)
                .map(transactionGet -> new ResponseEntity<>(transactionGet,HttpStatus.OK))
                .onErrorResume(error->{
                    log.error(error.getMessage());
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                });

    }

    //To Update specific Transaction
    @Override
    public Mono<ResponseEntity<TransactionGet>> transactionsPut(Mono<TransactionPut> transactionPutMono, ServerWebExchange exchange) {
        try{
            //Zip When to Search with repository by TransactionPut's ID
            return transactionPutMono.zipWhen(transactionPut -> repository.findById(transactionPut.getId()))
                    //Valid only changes sent in TransactionPut
                    .map(TransactionMapper::transactionPutAndTransactionPreviousToTransaction)
                    //Save the response to DB
                    .flatMap(transaction -> repository.save(transaction))
                    //Map to TransactionGet for response purposes
                    .map(TransactionMapper::transactionToTransactionGet)
                    //Map to ResponseEntity to return final response
                    .map(transactionGet -> new ResponseEntity<>(transactionGet,HttpStatus.OK))
                    //Log success of update
                    .doOnSuccess(nonusing ->  log.info("Transaction with was updated."))
                    //Log error
                    .doOnError(nonusing2 -> log.error("Error at update transaction --> transactionsPut"));
        } catch (Exception ex){
            Arrays.stream(ex.fillInStackTrace().getStackTrace()).forEach(stackTraceElement ->
                    log.error(stackTraceElement.toString()));
            return  Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @Override
    public Mono<ResponseEntity<TransactionGetClientBalance>> transactionsProductIdGet(String id, ServerWebExchange exchange){
        return repository.findByProductIdOrderByCreatedDate(id)
                .map(TransactionMapper::transactionToTransactionGet)
                .collectList()
                .zipWhen(transactionGets -> {
                    switch (transactionGets.get(0).getProduct().getType().getValue()){
                        case "savings_account":
                        case "checking_account":
                        case "fixed_term_account":
                            return accountService.fetchGetAccountById(transactionGets.get(0).getProduct().getId())
                                    .map(AccountMapper::accountResponseCreateDtoToProduct);
                        case "personal_credit":
                        case"business_credit":
                        case  "credit_card":
                            return creditService.fetchGetCreditById(transactionGets.get(0).getProduct().getId())
                                    .map(CreditMapper::creditResponseDtoToProduct);
                    }
                    return Mono.error(new Error("Transaction Product Type not Found. Valid Types: [ savings_account, checking_account, fixed_term_account, personal_credit, business_credit, credit_card "));
                }).map(TransactionMapper::productAndTransactionGetListToTransactionGetClientBalance)
                .map(transactionGetClientBalance -> new ResponseEntity<>(transactionGetClientBalance,HttpStatus.OK))
                .doOnSuccess(nousing -> log.info("Information for product with id {} find.",id))
                .onErrorResume(error -> {
                    log.error("Error at find information for Product with id {} --> transactionsProductIdGet",id);
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

}
