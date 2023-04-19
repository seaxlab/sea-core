package com.github.seaxlab.core.boot.autoconfigure;

import com.github.seaxlab.core.boot.autoconfigure.config.LogCostConfig;
import com.github.seaxlab.core.boot.autoconfigure.config.LogPublicConfig;
import com.github.seaxlab.core.boot.autoconfigure.config.LogRequestConfig;
import com.github.seaxlab.core.boot.autoconfigure.config.SeaCoreConfig;
import com.github.seaxlab.core.boot.autoconfigure.schedule.SeaScheduleConfig;
import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.lock.impl.RedissonLockService;
import com.github.seaxlab.core.spring.context.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan("com.github.seaxlab.core.spring.controller")
@EnableConfigurationProperties(SeaProperties.class)
@Import({LogCostConfig.class, LogPublicConfig.class, LogRequestConfig.class, SeaCoreConfig.class, SeaScheduleConfig.class})
public class SeaCoreAutoConfigure {

  /**
   * create spring context holder
   * <p>
   * if you want to create early, you should use @dependOn("springContextHolder") such as: mq listener, it should be
   * after spring context holder init.
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

  @Bean("seaRedissonLockService")
  @ConditionalOnMissingBean
  @ConditionalOnClass(RedissonClient.class)
  @ConditionalOnBean(RedissonClient.class)
  public LockService seaRedissonLockService(RedissonClient redissonClient) {
    return new RedissonLockService(redissonClient);
  }

}
