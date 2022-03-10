package com.github.spy.sea.core.component.transform.convert;

import com.github.spy.sea.core.component.transform.Convert;
import com.github.spy.sea.core.component.transform.FieldRule;
import com.github.spy.sea.core.component.transform.ValueParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/25
 * @since 1.0
 */
@Slf4j
public class MapConvert implements Convert {

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
            log.error("fail transform map={}, ex={}", obj, e);
        }
    }

    private void transform0(Object obj, List<FieldRule> rules) {
        Map<String, String> map = (Map) obj;
        for (FieldRule fieldRule : rules) {
            String source = fieldRule.getSource();
            String target = fieldRule.getTarget();
            String value = map.get(source);
            ValueParser valueParser = fieldRule.getValueParser();
            if (valueParser != null) {
                value = (String) valueParser.parse(value);
            }
            if (target == null || source.equals(target)) {
                map.put(source, value);
            } else {
                map.put(target, value);
                map.remove(source);
            }
        }
    }
}
