package com.github.spy.sea.core.example.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/18
 * @since 1.0
 */
@Slf4j
public class MyMapContainerInitializer implements MyContainerInitializer {
    @Override
    public void onStartup(ServletContext context) {
        log.info("map---");
    }
}
