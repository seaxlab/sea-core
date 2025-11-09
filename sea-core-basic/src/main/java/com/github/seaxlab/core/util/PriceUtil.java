package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.PriceUnitEnum;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 价格工具类
 *
 * @author spy
 * @version 1.0 2025/11/9
 * @since 1.0
 */
@Slf4j
public final class PriceUtil {

  private PriceUtil() {

  }

  // 1万
  private static final BigDecimal TEN_THOUSAND = new BigDecimal(10000);

  /**
   * xx元
   *
   * @param value 价格
   * @return 转换后的价格
   */
  public static String toString(BigDecimal value) {
    return toString(value, 2, PriceUnitEnum.YUAN_AND_UNIT);
  }

  /**
   * 价格字符串
   *
   * @param value     价格
   * @param scale     精度
   * @param priceUnit 价格单位格式
   * @return 转换后的价格
   */
  public static String toString(BigDecimal value, Integer scale, PriceUnitEnum priceUnit) {
    if (value == null) {
      log.warn("value is empty, so end.");
      return null;
    }
    priceUnit = priceUnit == null ? PriceUnitEnum.YUAN : priceUnit;
    //
    switch (priceUnit) {
      case YUAN:
        return toDisplay(value.setScale(scale, RoundingMode.HALF_UP));
      case YUAN_AND_UNIT:
        return toDisplay(value.setScale(scale, RoundingMode.HALF_UP)) + "元";
      case WAN:
        //N（万）
        return toDisplay(value.divide(TEN_THOUSAND, scale, RoundingMode.HALF_UP));
      case WAN_AND_UNIT:
        //N万元
        return toDisplay(value.divide(TEN_THOUSAND, scale, RoundingMode.HALF_UP)) + "万元";
      case YUAN_OR_WAN_UNIT:
        // 不足1万直接展示，超过1万则展示万元
        if (value.compareTo(TEN_THOUSAND) < 0) {
          return toDisplay(value.setScale(scale, RoundingMode.HALF_UP)) + "元";
        } else {
          return toDisplay(value.divide(TEN_THOUSAND, scale, RoundingMode.HALF_UP)) + "万元";
        }
      default:
        log.error("unhandled price unit,{}", priceUnit);
        break;
    }

    return null;
  }

  private static String toDisplay(BigDecimal value) {
    return value.stripTrailingZeros().toPlainString();
  }

}
