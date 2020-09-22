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
//        redisServer = new RedisServer(6379);
//        redisServer.start();

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

    @Test
    public void setBitSimpleTest() throws Exception {
        String key = "order:f:paysucc";
        for (int i = 0; i < 10; i++) {
            long userId = i;
            boolean ret = redisManager.setBit(key, userId, true);
            log.info("ret={}", ret);
        }
    }

    @Test
    public void getBitTest() throws Exception {
        String key = "order:f:paysucc";

        for (int i = 0; i < 15; i++) {
            log.info("{}={}", i, redisManager.getBit(key, i));
        }
    }

    @Test
    public void maxSetBitTest() throws Exception {
        String key = "order:f:paysucc";
        long userId = 4294967295L - 1;
        boolean ret = redisManager.setBit(key, userId, true);
        log.info("ret={}", ret);
    }

    @Test
    public void getMaxBitTest() throws Exception {
        String key = "order:f:paysucc";
        long userId = 4294967295L - 1;
//        userId = 4294967293L;
        boolean ret = redisManager.getBit(key, userId);
        log.info("ret={}", ret);
    }


    @Test
    public void run141() throws Exception {
        // 2147483647
        // 4294967295// 2^32-1
        log.info("{}", Integer.MAX_VALUE);

        log.info("{}", RedisManager.BIT_OFFSET_MAX_VALUE);

        // 9223372036854775807
        log.info("{}", Long.MAX_VALUE);

    }

    @Test
    public void deleteTest() throws Exception {
        String key = "order:f:paysucc";

        redisManager.del(key);
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
