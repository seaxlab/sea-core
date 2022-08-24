package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.component.validator.annotation.NameCheck;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * name validator
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class NameValidator implements ConstraintValidator<NameCheck, String> {

    private NameCheck annotation;

    @Override
    public void initialize(NameCheck annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        //TODO
        return false;
    }
}
