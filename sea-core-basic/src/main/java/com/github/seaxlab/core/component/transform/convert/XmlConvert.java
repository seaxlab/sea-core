package com.github.seaxlab.core.component.transform.convert;

import com.github.seaxlab.core.component.transform.Convert;
import com.github.seaxlab.core.component.transform.FieldRule;
import com.github.seaxlab.core.component.transform.ValueParser;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;

import java.util.List;


/**
 * xml convert
 *
 * @author spy
 * @version 1.0 2022/2/24
 * @since 1.0
 */
@Slf4j
public class XmlConvert implements Convert {

  @Override
  public void transform(Object obj, List<FieldRule> rules) {
    if (obj == null) {
      log.warn("input param obj is null");
      return;
    }
    if (rules == null || rules.isEmpty()) {
      log.warn("input param rules is empty.");
      return;
    }
    try {
      transform0(obj, rules);
    } catch (Exception e) {
      log.error("fail transform xml={}, ex=", obj, e);
    }
  }

  private void transform0(Object obj, List<FieldRule> rules) {
    Element body = (Element) obj;

    for (FieldRule rule : rules) {
      String source = rule.getSource();
      String target = rule.getTarget();
      ValueParser parser = rule.getValueParser();
      Object value = body.elementTextTrim(source);
      if (parser != null) {
        value = rule.getValueParser().parse(value);
      }
      if (target == null || source.equals(target)) {
        Element el = body.element(source);
        if (el == null) {
          el = body.addElement(source);
        }
        el.setText(value == null ? "" : value.toString());
      } else {
        if (value == null) {
          continue;
        }
        body.addElement(target).setText(value.toString());
        body.remove(body.element(source));
      }
    }
  }
}
