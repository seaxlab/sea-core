package com.github.seaxlab.core.component.transform.parser;

import com.github.seaxlab.core.component.transform.ValueParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/25
 * @since 1.0
 */
@Slf4j
public class FenToYuanParser implements ValueParser {

    @Override
    public Object parse(Object value) {
        String money = value.toString();

        if (StringUtils.isBlank(money) || "null".equals(money)) {
            return 0L;
        }
        final int MULTIPLIER = 100;
        return new BigDecimal(money).divide(new BigDecimal(MULTIPLIER)).setScale(2, BigDecimal.ROUND_DOWN).longValue();
    }
}
