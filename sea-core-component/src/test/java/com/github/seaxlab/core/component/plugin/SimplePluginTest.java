package com.github.seaxlab.core.component.plugin;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-14
 * @since 1.0
 */
@Slf4j
public class SimplePluginTest extends BaseCoreTest {

  @Test
  public void mapPluginShouldInterceptGet() {
    Map map = new HashMap<>();
    Map newMap = (Map) new AlwaysMapPlugin().plugin(map);
    assertEquals("Always", newMap.get("Anything"));
  }

  @Test
  public void shouldNotInterceptToString() {
    Map map = new HashMap<>();
    Map newMap = (Map) new AlwaysMapPlugin().plugin(map);
    assertNotEquals("Always", newMap.toString());
  }

  @Intercepts({@Signature(type = Map.class, method = "get", args = {Object.class})})
  public static class AlwaysMapPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) {
      return "Always";
    }

  }
}
