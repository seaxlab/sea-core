package com.github.spy.sea.core.spring.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.spy.sea.core.spring.BaseSpringTest;
import com.github.spy.sea.core.spring.cache.impl.RedisTemplateCacheService;
import com.github.spy.sea.core.spring.model.User;
import com.github.spy.sea.core.util.RandomUtil;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

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
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // output detailed class type, if no need, you should comment it.
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

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
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        redisTemplateCacheService = new RedisTemplateCacheService(redisTemplate);
    }

    @Test
    public void testAdd() throws Exception {
        for (int i = 0; i < 20; i++) {
            redisTemplateCacheService.set("key:limit:a:" + i, "" + i);
        }
    }

    @Test
    public void testScan() throws Exception {
        log.info("begin");
        redisTemplateCacheService.scan("key:limit:*", 100, bytes -> {
            String key = new String(bytes);
            log.info("key={}", key);
            redisTemplateCacheService.delete(key);
        });

        log.info("end...");
    }

    @Test
    public void testDeleteByWildcard() throws Exception {
        redisTemplateCacheService.deleteByWildcard("key:limit:*");
    }


    @Test
    public void testQueryMapList() throws Exception {

        String key = "users";
        // clean first
        redisTemplateCacheService.delete(key);

        List<String> mapKeys = ImmutableList.of("field1", "field2");
        List<User> users = redisTemplateCacheService.queryMapList(key, mapKeys, (userCodes) -> {
            List<User> dbUsers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                User user = new User();
                user.setCode("code" + RandomUtil.numeric(6));
                dbUsers.add(user);
            }

            return dbUsers;
        });
        log.info("users={}", users);
    }

    @Test
    public void testQueryMapListOnly() throws Exception {
        String key = "users";
        List<String> mapKeys = ImmutableList.of("code215815", "code700073");
        List<User> users = redisTemplateCacheService.queryMapList(key, mapKeys);
        log.info("users={}", users);
    }
}
