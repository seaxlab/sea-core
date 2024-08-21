package com.github.seaxlab.core.cache.redis;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.cache.redis.jedis.JedisManager;
import com.github.seaxlab.core.util.EqualUtil;
import com.github.seaxlab.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.github.seaxlab.core.test.util.TestUtil.runInMultiThread;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-18
 * @since 1.0
 */
@Slf4j
public class RedisManagerTest extends BaseCoreTest {

  JedisManager redisManager;

  @Before
  public void before() throws Exception {
    redisManager = new JedisManager();
    redisManager.setHost("mylab-redis");
    redisManager.setPort(6379);
    redisManager.setDatabase(1);
    redisManager.init();
  }

  @Test
  public void testInfo() throws Exception {
    Map<String, String> map = redisManager.info("stats");
    log.info("info={}", map);
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

    log.info("{}", JedisManager.BIT_OFFSET_MAX_VALUE);

    // 9223372036854775807
    log.info("{}", Long.MAX_VALUE);

  }

  @Test
  public void deleteTest() throws Exception {
    String key = "order:f:paysucc";

    redisManager.del(key);
  }

  @Test
  public void scanTest() throws Exception {
    String cursor = ScanParams.SCAN_POINTER_START;
    String key = "payBaseOne:*";
    ScanParams scanParams = new ScanParams();
    scanParams.match(key);// 匹配以 payBaseOne:* 为前缀的 key
    scanParams.count(10);
    while (true) {
      //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
      ScanResult<String> scanResult = redisManager.scan(cursor, scanParams);
      cursor = scanResult.getCursor();// 返回0 说明遍历完成
      List<String> list = scanResult.getResult();

      stopwatch.reset();
      stopwatch.start();

      for (int m = 0; m < list.size(); m++) {
        String realKey = list.get(m);
        log.info("real key={}", realKey);
      }
      log.info("cursor={},list size={},cost={}ms", cursor, list.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));

      if (EqualUtil.isEq(cursor, ScanParams.SCAN_POINTER_START)) {
        log.info("scan end.");
        break;
      }
    }
  }

  @Test
  public void testSscan() {
    Jedis jedis = redisManager.getJedis();
    // 游标初始值为0
    String cursor = ScanParams.SCAN_POINTER_START;
    ScanParams scanParams = new ScanParams();
    scanParams.count(1000);
    String key = "PLFX-ZZSFP";
    while (true) {
      //使用sscan命令获取数据，使用cursor游标记录位置，下次循环使用
      ScanResult<String> sscanResult = jedis.sscan(key, cursor, scanParams);
      cursor = sscanResult.getCursor();// 返回0 说明遍历完成
      List<String> list = sscanResult.getResult();
      stopwatch.reset();
      stopwatch.start();

      for (int m = 0; m < list.size(); m++) {
        String realKey = list.get(m);
        log.info("real key={}", realKey);
      }
      log.info("cursor={},list size={},cost={}ms", cursor, list.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));

      if (EqualUtil.isEq(cursor, ScanParams.SCAN_POINTER_START)) {
        log.info("scan end.");
        break;
      }
    }
  }

  //HSCAN, ZSCAN

  @Test
  public void testMultiLock() throws Exception {
    Set<String> keys = new HashSet<>();
    keys.add("test:lock:1");
    keys.add("test:lock:2");
    keys.add("test:lock:3");


    Runnable runnable = () -> {
      boolean flag = redisManager.tryLock(keys, 2, TimeUnit.MINUTES);
      log.info("flag={}", flag);
      if (flag) {
        try {
          log.info("do some biz....");
        } finally {
          redisManager.unLock(keys);
        }
      }

    };
    runInMultiThread(runnable, 8);

  }


  @Test
  public void testBatchIncr() throws Exception {
    List<String> keys = ListUtil.newArrayList("test-batch-incr1", "test-batch-incr2");

    for (int i = 0; i < 10; i++) {
      boolean ret = redisManager.batchIncr(keys);
      log.info("batch no={},result={}", i, ret);
    }

  }

  @Test
  public void testBatchIncrLimit() throws Exception {
    List<String> keys = ListUtil.newArrayList("test-batch-incr1", "test-batch-incr2");
    List<Integer> limits = ListUtil.newArrayList(10, 30);
    for (int i = 0; i < 14; i++) {
      boolean ret = redisManager.batchIncrLimit(keys, limits);
      log.info("batch no={},result={}", i, ret);
    }
  }

  @Test
  public void testBatchDecr() throws Exception {
    List<String> keys = ListUtil.newArrayList("test-batch-incr1", "test-batch-incr2");
    for (int i = 0; i < 5; i++) {
      boolean ret = redisManager.batchDecr(keys);
      log.info("batch no={},result={}", i, ret);
    }
  }

  @Test
  public void testBatchDecrLimit() throws Exception {
    List<String> keys = ListUtil.newArrayList("test-batch-incr1", "test-batch-incr2");
    List<Integer> limits = ListUtil.newArrayList(10, 20);
    for (int i = 0; i < 5; i++) {
      boolean ret = redisManager.batchDecrLimit(keys, limits);
      log.info("batch no={},result={}", i, ret);
    }
  }


  @Test
  public void testNextId() throws Exception {
    for (int i = 0; i < 10; i++) {
      log.info("id={}", redisManager.nextId("abc"));
    }
  }

  @Test
  public void testNextIds() throws Exception {
    for (int i = 0; i < 10; i++) {
      log.info("id={}", redisManager.nextIds("a2", 5));
    }
  }

  @After
  public void after() throws Exception {

    TimeUnit.SECONDS.sleep(2);
    if (redisManager != null) {
      redisManager.destroy();
    }

  }
}
