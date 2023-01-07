package com.github.seaxlab.core.component.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.component.json.model.A;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/11/20
 * @since 1.0
 */
@Slf4j
public class FastjsonTest extends BaseCoreTest {


  @Test
  public void testSerialize() throws Exception {
    A a = new A();
    a.setId(1L);
    a.setName("tt");
    a.setPassword("123456");
    log.info("a={}", JSON.toJSONString(a));
  }


}
