package com.mercado.mutant.services;

import com.mercado.mutant.model.DnaSequence;
import reactor.core.publisher.Mono;

public interface HumanService {

    Mono<Boolean> calculateAndSaveHuman(DnaSequence dnaSequence);

}
