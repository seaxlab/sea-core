package com.github.seaxlab.core.support.ditu.gaode;

import com.github.seaxlab.core.support.ditu.BaseDituTest;
import com.github.seaxlab.core.support.ditu.gaode.dto.BaseGaoDeReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

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

  @Before
  public void before() {
    manager = new GaoDeMapManager();
  }


  protected void configBase(BaseGaoDeReqDTO dto) {
    dto.setKey(getConfig("sea.gaode.key"));
  }


}
