package com.github.spy.sea.core.logging;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/19
 * @since 1.0
 */
@Slf4j
public class LogTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        Object[] args = {"1", "2", "3"};

        log.info(" args={}", JSON.toJSONString(args));
        log.info(" args={}", JSON.toJSONString(null));
    }
}
