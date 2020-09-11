package com.github.spy.sea.core.spring.extension;

import com.github.spy.sea.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * SpringBootstrap
 *
 * @author Frank Zhang
 * @date 2020-06-18 7:55 PM
 */
@Slf4j
public class SpringExtensionBootstrap {

    @Autowired
    private ExtensionRegister extensionRegister;

    public void init() {
        log.info("init sea core spring extension begin.");
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        Map<String, Object> extensionBeans = applicationContext.getBeansWithAnnotation(Extension.class);

        log.info("spring extension size={}", extensionBeans.size());

        extensionBeans.values().forEach(
                extension -> extensionRegister.doRegistration((IExtensionPoint) extension)
        );
        log.info("init sea core spring extension end.");
    }
}
