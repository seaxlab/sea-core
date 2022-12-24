package com.github.seaxlab.core.spring.component.json.xss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xss processor
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class XSSProcessor {

  private static final XSSProcessor processor = new XSSProcessor();
  private final List<MatchRule> rules = new ArrayList();

  private XSSProcessor() {
    try {
      //todo
      //FileUtil.readFormClasspath("xss-rules.json")
      JSONArray array = JSON.parseArray("[]");
      for (int i = 0; i < array.size(); i++) {
        JSONObject obj = array.getJSONObject(i);
        String match = obj.getString("match");
        String replace = obj.getString("replace");
        rules.add(new MatchRule(match, replace));
      }

    } catch (Exception e) {
      log.error("cannot load xss rules file", e);
    }
  }

  public static XSSProcessor getProcessor() {
    return processor;
  }

  public Object process(Object value) {
    if (value instanceof String) {
      for (MatchRule rule : rules) {
        value = rule.process((String) value);
      }
    }
    return value;
  }

  //
  private static class MatchRule {
    private final Pattern pattern;
    private final String replacement;

    public MatchRule(String match, String replacement) {
      this.pattern = Pattern.compile(match, Pattern.CASE_INSENSITIVE);
      this.replacement = replacement;
    }

    public String process(String value) {
      Matcher matcher = pattern.matcher(value);
      StringBuffer buf = null;

      //节省内存，未发现直接返回源对象
      while (matcher.find()) {
        if (buf == null) {
          buf = new StringBuffer(value.length() + 5);
        }
        matcher.appendReplacement(buf, replacement);
        log.warn("Found injected script: {}", matcher.group());
      }
      return buf == null ? value : matcher.appendTail(buf).toString();
    }

  }
}
