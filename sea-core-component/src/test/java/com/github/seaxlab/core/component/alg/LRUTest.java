package com.github.seaxlab.core.component.alg;

import com.github.seaxlab.core.component.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/26
 * @since 1.0
 */
@Slf4j
public class LRUTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    Map<Integer, Integer> map = new LRU<>(5);
    for (int i = 1; i <= 11; i++) {
      map.put(i, i);
      System.out.println("cache的容量为：" + map.size());
      if (i == 4) {
        map.get(1);
      }
    }

    System.out.println("=-=-=-=-=-=-=-map元素:");
    map.entrySet().forEach(integerIntegerEntry -> System.out.println(integerIntegerEntry.getValue()));
  }
}
