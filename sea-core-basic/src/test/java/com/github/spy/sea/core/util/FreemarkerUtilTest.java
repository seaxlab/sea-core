package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/3
 * @since 1.0
 */
@Slf4j
public class FreemarkerUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("user", "smith");
        params.put("age", 10);

        log.info(FreemarkerUtil.replace("hello ${user}", params));
        log.info(FreemarkerUtil.replace("hello ${user},${age}", params));
        //TODO 注意这里的null值
        log.info(FreemarkerUtil.replace("hello ${user},${age},${(abc)!}", params));
        log.info(FreemarkerUtil.replace("hello ${user},${age},${(object.attribute)!'default text'}", params));
    }
}
