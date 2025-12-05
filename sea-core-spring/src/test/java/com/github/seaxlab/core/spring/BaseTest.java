package com.github.seaxlab.core.spring;

import com.github.seaxlab.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
public class BaseTest extends AbstractCoreTest {


  @Test
  public void test18() {
    String referer = "https://www.baidu.com/admin";
    MultiValueMap<String, String> queryParamMap =
      UriComponentsBuilder.fromHttpUrl(referer).build().getQueryParams();

    log.info("{}",queryParamMap);
  }
}
