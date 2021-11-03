package com.github.spy.sea.core.component.condition.judge;

import com.github.spy.sea.core.exception.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/3
 * @since 1.0
 */
@Slf4j
public abstract class AbstractPredicateJudge {

    protected String[] parseArray2(String value) {
        String[] arrays = value.trim().split(",");

        if (arrays.length != 2) {
            ExceptionHandler.publishMsg("arrays length is not 2, plz check.");
        }

        return arrays;
    }
}
