package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.User;
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

    @Test
    public void run25() throws Exception {
        String str = "1";

        Assert.assertEquals(true, ClassUtil.isOneOfClasses(str, String.class));
        Assert.assertEquals(false, ClassUtil.isOneOfClasses(str, JSONObject.class, JSONArray.class));
    }

    @Test
    public void run35() throws Exception {

        User user = new User();
        log.info("{}", ClassUtil.getClassName(user));
    }
}
