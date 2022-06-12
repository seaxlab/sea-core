package com.github.seaxlab.core.component.transform.convert;

import com.alibaba.fastjson.JSONObject;
import com.github.seaxlab.core.component.transform.Convert;
import com.github.seaxlab.core.component.transform.FieldRule;
import com.github.seaxlab.core.component.transform.ValueParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/24
 * @since 1.0
 */
@Slf4j
public class JsonConvert implements Convert {

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
            log.error("fail transform json={}, ex={}", obj, e);
        }
    }

    private void transform0(Object obj, List<FieldRule> rules) {
        JSONObject jsonObject = (JSONObject) obj;

        for (FieldRule rule : rules) {
            String source = rule.getSource();
            String target = rule.getTarget();

            //TODO JSONPath
            ValueParser parser = rule.getValueParser();
            Object value = jsonObject.get(source);
            if (parser != null) {
                value = rule.getValueParser().parse(value);
            }
            if (target == null || source.equals(target)) {
                jsonObject.put(source, value);
            } else {
                jsonObject.put(target, value);
                jsonObject.remove(source);
            }
        }
    }
}
