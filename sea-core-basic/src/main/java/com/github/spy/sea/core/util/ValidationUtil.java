package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/12
 * @since 1.0
 */
@Slf4j
public final class ValidationUtil {

    static Validator validator;

    /**
     * get default validate
     *
     * @return
     */
    public static Validator getValidator() {
        if (validator == null) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            validator = validatorFactory.getValidator();
        }
        return validator;
    }

//    static {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }

}
