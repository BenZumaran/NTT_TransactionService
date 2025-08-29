package com.nttdata.transaction_service.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-08-29T17:52:57.098615300-05:00[America/Lima]")
@Controller
@RequestMapping("${openapi.transaction.base-path:/api/v1}")
public class TransactionsApiController implements TransactionsApi {

    private final TransactionsApiDelegate delegate;

    public TransactionsApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) TransactionsApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new TransactionsApiDelegate() {});
    }

    @Override
    public TransactionsApiDelegate getDelegate() {
        return delegate;
    }

}
