package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/19
 * @since 1.0
 */
@Slf4j
public class HtmlUtilTest extends BaseCoreTest {

  @Test
  public void run17() throws Exception {

    log.info("{}", StringEscapeUtils.unescapeHtml4("<a>a</a>"));
    log.info("{}", StringEscapeUtils.escapeHtml4("<a>a</a>"));
    log.info("{}", StringEscapeUtils.unescapeHtml4("&lt;a&gt;a&lt;/a&gt;"));
  }
}
