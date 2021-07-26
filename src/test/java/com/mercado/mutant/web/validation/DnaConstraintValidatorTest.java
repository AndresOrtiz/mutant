package com.mercado.mutant.web.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

class DnaConstraintValidatorTest {

    private DnaConstraintValidator dnaConstraintValidator;

    @BeforeEach
    void setUp() {
        dnaConstraintValidator = new DnaConstraintValidator();
    }

    @Test
    void givenANullDnaWhenIsValidThenIsTrue() {
        boolean actual = dnaConstraintValidator
                .isValid(null, null);

        assertThat(actual).isTrue();
    }

    @Test
    void givenANonSquareDnaWhenIsValidThenIsFalse() {
        boolean actual = dnaConstraintValidator
                .isValid(newArrayList("ATCG", "ATC", "ATCG", "ATCG"), null);

        assertThat(actual).isFalse();
    }

    @Test
    void givenADnaContainingInvalidCharactersWhenIsValidThenIsFalse() {
        boolean actual = dnaConstraintValidator
                .isValid(newArrayList("ATCZ", "ATCG", "ATCG", "ATCG"), null);

        assertThat(actual).isFalse();
    }

    @Test
    void givenAValidDnaWhenIsValidThenIsTrue() {
        boolean actual = dnaConstraintValidator
                .isValid(newArrayList("ATCG", "ATCG", "ATCG", "ATCG"), null);

        assertThat(actual).isTrue();
    }

}