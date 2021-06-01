package com.github.spy.sea.core.checker;

import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.model.checker.Checker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/14
 * @since 1.0
 */
@Slf4j
public class CheckerTest {

    @Test
    public void test16() throws Exception {
        Checker<String, Boolean> checker = new Checker<String, Boolean>() {
            @Override
            public BaseResult<Boolean> check(String input) {
                return BaseResult.<Boolean>builder().data(true).build();
            }
        };

        log.info("checker={}", checker.check("abc"));
    }
}
