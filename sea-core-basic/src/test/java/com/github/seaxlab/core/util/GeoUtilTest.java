package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/4/23
 * @since 1.0
 */
@Slf4j
public class GeoUtilTest {

  @Test
  public void testGetDistance() throws Exception {
    Double longitude1 = 104.02436160776520;
    Double latitude1 = 30.75109533912993;
    Double longitude2 = 104.02270936701203;
    Double latitude2 = 30.75073113557945;

    double distance = GeoUtil.getDistance(longitude1, latitude1, longitude2, latitude2);
    int distance2 = (int) distance;
    log.info("({},{})->({},{}), distance={},distance2={}", longitude1, latitude1, longitude2, latitude2,
      distance, distance2);
  }

  @Test
  public void testDistanceDesc() throws Exception {
    log.info("{}", GeoUtil.getDistanceDesc(100));
    log.info("{}", GeoUtil.getDistanceDesc(1000));
    log.info("{}", GeoUtil.getDistanceDesc(1001));
  }

}
