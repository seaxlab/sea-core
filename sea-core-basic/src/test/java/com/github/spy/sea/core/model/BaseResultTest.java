package com.github.spy.sea.core.model;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.JSONUtil;
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
public class BaseResultTest extends BaseCoreTest {

    @Test
    public void testFieldOrder() throws Exception {
        BaseResult ret = BaseResult.success("abc");
        log.info("{}", JSONUtil.toStr(ret));
    }

    @Test
    public void testNullInstanceOf() throws Exception {
        log.info("{}", null instanceof BaseResult);
    }
}
