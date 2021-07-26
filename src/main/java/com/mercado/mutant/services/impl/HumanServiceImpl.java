package com.mercado.mutant.services.impl;

import com.mercado.mutant.domain.Human;
import com.mercado.mutant.domain.HumanCount;
import com.mercado.mutant.model.DnaSequence;
import com.mercado.mutant.repositories.HumanCountRepository;
import com.mercado.mutant.repositories.HumanRepository;
import com.mercado.mutant.services.DnaService;
import com.mercado.mutant.services.HumanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HumanServiceImpl implements HumanService {

    private final HumanRepository humanRepository;
    private final HumanCountRepository humanCountRepository;
    private final DnaService dnaService;

    @Override
    @Transactional
    public Mono<Boolean> calculateAndSaveHuman(DnaSequence dnaSequence) {
        return humanRepository.findByDnaSequence(dnaSequence.getDna())
                .switchIfEmpty(Mono.defer(() -> createNewHuman(dnaSequence.getDna())))
                .map(Human::isMutant);
    }

    private Mono<Human> createNewHuman(List<String> dna) {
        return humanRepository.save(Human.builder()
                .dnaSequence(dna)
                .isMutant(dnaService.lookForMutantDna(dna))
                .build())
                .flatMap(this::updateHumanCount)
                .doOnError(e -> log.error("Human can not be created: " + dna, e))
                .doOnSuccess(human -> log.info("Human created successfully:" + human.getSecond().getId()))
                .map(Pair::getSecond);
    }

    private Mono<Pair<HumanCount, Human>> updateHumanCount(Human human) {
        return humanCountRepository.findAll()
                .single(HumanCount.builder()
                        .total(0L)
                        .mutant(0L)
                        .build())
                .map(humanCount -> HumanCount.builder()
                        .id(humanCount.getId())
                        .total(humanCount.getTotal() + 1)
                        .mutant(humanCount.getMutant() + (human.isMutant() ? 1L : 0L))
                        .build())
                .flatMap(humanCountRepository::save)
                .map(humanCount -> Pair.of(humanCount, human));
    }

}
