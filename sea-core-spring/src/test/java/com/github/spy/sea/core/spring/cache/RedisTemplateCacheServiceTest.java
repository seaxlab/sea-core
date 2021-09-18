package com.github.spy.sea.core.spring.cache;

import com.github.spy.sea.core.spring.BaseSpringTest;
import com.github.spy.sea.core.spring.cache.impl.RedisTemplateCacheService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/18
 * @since 1.0
 */
@Slf4j
public class RedisTemplateCacheServiceTest extends BaseSpringTest {

    RedisTemplate<String, String> redisTemplate;
    RedisTemplateCacheService redisTemplateCacheService;

    @Before
    public void before() {
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("mylab");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setPassword("");
        redisStandaloneConfiguration.setDatabase(0);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.afterPropertiesSet();

        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        redisTemplateCacheService = new RedisTemplateCacheService(redisTemplate);
    }

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 10000; i++) {
            redisTemplateCacheService.set("key" + i, "" + i);
        }
    }

    @Test
    public void testScan() throws Exception {
        redisTemplateCacheService.scan("key10*", bytes -> {
            String key = new String(bytes);
            log.info("key={}", key);
        });
    }
}
