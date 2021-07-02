package com.github.spy.sea.core.boot.autoconfigure;

import com.github.spy.sea.core.boot.autoconfigure.config.SeaCoreConfig;
import com.github.spy.sea.core.boot.autoconfigure.schedule.SeaScheduleConfig;
import com.github.spy.sea.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * SeaCore 自动注册
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SeaProperties.class)
@Import({SeaCoreConfig.class, SeaScheduleConfig.class})
public class SeaCoreAutoConfigure {

    /**
     * create spring context holder
     * <p>
     * if you want to create early, you should use @dependOn("springContextHolder")
     * such as: mq listener, it should be after spring context holder init.
     * </p>
     * 重点：请勿更改方法名，这是唯一bean id
     *
     * @return spring context holder bean
     */
    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    // a good choice.
//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        System.out.println("hello world, I have just started up");
//    }

}
