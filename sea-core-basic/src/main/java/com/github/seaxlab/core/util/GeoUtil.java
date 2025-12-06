package com.github.seaxlab.core.util;

import com.github.seaxlab.core.enums.RegExpEnum;
import lombok.extern.slf4j.Slf4j;

import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * 位置工具
 *
 * @author spy
 * @version 1.0 2021/3/3
 * @since 1.0
 */
@Slf4j
public final class GeoUtil {

  /**
   * 地球的半径 (m)
   */
  public static final double EARTH_RADIUS = 6378137;

  //如把经纬度 (longitude，latitude) (205.395583333332，57.9323888888888)
  //转换据成坐标(Degrees,minutes,seconds)(205°23’44.1″，57°55’56.6″)。


  /**
   * 校验经纬度是否合法
   * <p>
   * 经度： -180.0～+180.0（整数部分为0～180，必须输入1到8位小数）<br/>
   * 纬度： -90.0～+90.0（整数部分为0～90，必须输入1到8位小数）
   * </p>
   *
   * @param longitude
   * @param latitude
   * @return
   */
  public static boolean isValid(String longitude, String latitude) {
    boolean longitudeMatch = Pattern.matches(RegExpEnum.LONGITUDE.getExpression(), longitude);
    boolean latitudeMatch = Pattern.matches(RegExpEnum.LATITUDE.getExpression(), latitude);
    return longitudeMatch && latitudeMatch;
  }


  /**
   * 根据经纬度计算两点之间的距离 (单位：米)
   *
   * @param lng1 位置 1 的经度
   * @param lat1 位置 1 的纬度
   * @param lng2 位置 2 的经度
   * @param lat2 位置 2 的纬度
   * @return 返回距离
   */
  public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
    long begin = System.currentTimeMillis();
    try {
      double radLat1 = radian(lat1);
      double radLat2 = radian(lat2);
      double a = radLat1 - radLat2;
      double b = radian(lng1) - radian(lng2);
      return (2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)))) * EARTH_RADIUS;

    } finally {
      log.info("calc geo distance cost={}ms", System.currentTimeMillis() - begin);
    }
  }

  /**
   * 获取距离文案
   *
   * @param distance
   * @return
   */
  public static String getDistanceDesc(int distance) {
    return getDistanceDesc(distance, 1);
  }

  /**
   * 获取距离文案
   *
   * @param distance
   * @param scale
   * @return
   */
  public static String getDistanceDesc(int distance, int scale) {
    if (distance < 0) {
      log.warn("distance[{}] is negative", distance);
      return "-";
    }
    if (distance < 1000) {
      return distance + "米";
    }
    return NumberUtil.divide(distance, 1000, scale, RoundingMode.DOWN) + "公里";
  }


  // ---------private
  private static double radian(double d) {
    return d * Math.PI / 180.0;
  }
}
