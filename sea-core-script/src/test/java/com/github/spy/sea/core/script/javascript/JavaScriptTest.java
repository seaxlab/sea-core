package com.github.spy.sea.core.script.javascript;

import com.github.spy.sea.core.script.AbstractScriptTest;
import com.github.spy.sea.core.script.ScriptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.SimpleBindings;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class JavaScriptTest extends AbstractScriptTest {

    @Test
    public void run17() throws Exception {
        ScriptEngine engine = ScriptUtil.getScriptEngine("javascript");

        log.info("engine={}", engine);


        Bindings bindings = new SimpleBindings();
        bindings.put("a", "12");

        Double hour = (Double) engine.eval("var date = new Date();  date.getHours();", bindings);

        log.info("hour={}", hour);

        if (engine instanceof Compilable) {
            log.info("engine support compilable.");
            Compilable compEngine = (Compilable) engine;

            compEngine.compile("").eval();
        }

    }
}
