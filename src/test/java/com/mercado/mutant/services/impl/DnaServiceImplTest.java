package com.mercado.mutant.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

class DnaServiceImplTest {

    private DnaServiceImpl dnaService;

    @BeforeEach
    void setUp() {
        dnaService = new DnaServiceImpl();
    }

    @Test
    void givenANonMutantAdnSequenceWhenLookForMutantDnaThenIsFalse() {
        boolean isMutant = dnaService.lookForMutantDna(getNonMutantDnaSequence());

        assertThat(isMutant).isFalse();
    }

    @Test
    void givenAMutantAdnSequenceWhenLookForMutantDnaThenIsTrue() {
        boolean isMutant = dnaService.lookForMutantDna(getMutantDnaSequence());

        assertThat(isMutant).isTrue();
    }

    private ArrayList<String> getNonMutantDnaSequence() {
        return newArrayList("ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG");
    }

    private ArrayList<String> getMutantDnaSequence() {
        return newArrayList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    }
}