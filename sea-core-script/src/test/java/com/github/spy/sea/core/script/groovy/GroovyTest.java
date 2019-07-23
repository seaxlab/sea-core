package com.github.spy.sea.core.script.groovy;

import com.github.spy.sea.core.script.AbstractScriptTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Date;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-23
 * @since 1.0
 */
@Slf4j
public class GroovyTest extends AbstractScriptTest {

    @Test
    public void evalScript() throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();

        //每次生成一个engine实例
        ScriptEngine engine = factory.getEngineByName("groovy");

        System.out.println(engine.toString());

        assert engine != null;

        //javax.script.Bindings
        Bindings binding = engine.createBindings();
        binding.put("date", new Date());

        //如果script文本来自文件,请首先获取文件内容
        engine.eval("def getTime(){return date.getTime();}", binding);
        engine.eval("def sayHello(name,age){return 'Hello,I am ' + name + ',age' + age;}");

        Long time = (Long) ((Invocable) engine).invokeFunction("getTime", null);
        System.out.println(time);


        String message = (String) ((Invocable) engine).invokeFunction("sayHello", "zhangsan", new Integer(12));
        System.out.println(message);

    }


    @Test
    public void complexTest() throws Exception {
        String key = "classpath:Hello.groovy";
        Class<GroovyRule> hello = (Class<GroovyRule>) GroovyEngine.load(key);

        GroovyRule groovyRule = hello.newInstance();

        //上下文数据
        GroovyContext context = new GroovyContext();
        context.put("name", "hunter");


        GroovyResult result = groovyRule.run(context);

        log.info("result={}", result);
    }

    @Test
    public void run70() throws Exception {
        String key = "classpath:decision.groovy";
        Class<GroovyRule> hello = (Class<GroovyRule>) GroovyEngine.load(key);

        GroovyRule groovyRule = hello.newInstance();

        //上下文数据
        GroovyContext context = new GroovyContext();
        context.put("age", 11);


        GroovyResult result = groovyRule.run(context);

        log.info("result={}", result);
    }
}
