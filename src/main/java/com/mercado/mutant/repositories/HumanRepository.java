package com.mercado.mutant.repositories;

import com.mercado.mutant.domain.Human;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HumanRepository extends ReactiveMongoRepository<Human, String> {

    Mono<Human> findByDnaSequence(List<String> dnaSequence);

}
