package com.mercado.mutant.services.impl;

import com.mercado.mutant.TestUtils;
import com.mercado.mutant.domain.Human;
import com.mercado.mutant.domain.HumanCount;
import com.mercado.mutant.model.DnaSequence;
import com.mercado.mutant.repositories.HumanCountRepository;
import com.mercado.mutant.repositories.HumanRepository;
import com.mercado.mutant.services.DnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HumanServiceImplTest {

    @Mock
    private HumanRepository humanRepository;

    @Mock
    private HumanCountRepository humanCountRepository;

    @Mock
    private DnaService dnaService;

    private HumanServiceImpl humanService;

    @BeforeEach
    void setUp() {
        humanService = new HumanServiceImpl(humanRepository, humanCountRepository, dnaService);
    }

    @Test
    void givenANewDnaMutantSequenceWhenCalculateAndSaveHumanThenHumanIsSavedAndCountIsUpdated() {
        when(humanRepository.findByDnaSequence(anyList())).thenReturn(Mono.empty());
        when(dnaService.lookForMutantDna(anyList())).thenReturn(true);
        when(humanRepository.save(any(Human.class))).thenAnswer(TestUtils.getMonoFromFirstArgument());
        when(humanCountRepository.findAll()).thenReturn(Flux.empty());
        when(humanCountRepository.save(any(HumanCount.class))).thenAnswer(TestUtils.getMonoFromFirstArgument());

        StepVerifier
                .create(humanService.calculateAndSaveHuman(DnaSequence.builder().dna(newArrayList()).build()))
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    void givenANewDnaNonMutantSequenceWhenCalculateAndSaveHumanThenHumanIsSavedAndCountIsUpdated() {
        when(humanRepository.findByDnaSequence(anyList())).thenReturn(Mono.empty());
        when(dnaService.lookForMutantDna(anyList())).thenReturn(false);
        when(humanRepository.save(any(Human.class))).thenAnswer(TestUtils.getMonoFromFirstArgument());
        when(humanCountRepository.findAll()).thenReturn(Flux.empty());
        when(humanCountRepository.save(any(HumanCount.class))).thenAnswer(TestUtils.getMonoFromFirstArgument());

        StepVerifier
                .create(humanService.calculateAndSaveHuman(DnaSequence.builder().dna(newArrayList()).build()))
                .expectNext(false)
                .expectComplete()
                .verify();
    }

    @Test
    void givenAnExistentDnaSequenceWhenCalculateAndSaveHumanThenAnswerIsReturned() {
        when(humanRepository.findByDnaSequence(anyList()))
                .thenReturn(Mono.just(Human.builder().isMutant(true).build()));

        StepVerifier
                .create(humanService.calculateAndSaveHuman(DnaSequence.builder().dna(newArrayList()).build()))
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    void givenMoreThanOneCountWhenCalculateMutantAndSaveThenThereIsAnError() {
        when(humanRepository.findByDnaSequence(anyList())).thenReturn(Mono.empty());
        when(dnaService.lookForMutantDna(anyList())).thenReturn(true);
        when(humanRepository.save(any(Human.class))).thenAnswer(TestUtils.getMonoFromFirstArgument());
        when(humanCountRepository.findAll()).thenReturn(Flux.just(HumanCount.builder().build(),
                HumanCount.builder().build()));

        StepVerifier
                .create(humanService.calculateAndSaveHuman(DnaSequence.builder().dna(newArrayList()).build()))
                .expectErrorMatches(throwable -> throwable instanceof IndexOutOfBoundsException)
                .verify();
    }

}