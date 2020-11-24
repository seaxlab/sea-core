package com.github.spy.sea.core.dubbo;

import com.github.spy.sea.core.dubbo.common.dto.BeanConfig;
import com.github.spy.sea.core.dubbo.facade.UserFacadeService;
import com.github.spy.sea.core.dubbo.util.DubboUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    public void startProviderTest() throws Exception {
        log.info("start dubbo provider.");
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-dubbo.xml");
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

}
