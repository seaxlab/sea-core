package com.github.seaxlab.core.cache.redis.redisson;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RBuckets;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

import static com.github.seaxlab.core.test.util.TestUtil.runInMultiThread;
import static com.github.seaxlab.core.test.util.TestUtil.sleepSecond;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/19
 * @since 1.0
 */
@Slf4j
public class RedissonTest extends BaseCoreTest {

  RedissonClient client;

  @Before
  public void before() throws Exception {

    Config config = new Config();
//    config.useSingleServer().setAddress("redis://redis:6379");
    config.useSingleServer().setAddress("redis://mylab-redis:31919");

    client = Redisson.create(config);
  }

  @Test
  public void testReadWriteLock() throws Exception {
    runInMultiThread(() -> {
      ReadWriteLock innerLock = client.getReadWriteLock("lock1");
      log.info("inner lock begin ");
      innerLock.writeLock().lock();
      log.info("inner lock biz ");
      sleepSecond(10);
      innerLock.writeLock().unlock();
      log.info("inner lock end ");

    });

  }

  @Test
  public void testReadWriteLock2() throws Exception {
    String key = "lock1";
    RLock lock = client.getLock(key);
    boolean flag = lock.tryLock();
    if (!flag) {
      return;
    }
    try {
      // 此模式下会存在读，无法释放
      for (int i = 0; i < 3; i++) {
        ReadWriteLock innerLock = client.getReadWriteLock(key);
        log.info("inner lock begin ");
        innerLock.writeLock().lock(); // 第二次加锁时会一直阻塞
        log.info("inner lock biz ");
        sleepSecond(1);
        innerLock.writeLock().unlock();
        log.info("inner lock end ");
      }
    } finally {
      lock.unlock();
    }

  }

  @Test
  public void testTry() throws Exception {
    RLock lock = client.getLock("lock1");
    // key会自动续期（leaseTime=-1会自动续期）
    lock.tryLock();
    sleepSecond(50);
    lock.unlock();
  }

  @Test
  public void testLock() throws Exception {
    RLock lock = client.getLock("lock1");
    boolean flag = lock.tryLock(0, TimeUnit.SECONDS);

    if (!flag) {
      log.warn("fail to get all lock");
      return;
    }

    try {
      log.info("do biz");
      sleepSecond(3);
    } finally {
      lock.unlock();
    }
    log.info("biz end.");
  }

  @Test
  public void testRelease() throws Exception {
    String lockKey = "lock1";
    RLock lock = client.getLock(lockKey);
    boolean flag = lock.tryLock();

    if (!flag) {
      log.warn("fail to get all lock");
      return;
    }

    RLock lock2 = client.getLock(lockKey);
    boolean flag2 = lock2.tryLock();

    try {
      log.info("do biz");
      sleepSecond(3);

      lock2.unlock();
    } finally {
      lock.unlock();
    }
    log.info("biz end.");
  }

  @Test
  public void testInMultiThread() throws Exception {

    runInMultiThread(() -> {
      RLock lock = client.getLock("lock1");
      boolean flag = false;
      try {
        flag = lock.tryLock(0, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        log.error("fail to interrupted exception", e);
      }

      if (!flag) {
        log.warn("fail to get all lock");
        return;
      }

      try {
        log.info("do biz");
        sleepSecond(3);
      } finally {
        lock.unlock();
      }
      log.info("biz end.");
    });
    sleepSecond(10);
  }


  @Test
  public void testMultiLock() throws Exception {
    RLock lock1 = client.getLock("lock1");
    RLock lock2 = client.getLock("lock2");
    RLock lock3 = client.getLock("lock3");

    RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);

    boolean flag = lock.tryLock();

    if (!flag) {
      log.warn("fail to get all lock");
      return;
    }

    try {
      log.info("do biz");
      sleepSecond(3);
    } finally {
      lock.unlock();
    }
    log.info("biz end.");
  }


  @Test
  public void testMultiLockInNested() throws Exception {
    RLock lock1 = client.getLock("lock1");
    RLock lock2 = client.getLock("lock2");
    RLock lock3 = client.getLock("lock3");

    RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);

    boolean flag = lock.tryLock();

    if (!flag) {
      log.warn("fail to get all lock");
      return;
    }

    try {
      log.info("do biz");
      sleepSecond(3);
      innerLock();

    } finally {
      log.info("release lock");
      lock.unlock();
    }
    log.info("biz end.");
  }


  private void innerLock() {
    log.info("inner lock");
    RLock lock1 = client.getLock("lock1");
    RLock lock2 = client.getLock("lock2");
    RLock lock3 = client.getLock("lock3");
    RedissonMultiLock innerLock = new RedissonMultiLock(lock1, lock2, lock3);
    boolean innerFlag = innerLock.tryLock();
    if (!innerFlag) {
      log.warn("fail to get all inner lock");
      throw new RuntimeException("no inner lock");
    }
    try {
      log.info("do inner biz");
      sleepSecond(2);
    } finally {
      log.info("release inner lock");
      innerLock.unlock();
    }
  }


  @Test
  public void testBucketsPut() throws Exception {
    RBuckets buckets = client.getBuckets();

    Map<String, String> data = new HashMap<>();
    data.put("test:1", "1");
    data.put("test:2", "2");
    buckets.trySet(data);
  }

  @Test
  public void testBuckets() throws Exception {
    RBuckets buckets = client.getBuckets();

    Map<String, String> map = buckets.get("test:1", "test:2", "test:3");
    log.info("map={}", map);

  }

}
