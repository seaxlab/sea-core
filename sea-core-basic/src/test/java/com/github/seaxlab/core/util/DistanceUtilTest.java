package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * distance util test
 *
 * @author spy
 * @version 1.0 2023/2/11
 * @since 1.0
 */
@Slf4j
public class DistanceUtilTest extends BaseCoreTest {

  @Test
  public void testGetDistance() throws Exception {
    Double longitude1 = 104.02436160776520;
    Double latitude1 = 30.75109533912993;
    Double longitude2 = 104.02270936701203;
    Double latitude2 = 30.75073113557945;

    double distance = DistanceUtil.getDistance(longitude1, latitude1, longitude2, latitude2);
    int distance2 = (int) distance;
    log.info("({},{})->({},{}), distance={},distance2={}", longitude1, latitude1, longitude2, latitude2, distance,
      distance2);
  }

}
