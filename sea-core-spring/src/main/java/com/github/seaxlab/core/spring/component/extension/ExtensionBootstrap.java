package com.github.seaxlab.core.spring.component.extension;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringBootstrap
 *
 * @author Frank Zhang
 * @date 2020-06-18 7:55 PM
 */
@Slf4j
public class ExtensionBootstrap implements ApplicationContextAware, InitializingBean {

  @Autowired
  private ExtensionRegister extensionRegister;

  private ApplicationContext applicationContext;


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.info("init sea core spring extension begin.");
    Map<String, Object> extensionBeans = applicationContext.getBeansWithAnnotation(Extension.class);

    log.info("spring extension size={}", extensionBeans.size());

    extensionBeans.values().forEach(extension -> extensionRegister.doRegistration((IExtensionPoint) extension));
    log.info("init sea core spring extension end.");
  }
}
