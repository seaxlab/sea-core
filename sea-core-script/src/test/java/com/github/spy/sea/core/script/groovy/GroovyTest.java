package com.github.spy.sea.core.script.groovy;

import com.github.spy.sea.core.script.AbstractScriptTest;
import com.github.spy.sea.core.util.ListUtil;
import com.google.common.base.Stopwatch;
import groovy.lang.GroovyShell;
import groovy.lang.IntRange;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
    public void testShell() throws Exception {
        Object ret;

        GroovyShell groovyShell = new GroovyShell();
        ret = groovyShell.evaluate("[1..12]");
        log.info("ret={}", ret);

        Script parse = groovyShell.parse("(1..12).collect {t -> t.toString().padLeft(2,'0')}");
        ret = parse.run();
        log.info("ret={}", ret);

        IntRange integers = new IntRange(1, 12);
        Iterator<Integer> toInt = integers.iterator();
        log.info("ret={}", ListUtil.toList(toInt));
    }

    @Test
    public void testEvalScript() throws Exception {
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
    public void testComplex() throws Exception {
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
    public void testComplex2() throws Exception {
        String key = "classpath:decision.groovy";
        Class<GroovyRule> hello = (Class<GroovyRule>) GroovyEngine.load(key);

        GroovyRule groovyRule = hello.newInstance();

        //上下文数据
        GroovyContext context = new GroovyContext();
        context.put("age", 11);

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 10; i++) {
            GroovyResult result = groovyRule.run(context);
            log.info("result={}", result);
            log.info("cost={}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.reset();
        }

    }
}
