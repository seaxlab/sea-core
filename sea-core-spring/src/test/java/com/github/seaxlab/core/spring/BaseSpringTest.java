package com.github.seaxlab.core.spring;

import com.github.seaxlab.core.spring.extension.ExtensionBootstrap;
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
    ExtensionBootstrap bootstrap = (ExtensionBootstrap) context.getBean("bootstrap");
    System.out.println(bootstrap);
  }

}
