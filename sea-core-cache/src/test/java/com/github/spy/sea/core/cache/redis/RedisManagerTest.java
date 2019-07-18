package com.github.spy.sea.core.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.util.Optional;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
@Slf4j
public class RedisManagerTest {

    RedisServer redisServer;

    RedisManager redisManager;

    @Before
    public void before() throws Exception {
        redisServer = new RedisServer(6379);
        redisServer.start();

        redisManager = new RedisManager();

        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        redisManager.setDatabase(0);


        redisManager.init();
    }


    @Test
    public void simpleTest() throws Exception {


        redisManager.set("11", "abc");

        String value = (String) redisManager.get("11");

        log.info("value={}", value);

    }

    @Test
    public void jsonTest() throws Exception {

        String key = "my-json";
        User user = User.builder().id(1L).name("abc").build();

        redisManager.setJSON(key, user);

        Optional<User> optionalUser = redisManager.getJSON(key, User.class);


        log.info("my user={}", optionalUser);
    }


    @After
    public void after() {

        if (redisServer != null) {
            redisServer.stop();
        }

        if (redisManager != null) {

            redisManager.destroy();
        }

    }
}
