package com.github.seaxlab.core.example.web.config;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.lock.impl.RedissonLockService;
import com.github.seaxlab.core.spring.component.encrypt.EncryptPropertiesBeanFactoryPostProcessor;
import com.github.seaxlab.core.spring.component.tunnel.service.TunnelService;
import com.github.seaxlab.core.spring.component.tunnel.service.impl.DefaultTunnelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/24
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class BeanConfig {

  private final ApplicationContext applicationContext;
  private final TransactionTemplate transactionTemplate;

  //@Bean
  public EncryptPropertiesBeanFactoryPostProcessor encryptPropertiesBeanFactoryPostProcessor(
    ConfigurableEnvironment environment) {
    return new EncryptPropertiesBeanFactoryPostProcessor(environment);
  }

  @Bean
  public LockService seaRedissonLockService(RedissonClient redissonClient) {
    return new RedissonLockService(redissonClient);
  }

  @Bean
  public TunnelService tunnelService() {
    return new DefaultTunnelService(applicationContext, transactionTemplate);
  }
}
