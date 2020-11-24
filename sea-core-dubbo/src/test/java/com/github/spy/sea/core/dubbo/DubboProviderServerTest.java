package com.github.spy.sea.core.dubbo;

import com.github.spy.sea.core.dubbo.common.dto.BeanConfig;
import com.github.spy.sea.core.dubbo.facade.ProductFacadeService;
import com.github.spy.sea.core.dubbo.facade.UserFacadeService;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import com.github.spy.sea.core.model.BaseResult;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/24
 * @since 1.0
 */
@Slf4j
public class DubboProviderServerTest extends BaseDubboTest {

    @Test
    public void dubboProviderTest() throws Exception {
        log.info("start dubbo provider.");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
        ctx.start();
        System.in.read();
    }

    @Test
    public void dubboProvider2Test() throws Exception {
        log.info("start dubbo provider.");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-dubbo2.xml");
        ctx.start();
        System.in.read();
    }

    @Test
    public void dubboBeanTest() throws Exception {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setUrl("10.10.11.195:20880");
        beanConfig.setVersion("1.0.0");

        UserFacadeService userFacadeService = DubboUtil.getBean(beanConfig, UserFacadeService.class);
        for (int i = 0; i < 10; i++) {
            log.info("user name={}", userFacadeService.getName("abc"));
        }

//        TimeUnit.MINUTES.sleep(1);
    }

    @Test
    public void dubboBean2Test() throws Exception {
        // 20000端口是nginx 负载后的端口
        // nginx 4层代理dubbo 20880,20881
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1; i++) {
            BeanConfig beanConfig = new BeanConfig();
            beanConfig.setUrl("10.10.11.195:20000");
            beanConfig.setVersion("1.0.0");

            ProductFacadeService productFacadeService = DubboUtil.getBean(beanConfig, ProductFacadeService.class);
            log.info("user name={}", productFacadeService.get(Long.valueOf(i)));
            TimeUnit.MILLISECONDS.sleep(200);
        }
        log.info("cost={}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void dubboBean23Test() throws Exception {
        // 20000端口是nginx 负载后的端口
        // nginx 4层代理dubbo 20880,20881
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1; i++) {
            BeanConfig beanConfig = new BeanConfig();
            beanConfig.setUrl("10.10.11.195:20000");
            beanConfig.setVersion("1.0.0");

            UserFacadeService userFacadeService = DubboUtil.getBean(beanConfig, UserFacadeService.class);
            log.info("user name={}", userFacadeService.getName("abc"));
            TimeUnit.MILLISECONDS.sleep(200);
        }
        log.info("cost={}", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Test
    public void dubbo2Test() throws Exception {
        for (int i = 0; i < 1000; i++) {
            BaseResult ret = DubboUtil.invoke("zookeeper://10.122.2.203:2181",
                    "com.github.spy.sea.core.dubbo.facade.UserFacadeService",
                    "getName", "1.0.0", "abc");
            log.info("user name={}", ret.getData());
        }

        TimeUnit.MINUTES.sleep(1);
    }

}
