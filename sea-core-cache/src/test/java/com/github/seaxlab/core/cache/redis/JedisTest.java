package com.github.seaxlab.core.cache.redis;

import com.github.seaxlab.core.cache.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/23
 * @since 1.0
 */
@Slf4j
public class JedisTest extends BaseTest {

    private JedisPool pool;

    @Before
    public void before() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(100);
        config.setMaxTotal(200);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);


        String host = "10.122.2.110";
        int port = 6379;
        String password = "yuantu123";
        int database = 8;

        pool = new JedisPool(config, host, port, 30_000, password, database);
    }

    @Test
    public void test39() throws Exception {

        Jedis jedis = pool.getResource();
        boolean has = jedis.exists("qingdaoUat:numSourceKey:3825_1_04250804");

        jedis.pipelined();
        log.info("has={}", has);
    }


}
