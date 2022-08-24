package com.github.seaxlab.core.component.validator;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/24
 * @since 1.0
 */
@Slf4j
public class BaseValidatorTest extends BaseCoreTest {

    protected <T> void checkValidator(Set<ConstraintViolation<T>> set) {
        if (Objects.isNull(set) || set.isEmpty()) {
            log.warn("validator set is empty");
            return;
        }

        set.forEach(item -> {
            log.info("{}:{}", item.getPropertyPath().toString(), item.getMessage());
        });

    }
}
