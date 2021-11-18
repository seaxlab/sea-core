package com.github.spy.sea.core.script.aviator.func;

import com.github.spy.sea.core.script.aviator.AviatorTest;
import com.github.spy.sea.core.script.aviator.func.lang.IfNullFunction;
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
public class IfNullFunctionTest extends AviatorTest {

    @Test
    public void test17() throws Exception {
        AviatorEvaluator.addFunction(new IfNullFunction());
        String expressionStr = "ifnull(userName,'1')";
        Expression expression = AviatorEvaluator.compile(expressionStr);


        Map<String, Object> params = new HashMap<>();
        params.put("userId", "9527");

        Object obj = expression.execute(params);
        log.info("obj={}", obj);
    }
}
