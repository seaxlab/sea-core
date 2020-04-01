package com.github.spy.sea.core.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPubSub;
import redis.embedded.RedisServer;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

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


    @Test
    public void pubSubTest() throws Exception {
        JedisPubSub pubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                log.info("msg={}", message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                log.info("subscribe...");
            }
        };
        JedisPubSub pubSub2 = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                log.info("msg={}", message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                log.info("subscribe...");
            }
        };
        new Thread(() -> {
            redisManager.subscribe(pubSub, "my-channel");
        }).start();

        new Thread(() -> {
            redisManager.subscribe(pubSub2, "my-channel");
        }).start();

        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 10; i++) {
            log.info("publish message");
            redisManager.publish("my-channel", "" + i);
        }

        TimeUnit.SECONDS.sleep(10);
        pubSub.unsubscribe();
        pubSub2.unsubscribe();
    }


    @After
    public void after() throws Exception {

        TimeUnit.SECONDS.sleep(2);
        if (redisServer != null) {
            redisServer.stop();
        }

        if (redisManager != null) {

            redisManager.destroy();
        }

    }
}
