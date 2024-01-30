package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.support.ditu.BaseDituTest;
import com.github.seaxlab.core.support.ditu.gaode.dto.BaseGaoDeReqDTO;
import com.github.seaxlab.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

/**
 * gaode ditu test
 *
 * @author spy
 * @version 1.0 2023/8/26
 * @since 1.0
 */
@Slf4j
public class BaseGaoDeDiTuTest extends BaseDituTest {

  protected GaoDeMapManager manager;

  protected final String LOCATION_云密城 = "118.749882,31.969491";
  protected final String LOCATION_南京南站 = "118.797968,31.968846";
  protected final String LOCATION_太平山公园 = "118.873156,32.129812";

  @Before
  public void before() {
    manager = new GaoDeMapManager();
  }


  public <T extends BaseGaoDeReqDTO> T buildReqDTO(Class<T> clazz) {
    T dto = ClassUtil.newInstance(clazz);
    dto.setKey(getConfig("sea.gaode.key"));
    return dto;
  }

  protected void configBase(BaseGaoDeReqDTO dto) {
    dto.setKey(getConfig("sea.gaode.key"));
  }


}
