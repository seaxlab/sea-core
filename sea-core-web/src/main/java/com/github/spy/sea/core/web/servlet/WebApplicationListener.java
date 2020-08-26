package com.github.spy.sea.core.web.servlet;

import com.github.spy.sea.core.common.CoreConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/26
 * @since 1.0
 */
@Slf4j
public class WebApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        CoreConst.WEB_ROOT = sce.getServletContext().getRealPath(".");
        log.info("webRoot={}", CoreConst.WEB_ROOT);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
