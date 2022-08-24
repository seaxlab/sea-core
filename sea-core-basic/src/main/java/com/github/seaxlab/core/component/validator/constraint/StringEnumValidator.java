package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.component.validator.annotation.StringEnum;
import com.github.seaxlab.core.util.EqualUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * String enum validator
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class StringEnumValidator implements ConstraintValidator<StringEnum, String> {

    private String[] values;

    @Override
    public void initialize(StringEnum annotation) {
        this.values = annotation.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        for (String item : values) {
            if (EqualUtil.isEq(item, value.trim())) {
                return true;
            }
        }
        return false;
    }
}
