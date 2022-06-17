package com.github.seaxlab.core.model;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class ResultTest extends BaseCoreTest {

    @Test
    public void testFieldOrder() throws Exception {
        Result ret = Result.success("abc");
        log.info("{}", JSONUtil.toStr(ret));
    }

    @Test
    public void testNullInstanceOf() throws Exception {
        log.info("{}", null instanceof Result);
    }
}
