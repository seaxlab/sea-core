package com.github.spy.sea.core.checker;

import com.github.spy.sea.core.model.Result;
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
            public Result<Boolean> check(String input) {
                return Result.success(true);
            }
        };

        log.info("checker={}", checker.check("abc"));
    }
}
