package com.mercado.mutant.services.impl;

import com.mercado.mutant.domain.HumanCount;
import com.mercado.mutant.model.Stats;
import com.mercado.mutant.repositories.HumanCountRepository;
import com.mercado.mutant.services.StatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HumanCountRepository humanCountRepository;

    @Override
    public Mono<Stats> getStats() {
        return humanCountRepository.findAll()
                .single(HumanCount.builder()
                        .total(0L)
                        .mutant(0L)
                        .build())
                .map(humanCount -> Stats.builder()
                        .countHumanDna(humanCount.getTotal())
                        .countMutantDna(humanCount.getMutant())
                        .ratio(getRatio(humanCount))
                        .build())
                .doOnError(e -> log.error("Stats could not be retrieved", e));
    }

    private double getRatio(HumanCount humanCount) {
        if (humanCount.getTotal() != 0L) {
            return 1.0 * humanCount.getMutant() / humanCount.getTotal();
        }
        return 0;
    }

}
