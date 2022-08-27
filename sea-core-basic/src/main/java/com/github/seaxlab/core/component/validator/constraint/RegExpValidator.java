package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.component.validator.annotation.RegExpCheck;
import com.github.seaxlab.core.util.RegExpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * RegExp validator
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class RegExpValidator implements ConstraintValidator<RegExpCheck, String> {

    private RegExpCheck annotation;

    @Override
    public void initialize(RegExpCheck annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return RegExpUtil.is(value, annotation.value().getExpression());
    }
}
