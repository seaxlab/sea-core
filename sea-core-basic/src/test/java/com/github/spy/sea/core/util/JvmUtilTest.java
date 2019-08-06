package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-06
 * @since 1.0
 */
@Slf4j
public class JvmUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {

        log.info(JvmUtil.getPID());
    }
}
