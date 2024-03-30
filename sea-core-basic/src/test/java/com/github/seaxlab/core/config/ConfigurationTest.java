package com.github.seaxlab.core.config;

import static com.github.seaxlab.core.test.util.TestUtil.runInMultiThread;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-26
 * @since 1.0
 */
@Slf4j
public class ConfigurationTest extends BaseCoreTest {

  Configuration config;

  @Before
  public void before() {
    System.setProperty("sea.config.xx", "xx");
    config = ConfigurationFactory.getInstance();

  }

  @Test
  public void run16() throws Exception {

    println("1");
    println("a");
    println("user.home");
    println("sea.env");
    println("sea.config.env");
    println("sea.config.title");
    println("sea.config.xx");
  }

  @Test
  public void run38() throws Exception {
    config.putString("aa", "abc");

    println("aa");
  }

  @Test
  public void run45() throws Exception {
    config.putString("aa", "abc");

    println("aa");
    config.remove("aa");
    println("aa");
  }

  @Test
  public void run54() throws Exception {
    System.out.println(config);
    config.putString("abc", "111");
    System.out.println(config);

    config.putString("c", "c");
    System.out.println(config);

    config.putString("abc", "123");
    System.out.println(config);

    println("abc");
    println("c");

  }

  @Test
  public void test72() throws Exception {

    //FIXME 没法写
    Runnable runnable = () -> {

    };
    runInMultiThread(runnable);
  }

  private void println(String path) {
    log.info("{}={}", path, config.getString(path));
  }
}
