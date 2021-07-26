package com.mercado.mutant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
public class MongoTransactionConfig {

    @Bean
    public ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory rdbf) {
        return new ReactiveMongoTransactionManager(rdbf);
    }

    @Bean
    public TransactionalOperator transactionOperator(ReactiveTransactionManager rtm) {
        return TransactionalOperator.create(rtm);
    }

}
