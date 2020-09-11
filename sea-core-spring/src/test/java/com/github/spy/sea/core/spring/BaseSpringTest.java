package com.github.spy.sea.core.spring;

import com.github.spy.sea.core.spring.extension.SpringExtensionBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/9/11
 * @since 1.0
 */
@Slf4j
public class BaseSpringTest {

    @Test
    public void run17() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestSpringConfig.class);
        SpringExtensionBootstrap bootstrap = (SpringExtensionBootstrap) context.getBean("bootstrap");
        System.out.println(bootstrap);
    }

}
