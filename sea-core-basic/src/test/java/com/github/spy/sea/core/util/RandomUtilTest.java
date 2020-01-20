package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/1/20
 * @since 1.0
 */
@Slf4j
public class RandomUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {

        for (int i = 0; i < 20; i++) {
            log.info("random={}", RandomUtil.oneOf("abc", "bc", "cc"));
        }
    }
}
