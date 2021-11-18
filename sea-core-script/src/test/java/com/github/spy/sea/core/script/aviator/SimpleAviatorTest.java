package com.github.spy.sea.core.script.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/18
 * @since 1.0
 */
@Slf4j
public class SimpleAviatorTest {

    @Test
    public void test16() throws Exception {
        Map<String, Object> env = new HashMap<String, Object>() {{
            put("a", 3);
            put("b", 2);
            put("c", 5);
            put("d", -2);
        }};
        String expression = " (a > b) && (c > 0 || d > 0) ? a : b";
        System.out.println(AviatorEvaluator.execute(expression, env));
    }
}
