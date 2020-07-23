package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/23
 * @since 1.0
 */
@Slf4j
public class IdUtilTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("{}={}", i, IdUtil.getSimpleId());
        }
    }
}
