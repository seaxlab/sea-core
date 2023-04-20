package com.github.seaxlab.core.boot.autoconfigure.redisson;

import com.github.seaxlab.core.component.lock.LockService;
import com.github.seaxlab.core.component.lock.impl.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * sea redisson config
 *
 * @author spy
 * @version 1.0 2023/4/20
 * @since 1.0
 */
@Slf4j
//@Configuration
//@ConditionalOnClass({RedissonClient.class, Redisson.class})
public class SeaRedissonConfig {

  //DOC redissonClient init bean, we cannot infer, so we cannot create this bean.
  @Bean("seaRedissonLockService")
  @ConditionalOnMissingBean
  @ConditionalOnBean(RedissonClient.class)
  public LockService seaRedissonLockService(RedissonClient redissonClient) {
    return new RedissonLockService(redissonClient);
  }
}
