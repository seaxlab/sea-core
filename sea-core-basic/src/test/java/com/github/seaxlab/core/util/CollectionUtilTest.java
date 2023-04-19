package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/19
 * @since 1.0
 */
@Slf4j
public class CollectionUtilTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    Collection<String> all = new ArrayList<>();
    all.add("1");
    all.add("2");
    all.add("3");
    log.info("{}", CollectionUtil.toString(all));

    Collection<Integer> all2 = new ArrayList<>();
    all2.add(1);
    all2.add(2);
    all2.add(3);
    log.info("{}", CollectionUtil.toString(all2));
  }
}
