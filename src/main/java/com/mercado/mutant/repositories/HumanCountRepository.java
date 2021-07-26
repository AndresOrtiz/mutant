package com.mercado.mutant.repositories;

import com.mercado.mutant.domain.HumanCount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HumanCountRepository extends ReactiveMongoRepository<HumanCount, String> {
}
