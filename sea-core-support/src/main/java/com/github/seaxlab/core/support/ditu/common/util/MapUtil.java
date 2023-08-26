package com.github.seaxlab.core.support.ditu.common.util;

import com.github.seaxlab.core.support.ditu.common.model.Point;
import com.github.seaxlab.core.util.CollectionUtil;
import com.google.common.base.Stopwatch;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * ditu util
 *
 * @author spy
 * @version 1.0 2023/8/25
 * @since 1.0
 */
@Slf4j
public final class MapUtil {


  /**
   * 构建点集合
   *
   * @param pointListStr
   * @return
   */
  public static List<Point> buildPoints(String pointListStr) {
    String[] pointArrayStr = pointListStr.split(";");
    return Arrays.stream(pointArrayStr).map(item -> {
      String[] point = item.split(",");
      return Point.of(parseDouble(point[0]), parseDouble(point[1]));
    }).collect(Collectors.toList());

  }

  /**
   * 判断是否在多边形区域内
   *
   * @param coordinate    x,y
   * @param polygonPoints 多边形顶点集合
   * @return
   */
  public static boolean isInPolygon(String coordinate, List<Point> polygonPoints) {
    String[] point = coordinate.split(",");
    return isInPolygon(parseDouble(point[0]), parseDouble(point[1]), polygonPoints);
  }

  /**
   * 判断是否在多边形区域内
   *
   * @param longitude     目标点的经度
   * @param latitude      目标点的维度
   * @param polygonPoints 区域各顶点集合
   * @return
   */
  public static boolean isInPolygon(double longitude, double latitude, List<Point> polygonPoints) {
    if (CollectionUtil.isEmpty(polygonPoints)) {
      log.warn("polygon points is empty.");
      return false;
    }
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      // 将要判断的横纵坐标组成一个点
      Point2D.Double target = new Point2D.Double(longitude, latitude);
      // 将区域各顶点的横纵坐标放到一个点集合里面
      List<Double> pointList = new ArrayList<>();
      for (Point point : polygonPoints) {
        Point2D.Double polygonPoint = new Point2D.Double(point.getLongitude(), point.getLatitude());
        pointList.add(polygonPoint);
      }
      return areaCheck(target, pointList);
    } finally {
      stopwatch.stop();
      log.info("isInPolygon cost={}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
  }

  /**
   * 判断是否在多边形区域内
   *
   * @param point   要判断的点
   * @param polygon 区域点集合
   * @return
   */
  public static boolean areaCheck(Point2D.Double point, List<Point2D.Double> polygon) {
    java.awt.geom.GeneralPath generalPath = new java.awt.geom.GeneralPath();
    Point2D.Double first = polygon.get(0);
    // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
    generalPath.moveTo(first.x, first.y);
    polygon.remove(0);
    for (Point2D.Double d : polygon) {
      // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
      generalPath.lineTo(d.x, d.y);
    }
    // 将几何多边形封闭
    generalPath.lineTo(first.x, first.y);
    generalPath.closePath();
    // 测试指定的 Point2D 是否在 Shape 的边界内。
    return generalPath.contains(point);
  }

  //------------------------------------private ----------------------------------
  private static double parseDouble(String value) {
    return java.lang.Double.parseDouble(value);
  }
}
