package com.github.seaxlab.core.cache.redis.jedis;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.cache.CacheManager;
import com.github.seaxlab.core.util.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

/**
 * jedis cache manager
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Slf4j
public class JedisCacheManager implements CacheManager {


  private JedisPool pool;


  @Override
  public Optional<Object> get(String key) {
    try (Jedis jedis = pool.getResource()) {
      return Optional.ofNullable(jedis.get(key));
    }
  }

  @Override
  public boolean add(String key, Object object) {
    try (Jedis jedis = pool.getResource()) {
      jedis.set(key.getBytes(), SerializeUtil.serialize(object));
      return true;
    } catch (Exception e) {
      log.error("fail to add to cache.", e);
      return false;
    }
  }

  @Override
  public boolean add(String key, Object object, int expired) {
    try (Jedis jedis = pool.getResource()) {
      jedis.set(key.getBytes(), SerializeUtil.serialize(object));
      jedis.expire(key.getBytes(), expired);
      return true;
    } catch (Exception e) {
      log.error("fail to add to cache.", e);
      return false;
    }
  }

  @Override
  public boolean del(String... keys) {
    try (Jedis jedis = pool.getResource()) {
      return jedis.del(keys) > 0;
    } catch (Exception e) {
      log.error("fail to add to cache.", e);
      return false;
    }
  }

  @Override
  public boolean setJSON(String key, Object obj) {
    try (Jedis jedis = pool.getResource()) {
      jedis.set(key, JSON.toJSONString(obj));
      return true;
    } catch (Exception e) {
      log.error("fail to add to cache.", e);
      return false;
    }
  }

  @Override
  public <T> Optional<T> getJSON(String key, Class<T> clazz) {
    try (Jedis jedis = pool.getResource()) {
      byte[] value = jedis.get(key.getBytes());
      if (null == value) {
        return Optional.empty();
      }

      return Optional.of(JSON.parseObject(value, clazz));

    } catch (Exception e) {
      log.error("fail to add to cache.", e);
      return Optional.empty();
    }
  }

}
