package com.github.seaxlab.core.support.ditu.common.model;

import com.github.seaxlab.core.component.json.jackson.util.JacksonUtil;
import lombok.Data;

/**
 * point
 *
 * @author spy
 * @version 1.0 2023/08/25
 * @since 1.0
 */
@Data
public class Point {

  private double longitude;
  private double latitude;

  public static Point of(double longitude, double latitude) {
    Point point = new Point();
    point.setLongitude(longitude);
    point.setLatitude(latitude);
    return point;
  }

  @Override
  public String toString() {
    return JacksonUtil.toString(this);
  }

}
