package com.github.seaxlab.core.component.transform.parser;

import com.github.seaxlab.core.component.transform.ValueParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * yuan to fen parser
 *
 * @author spy
 * @version 1.0 2022/2/25
 * @since 1.0
 */
@Slf4j
public class YuanToFenParser implements ValueParser {

  @Override
  public Object parse(Object value) {
    String money = value.toString();

    if (StringUtils.isBlank(money) || "null".equals(value)) {
      return 0l;
    }

    BigDecimal dec = new BigDecimal(money);
    BigDecimal result = dec.multiply(new BigDecimal(100));
    return result.longValue();
  }
}
