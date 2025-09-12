package com.nttdata.transaction_service.dto.transaction;

import com.nttdata.transaction_service.model.entity.CardEntity;
import com.nttdata.transaction_service.model.entity.PersonEntity;
import com.nttdata.transaction_service.model.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("transaction")
@Data
@Builder
public class CacheTransactionDTO implements Serializable {
    @Id
    private String id;
    private int number;
    private ProductEntity sender;
    private ProductEntity receiver;
    private String type;
    private double amount;
    private LocalDateTime createdDate;
    private PersonEntity holder;
    private PersonEntity signatory;
    private CardEntity card;
}
