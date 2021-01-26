package com.github.spy.sea.core.boot.autoconfigure.listener;

import com.github.spy.sea.core.common.CoreConst;
import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.intf.ApplicationInitBean;
import com.github.spy.sea.core.loader.EnhancedServiceLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * 初始化监听
 *
 * @author spy
 * @version 2019-07-23
 */
@Slf4j
public class ApplicationInitListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private AbstractApplicationContext ctx;

    @Value("${sea.env:pro}")
    private String env;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurationFactory.getInstance().putString(CoreConst.KEY_SEA_DEV_MODE, env);

        log.info("=======================================");
        log.info("=         Application Ready           =");
        log.info("=======================================");

        log.info("Power by Sea Framework. Design by SPY.");

        doStatic();

        new Thread(() -> doSystemInit()).start();
    }

    private void doStatic() {
        log.info("bean definition count = {}", ctx.getBeanDefinitionCount());
    }

    private void doSystemInit() {
        log.info("--- system init ---");
        //SPI way
        List<ApplicationInitBean> list = EnhancedServiceLoader.loadAll(ApplicationInitBean.class);

        list.stream().forEach(bean -> {
            boolean hasException = true;
            try {
                bean.init();
                hasException = false;
            } catch (Exception e) {
                log.error("fail to invoke init method", e);
            } finally {
                logInvokeMsg(bean.getClass().getName(), hasException);
            }
        });

        //  spring way.
        Map<String, ApplicationInitBean> beanMap = ctx.getBeansOfType(ApplicationInitBean.class);

        if (beanMap != null) {
            beanMap.forEach((name, bean) -> {
                boolean hasException = true;

                try {
                    bean.init();
                    hasException = false;
                } catch (Exception e) {
                    log.error("fail to invoke bean init method", e);
                } finally {
                    logInvokeMsg(bean.getClass().getName(), hasException);
                }
            });
        }
    }


    private void logInvokeMsg(String className, boolean hasException) {
        if (hasException) {
            log.info("fail to invoke {} init method.", className);
        } else {
            log.info("invoke {} init method successfully.", className);
        }
    }
}
