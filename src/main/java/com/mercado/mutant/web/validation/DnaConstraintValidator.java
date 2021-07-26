package com.mercado.mutant.web.validation;

import com.mercado.mutant.web.validation.constraints.Dna;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class DnaConstraintValidator implements ConstraintValidator<Dna, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        return value == null ||
                value.stream().allMatch(s -> s.length() == value.size() && s.matches("^[ATCG]+$"));
    }

}
