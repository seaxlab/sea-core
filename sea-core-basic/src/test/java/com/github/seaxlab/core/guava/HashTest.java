package com.github.seaxlab.core.guava;

import com.github.seaxlab.core.BaseCoreTest;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/5
 * @since 1.0
 */
@Slf4j
public class HashTest extends BaseCoreTest {

  @Test
  public void md5Test() throws Exception {
  }

  @Test
  public void murmurTest() throws Exception {
    HashFunction hashFn = Hashing.murmur3_32();

    HashCode hashCode = hashFn.hashString("123", StandardCharsets.UTF_8);
    Assert.assertEquals(-1632341525, hashCode.hashCode());

    HashCode hashCode1 = Hashing.murmur3_128().hashString("123", StandardCharsets.UTF_8);
    Assert.assertEquals(224821098, hashCode1.hashCode());
  }


  @Test
  public void crc32Test() throws Exception {
    for (int i = 0; i < 100; i++) {
      int hashCode = Hashing.crc32().hashString("" + i, StandardCharsets.UTF_8).hashCode();
      log.info("hashCode={}", hashCode);
    }
  }

  @Test
  public void consistentHashTest() throws Exception {
    Map<Integer, Long> bucketMap = new HashMap<>();

    for (int i = 0; i < 100; i++) {
      int bucket = Hashing.consistentHash(i, 10);
//            log.info("i={},bucket={}", i, bucket);
      bucketMap.putIfAbsent(bucket, new Long(0));
      bucketMap.put(bucket, bucketMap.get(bucket) + 1);
    }

    log.info("bucket map={}", bucketMap);
  }
}
