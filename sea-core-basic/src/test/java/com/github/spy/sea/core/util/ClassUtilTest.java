package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-08-03
 * @since 1.0
 */
@Slf4j
public class ClassUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Assert.assertEquals(ClassUtil.getFullClassName(String.class), "java.lang.String");
        Assert.assertEquals(ClassUtil.getClassName(String.class), "String");
    }
}
