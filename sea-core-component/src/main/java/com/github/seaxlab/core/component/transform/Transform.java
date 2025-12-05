package com.github.seaxlab.core.component.transform;

import com.github.seaxlab.core.component.transform.convert.JsonConvert;
import com.github.seaxlab.core.component.transform.convert.MapConvert;
import com.github.seaxlab.core.component.transform.convert.XmlConvert;
import com.github.seaxlab.core.component.transform.enums.Mode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * transform
 *
 * @author spy
 * @version 1.0 2022/3/2
 * @since 1.0
 */
@Slf4j
public class Transform {

  private static final Convert XML_CONVERT = new XmlConvert();
  private static final Convert JSON_CONVERT = new JsonConvert();
  private static final Convert MAP_CONVERT = new MapConvert();

  public static void execute(Object obj, List<FieldRule> rules, Mode mode) {
    switch (mode) {
      case XML:
        XML_CONVERT.transform(obj, rules);
        break;
      case JSON:
        JSON_CONVERT.transform(obj, rules);
        break;
      case MAP:
        MAP_CONVERT.transform(obj, rules);
        break;
      case CUSTOM:
        //TODO
        break;
      default:
        log.warn("unhandled mode={}", mode);
        break;
    }
  }


}
