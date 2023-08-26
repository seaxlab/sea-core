package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.support.ditu.gaode.dto.DistrictQueryReqDTO;
import com.github.seaxlab.core.support.ditu.gaode.dto.InputTipsReqDTO;
import com.github.seaxlab.core.support.ditu.gaode.dto.ReGeoReqDTO;
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
public class WebApiTest extends BaseGaoDeDiTuTest {


  @Test
  public void testDistrict() throws Exception {
    DistrictQueryReqDTO dto = buildReqDTO(DistrictQueryReqDTO.class);
    dto.setKeywords("南京");
    log.info("{}", manager.queryDistrict(dto));
  }

  @Test
  public void testInputTips() throws Exception {
    InputTipsReqDTO dto = buildReqDTO(InputTipsReqDTO.class);
    //
    dto.setKeywords("南京南站");
    log.info("{}", manager.queryInputTips(dto));
  }

  @Test
  public void testReGeo() throws Exception {
    ReGeoReqDTO dto = buildReqDTO(ReGeoReqDTO.class);
    //
    dto.setLocation(LOCATION_南京南站);
    log.info("{}", manager.reGeo(dto));
  }

}
