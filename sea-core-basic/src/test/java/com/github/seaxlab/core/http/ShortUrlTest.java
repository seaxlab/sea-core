package com.github.seaxlab.core.http;

import com.github.seaxlab.core.http.simple.HttpClientUtil;
import com.github.seaxlab.core.util.TemplateUtil;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/17
 * @since 1.0
 */
@Slf4j
public class ShortUrlTest {
  @Test
  public void run47() throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    for (int i = 0; i < 1000; i++) {
      String target = "http://www.baidu.com/s?wd=" + new Random().nextInt(10000);
      stopwatch.reset();
      stopwatch.start();
      String url = TemplateUtil.format("http://tinyurl.com/api-create.php?url={}", target);
      String ret = HttpClientUtil.get(url);
      log.info("cost={}ms,ret={}", stopwatch.elapsed(TimeUnit.MILLISECONDS), ret);
    }
  }
}
