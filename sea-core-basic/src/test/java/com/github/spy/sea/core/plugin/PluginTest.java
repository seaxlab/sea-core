package com.github.spy.sea.core.plugin;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-14
 * @since 1.0
 */
@Slf4j
public class PluginTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        Map map = new HashMap();

        // 重要：注意这里的顺序，以及执行后的顺序
        map = (Map) new AlwaysMapPlugin().plugin(map);
        map = (Map) new AlwaysMap2Plugin().plugin(map);
        assertEquals("Always2", map.get("Anything"));
    }

    @Intercepts({
            @Signature(type = Map.class, method = "get", args = {Object.class})})
    public static class AlwaysMapPlugin implements Interceptor {
        @Override
        public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {

            log.info("---1 before");
            invocation.proceed();
            log.info("---1 after");
            return "Always";
        }

    }

    @Intercepts({
            @Signature(type = Map.class, method = "get", args = {Object.class})})
    public static class AlwaysMap2Plugin implements Interceptor {
        @Override
        public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {

            log.info("---2 before");
            invocation.proceed();
            log.info("---2 after");
            return "Always2";
        }

    }
}
