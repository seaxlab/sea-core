package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.model.annotation.InnerTesting;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/23
 * @since 1.0
 */
@Slf4j
@InnerTesting
public class AnnotationUtilTest extends BaseCoreTest {

    @Test
    public void getMap() throws Exception {
        Greet greet = Demo.class.getAnnotation(Greet.class);

        Map<String, Object> map = AnnotationUtil.getMap(greet);
        log.info("map={}", map);
    }

    @Test
    public void changeTest() throws Exception {
        Greet greet = Demo.class.getAnnotation(Greet.class);
        log.info("Hello there [{}]", greet.name());

        // new annotation value.
        DynamicGreetings altered = new DynamicGreetings("KungFu Panda");
        AnnotationUtil.change(Demo.class, Greet.class, altered);
        greet = Demo.class.getAnnotation(Greet.class);
        log.info("After alteration...Hello there [{}]", greet.name());
    }


    @Test
    public void testFind() throws Exception {
        // 这里不能用@Slf4j,这个注解会在编译时，生成其他代码
        InnerTesting anno = AnnotationUtil.findClassAnnotation(AnnotationUtilTest.class, InnerTesting.class);
        log.info("anno={}", anno);
    }


    @Retention(RetentionPolicy.RUNTIME)
    public @interface Greet {
        /**
         * @return - The name of the person to greet.
         */
        String name() default "";
    }

    @Greet(name = "Dragon Warrior")
    public static class Demo {
    }

    /**
     * 注意这个地方的实现，很是巧妙,实现了Greet接口
     */
    public class DynamicGreetings implements Greet {
        private String name;

        public DynamicGreetings(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return DynamicGreetings.class;
        }
    }
}
