package com.mercado.mutant.services;

import com.mercado.mutant.model.Stats;
import reactor.core.publisher.Mono;

public interface StatsService {

    Mono<Stats> getStats();

}
