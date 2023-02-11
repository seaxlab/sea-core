package com.github.seaxlab.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;

/**
 * distance util
 *
 * @author spy
 * @version 1.0 2023/2/11
 * @since 1.0
 */
@Slf4j
public final class DistanceUtil {

  public static final String EMPTY = "-";
  // 赤道半径
  private static final double EARTH_RADIUS = 6378137;

  private DistanceUtil() {
  }


  /**
   * 根据经纬度获取两点的距离(单位米)
   */
  public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
    double radLat1 = rad(lat1);
    double radLat2 = rad(lat2);
    double a = radLat1 - radLat2;
    double b = rad(lng1) - rad(lng2);
    double s = 2 * Math.asin(Math.sqrt(
      Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
    s = s * EARTH_RADIUS;
    return s;
  }

  private static double rad(double d) {
    return d * Math.PI / 180.0;
  }

  /**
   * 根据经纬度获取两点的距离(单位米)
   */
  public static Double getDistance(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
    return getDistance(lng1.doubleValue(), lat1.doubleValue(), lng2.doubleValue(), lat2.doubleValue());
  }

  /**
   * 根据经纬度获取两点的距离(单位公里/千米)
   */
  public static double getKilometer(double lng1, double lat1, double lng2, double lat2) {
    return Math.round(getDistance(lng1, lat1, lng2, lat2)) / 1000d;
  }

  /**
   * 根据经纬度获取两点的距离(单位公里/千米)
   */
  public static double getKilometer(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
    return Math.round(getDistance(lng1.doubleValue(), lat1.doubleValue(), lng2.doubleValue(), lat2.doubleValue()))
      / 1000d;
  }

  /**
   * 根据距离获取距离文案
   *
   * @param distance
   * @return
   */
  public static String getDesc(int distance) {
    if (distance < 0) {
      return EMPTY;
    }
    if (distance < 1000) {
      return String.format("%s米", distance);
    } else {
      BigDecimal bigDecimal = NumberUtil.divide(distance, 1000, 1, RoundingMode.DOWN);
      return String.format("%s公里", bigDecimal.intValue());
    }
  }


}
