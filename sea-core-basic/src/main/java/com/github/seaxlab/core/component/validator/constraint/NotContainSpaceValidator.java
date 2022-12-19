package com.github.seaxlab.core.component.validator.constraint;

import com.github.seaxlab.core.common.SymbolConst;
import com.github.seaxlab.core.component.validator.annotation.NotContainSpace;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * not contain space validator.
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class NotContainSpaceValidator implements ConstraintValidator<NotContainSpace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return !value.contains(SymbolConst.SPACE);
    }
}
