package com.mercado.mutant.web.validation.constraints;

import com.mercado.mutant.web.validation.DnaConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DnaConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dna {
    String message() default "Dna invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
