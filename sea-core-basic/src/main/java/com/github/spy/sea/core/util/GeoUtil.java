package com.github.spy.sea.core.util;

import lombok.extern.slf4j.Slf4j;

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

    /**
     * 根据经纬度计算两点之间的距离 (m)
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
            return (2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                    + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))))
                    * EARTH_RADIUS;
        } finally {
            log.info("calc geo distance cost={}ms", System.currentTimeMillis() - begin);
        }
    }

    private static double radian(double d) {
        return d * Math.PI / 180.0;
    }

}
