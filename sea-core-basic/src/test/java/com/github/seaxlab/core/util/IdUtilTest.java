package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/23
 * @since 1.0
 */
@Slf4j
public class IdUtilTest extends BaseCoreTest {


  @Test
  public void run18() throws Exception {
    for (int i = 0; i < 100; i++) {
      log.info("{}={}", i, IdUtil.getSimpleId());
    }
  }

  /**
   * 判断是否有重复
   */
  @Test
  public void testSimpleIdDuplicated() {
    Set<String> ids = new HashSet<>();

    for (int i = 0; i < 1000_000; i++) {
      String id = IdUtil.getSimpleId();
      if (ids.contains(id)) {
        log.warn("id={} is duplicated.", id);
      } else {
        ids.add(id);
      }
    }
  }


  @Test
  public void testYY() throws Exception {
    log.info("{}", IdUtil.getYYMMDD());
    log.info("{}", IdUtil.getYYMMDDHHMMSS());
    log.info("{}", IdUtil.getYYMMDDHHMMSSSSS());
  }

}
