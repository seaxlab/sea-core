package com.github.seaxlab.core.checker;

import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.model.checker.Checker;
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
        Checker<String> checker = new Checker<String>() {
            @Override
            public Result<Void> check(String input) {
                return Result.success();
            }
        };

        log.info("checker={}", checker.check("abc"));
    }
}
