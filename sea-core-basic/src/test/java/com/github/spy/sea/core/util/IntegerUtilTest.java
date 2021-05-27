package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/27
 * @since 1.0
 */
@Slf4j
public class IntegerUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        String str = "1, 2, 3,, ,";
        Integer[] values = IntegerUtil.split(str, ',');
        log.info("values={}", ListUtil.toList(values));
    }
}
