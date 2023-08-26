package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.support.ditu.gaode.dto.TruckRoutePlanReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/8/26
 * @since 1.0
 */
@Slf4j
public class TruckRoutePlanTest extends BaseGaoDeDiTuTest {

  //https://restapi.amap.com/v4/direction/truck?origin=109.545272,38.297345&destination=106.952161,39.178198&size=3&strategy=11&height=4&width=2.4&load=10&weight=5.9&axis=2&province=陕&number=KE4605&cartype=0&nosteps=1

  @Test
  public void testNormal() throws Exception {
    TruckRoutePlanReqDTO dto = new TruckRoutePlanReqDTO();
    configBase(dto);
    dto.setOrigin("118.749882,31.969491");
    dto.setDestination("118.873156,32.129812");
    String resp = manager.truckRoutePlan(dto);
    log.info("resp={}", resp);
  }

  @Test
  public void testNoSteps() throws Exception {
    TruckRoutePlanReqDTO dto = new TruckRoutePlanReqDTO();
    configBase(dto);
    dto.setOrigin("118.749882,31.969491");
    dto.setDestination("118.873156,32.129812");
    dto.setNosteps(1);
    String resp = manager.truckRoutePlan(dto);
    log.info("resp={}", resp);
  }

}
