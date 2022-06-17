package com.github.seaxlab.core.spring.extension;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * SpringBootstrap
 *
 * @author Frank Zhang
 * @date 2020-06-18 7:55 PM
 */
@Slf4j
@Component("seaCoreExtensionBootstrap")
public class ExtensionBootstrap implements ApplicationContextAware {

    @Autowired
    private ExtensionRegister extensionRegister;

    private ApplicationContext applicationContext;


    @PostConstruct
    public void init() {
        log.info("init sea core spring extension begin.");
//        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        Map<String, Object> extensionBeans = applicationContext.getBeansWithAnnotation(Extension.class);

        log.info("spring extension size={}", extensionBeans.size());

        extensionBeans.values().forEach(
                extension -> extensionRegister.doRegistration((IExtensionPoint) extension)
        );
        log.info("init sea core spring extension end.");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
