package com.github.spy.sea.core.script.aviator.func;

import com.github.spy.sea.core.script.aviator.AviatorTest;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
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
public class SumFunctionTest extends AviatorTest {


    @Test
    public void test27() throws Exception {
        String expressionStr = "sum(a,b,c)";
        Expression expression = AviatorEvaluator.compile(expressionStr);

        Map<String, Object> env = new HashMap<>();
        env.put("a", 1);
        env.put("b", 2);
        env.put("c", 3);

        Object result = expression.execute(env);
        log.info("{}", result);
    }
}
