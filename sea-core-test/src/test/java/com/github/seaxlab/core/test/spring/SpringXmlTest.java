package com.github.seaxlab.core.test.spring;

import com.github.seaxlab.core.test.AbstractCoreSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/22
 * @since 1.0
 */
@Slf4j
@ContextConfiguration(value = "classpath:spring/spring.xml", initializers = SpringAppInitializer.class)
@ComponentScan("com.github.seaxlab.core.test.spring.listener")
public class SpringXmlTest extends AbstractCoreSpringTest {

  @Test
  public void test17() throws Exception {
    log.info("ctx={}", ctx);
    log.info("sea.appName in app.properties={}", getEnvValue("sea.appName"));
    log.info("a={} in spring.properties", getEnvValue("a"));
    log.info("sea.xxx={}", getEnvValue("sea.xxx"));
  }


  private String getEnvValue(String key) {
    return ctx.getEnvironment().getProperty(key);
  }
}
