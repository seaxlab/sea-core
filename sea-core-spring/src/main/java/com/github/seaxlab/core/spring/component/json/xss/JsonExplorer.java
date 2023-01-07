package com.github.seaxlab.core.spring.component.json.xss;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class JsonExplorer {

  public static Object explore(Object obj, FieldReplacer replacer) {
    if (isSimpleValueType(obj)) {
      return replacer.doReplace(obj);
    }

    explore(obj, replacer, new HashSet<>());
    return obj;
  }

  private static void explore(Object obj, FieldReplacer replacer, Set<Object> set) {
    if (obj == null || set.contains(obj)) {
      return;
    }
    set.add(obj);
    if (obj instanceof Map) {
      Map<String, Object> map = (Map<String, Object>) obj;
      for (Map.Entry<String, Object> entry : map.entrySet()) {
        Object value = entry.getValue();
        if (isSimpleValueType(value)) {
          Object newValue = replacer.doReplace(value);
          if (newValue != value) {
            entry.setValue(newValue);
          }
        } else {
          explore(value, replacer, set);
        }
      }
    } else if (obj instanceof List) {
      List<Object> list = (List<Object>) obj;
      for (int i = 0; i < list.size(); i++) {
        Object value = list.get(i);
        if (isSimpleValueType(value)) {
          Object newValue = replacer.doReplace(value);
          if (newValue != value) {
            list.set(i, newValue);
          }
        } else {
          explore(value, replacer, set);
        }
      }
    }

  }


  //
  private static boolean isSimpleValueType(Object obj) {

    return obj instanceof String
      || obj instanceof Number
      || obj instanceof Boolean;
  }

}
