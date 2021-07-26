package com.mercado.mutant.services.impl;

import com.mercado.mutant.domain.HumanCount;
import com.mercado.mutant.model.Stats;
import com.mercado.mutant.repositories.HumanCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

    @Mock
    private HumanCountRepository humanCountRepository;

    private StatsServiceImpl statsService;

    @BeforeEach
    void setUp() {
        statsService = new StatsServiceImpl(humanCountRepository);
    }

    @Test
    void givenAnEmptyTotalElementsFluxWhenGetStatsThenZeroStats() {
        when(humanCountRepository.findAll()).thenReturn(Flux.empty());

        StepVerifier
                .create(statsService.getStats())
                .expectNext(Stats.builder().countHumanDna(0L).countMutantDna(0L).ratio(0).build())
                .expectComplete()
                .verify();
    }

    @Test
    void givenATotalElementsFluxWhenGetStatsThenRightStats() {
        when(humanCountRepository.findAll()).thenReturn(Flux.fromIterable(newArrayList(HumanCount.builder()
                .total(1L)
                .mutant(1L)
                .build())));

        StepVerifier
                .create(statsService.getStats())
                .expectNext(Stats.builder().countHumanDna(1L).countMutantDna(1L).ratio(1).build())
                .expectComplete()
                .verify();
    }

    @Test
    void givenATotalElementsFluxWithMoreThaOneElementWhenGetStatsThenError() {
        when(humanCountRepository.findAll()).thenReturn(Flux.fromIterable(newArrayList(HumanCount.builder()
                        .total(1L).mutant(1L).build(),
                HumanCount.builder().total(2L).mutant(2L).build())));

        StepVerifier
                .create(statsService.getStats())
                .expectErrorMatches(throwable -> throwable instanceof IndexOutOfBoundsException)
                .verify();
    }
}