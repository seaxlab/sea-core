package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/16
 * @since 1.0
 */
@Slf4j
public class RegExpUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        log.info("{}", RegExpUtil.isCarNumber("浙A10232"));
    }
}
