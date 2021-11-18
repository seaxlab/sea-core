package com.github.spy.sea.core.script.aviator;

import com.github.spy.sea.core.script.AbstractScriptTest;
import com.github.spy.sea.core.script.ScriptUtil;
import com.github.spy.sea.core.script.aviator.func.lang.IfNullFunction;
import com.github.spy.sea.core.script.aviator.func.lang.SumFunction;
import com.googlecode.aviator.AviatorEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/11/18
 * @since 1.0
 */
@Slf4j
public class AviatorTest extends AbstractScriptTest {

    @Before
    public void before() {
        AviatorEvaluator.addFunction(new IfNullFunction());
        AviatorEvaluator.addFunction(new SumFunction());
    }

    @Test
    public void test15() throws Exception {
        ScriptEngine scriptEngine = ScriptUtil.getScriptEngine("aviator");
        log.info("se={}", scriptEngine);
    }


}
