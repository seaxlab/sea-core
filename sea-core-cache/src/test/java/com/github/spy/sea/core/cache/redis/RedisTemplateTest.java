package com.github.spy.sea.core.cache.redis;

import com.github.spy.sea.core.cache.BaseTest;
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
 * @version 1.0 2021/4/23
 * @since 1.0
 */
@Slf4j
public class RedisTemplateTest extends BaseTest {
    RedisTemplate<String, Object> redisTemplate;

    @Before
    public void before() throws Exception {
        RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration();
        cfg.setHostName("10.122.2.110");
        cfg.setPort(6379);
        cfg.setDatabase(8);
        cfg.setPassword("yuantu123");

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(cfg);
        connectionFactory.afterPropertiesSet();

        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //重点：注意序列化方式
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
    }

    @Test
    public void test17() throws Exception {

        boolean hasKey = redisTemplate.hasKey("qingdaoUat:numSourceKey:3825_1_04250804");
        log.info("hasKey={}", hasKey);
        Object obj = redisTemplate.opsForValue().get("qingdaoUat:numSourceKey:3825_1_04250804");
        log.info("obj={}", obj);
    }
}
