package com.github.seaxlab.core.spring.component.extension.register;

import com.github.seaxlab.core.spring.component.extension.IExtensionPoint;
import com.github.seaxlab.core.spring.component.extension.annotation.Extension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * SpringBootstrap
 *
 * @author Frank Zhang
 * @date 2020-06-18 7:55 PM
 */
@Slf4j
public class ExtensionScanner implements ApplicationContextAware, InitializingBean {

  @Autowired
  private ExtensionRegister extensionRegister;

  private ApplicationContext applicationContext;


  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    //获取所有扩展点
    Map<String, Object> extensionBeans = applicationContext.getBeansWithAnnotation(Extension.class);
    //
    extensionBeans.values().forEach(extension -> extensionRegister.doRegistration((IExtensionPoint) extension));
    log.info("init spring extension point, size={}", extensionBeans.size());
  }
}
