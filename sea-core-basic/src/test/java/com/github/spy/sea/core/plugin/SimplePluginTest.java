package com.github.spy.sea.core.plugin;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-14
 * @since 1.0
 */
@Slf4j
public class SimplePluginTest extends BaseCoreTest {

    @Test
    void mapPluginShouldInterceptGet() {
        Map map = new HashMap();
        map = (Map) new AlwaysMapPlugin().plugin(map);
        assertEquals("Always", map.get("Anything"));
    }

    @Test
    void shouldNotInterceptToString() {
        Map map = new HashMap();
        map = (Map) new AlwaysMapPlugin().plugin(map);
        assertNotEquals("Always", map.toString());
    }

    @Intercepts({
            @Signature(type = Map.class, method = "get", args = {Object.class})})
    public static class AlwaysMapPlugin implements Interceptor {
        @Override
        public Object intercept(Invocation invocation) {
            return "Always";
        }

    }
}
