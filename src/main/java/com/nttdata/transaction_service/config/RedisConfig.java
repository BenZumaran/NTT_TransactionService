package com.nttdata.transaction_service.config;

import com.nttdata.transaction_service.dto.transaction.CacheTransactionDTO;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfig {

    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    public ReactiveValueOperations<String, CacheTransactionDTO> redisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<CacheTransactionDTO> valueSerializer = new Jackson2JsonRedisSerializer<>(CacheTransactionDTO.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, CacheTransactionDTO> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String, CacheTransactionDTO> context =
                builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(redisConnectionFactory, context).opsForValue();
    }
/*
    @Bean
    public ReactiveValueOperations<String, CacheTransactionDTO> redisOps(ReactiveRedisTemplate<String, CacheTransactionDTO> redisTemplate) {
        return redisTemplate.opsForValue();
    }

/*


    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<TransactionGet> serializer = new Jackson2JsonRedisSerializer<>(TransactionGet.class);
        serializer.setObjectMapper(new ObjectMapper().registerModule(new JavaTimeModule()));

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer));
        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
     */
}
