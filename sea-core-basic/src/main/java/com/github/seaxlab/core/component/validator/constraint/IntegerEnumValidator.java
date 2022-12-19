package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.component.validator.annotation.IntegerEnum;
import com.github.seaxlab.core.util.EqualUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Integer enum validator
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class IntegerEnumValidator implements ConstraintValidator<IntegerEnum, Integer> {

    private IntegerEnum annotation;

    @Override
    public void initialize(IntegerEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        for (int item : annotation.values()) {
            if (EqualUtil.isEq(item, value)) {
                return true;
            }
        }
        return false;
    }
}
