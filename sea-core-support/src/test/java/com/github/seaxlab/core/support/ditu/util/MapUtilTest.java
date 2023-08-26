package com.github.seaxlab.core.support.ditu.util;

import com.github.seaxlab.core.support.ditu.BaseDituTest;
import com.github.seaxlab.core.support.ditu.common.model.Point;
import com.github.seaxlab.core.support.ditu.common.util.MapUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/8/25
 * @since 1.0
 */
@Slf4j
public class MapUtilTest extends BaseDituTest {

  @Test
  public void test17() throws Exception {
    // 云密城
    String pointListStr = "118.750182,31.971363;118.74956,31.970198;118.749367,31.968996;118.749367,31.967085;118.751491,31.96734;118.751921,31.969306;118.75235,31.970999;118.752393,31.971435;118.751749,31.971399";

    List<Point> points = MapUtil.buildPoints(pointListStr);
    log.info("{}", MapUtil.isInPolygon(118.749909, 31.969447, points)); //易司拓
    log.info("{}", MapUtil.isInPolygon(118.748579, 31.969278, points));//创智大厦
    log.info("{}", MapUtil.isInPolygon(118.751663, 31.972637, points)); //大数据产业基地
  }

}
