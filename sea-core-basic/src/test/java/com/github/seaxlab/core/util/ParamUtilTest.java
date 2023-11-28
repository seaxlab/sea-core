package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/11/28
 * @since 1.0
 */
@Slf4j
public class ParamUtilTest extends BaseCoreTest {

  @Test
  public void test17() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("code", "123");
    params.put("name", "smith");

    log.info("{}", ParamUtil.getStr(params));

  }

}
