package com.github.seaxlab.core.component.transform;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.component.BaseCoreTest;
import com.github.seaxlab.core.component.transform.enums.Mode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/28
 * @since 1.0
 */
@Slf4j
public class TransformTest extends BaseCoreTest {

  @Test
  public void testJSON() throws Exception {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("code", "1000");
    jsonObj.put("name", "abc");

    Transform.execute(jsonObj, Lists.newArrayList(
      FieldRule.create("code", "code1"),
      FieldRule.create("code1", "code1", new CustomValueParser())
    ), Mode.JSON);

    log.info("json={}", jsonObj);
  }


  private class CustomValueParser implements ValueParser {

    @Override
    public Object parse(Object value) {
      return value + "__00";
    }
  }

}
