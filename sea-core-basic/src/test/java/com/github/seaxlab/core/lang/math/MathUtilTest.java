package com.github.seaxlab.core.lang.math;

import cn.hutool.core.math.Combination;
import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.util.StringUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public class MathUtilTest extends BaseCoreTest {

  @Test
  public void testGetDelta() throws Exception {
    log.info("{}", MathUtil.getDelta(10, 2, 1));
    log.info("{}", MathUtil.getDelta(10, 5, 10));
    log.info("{}", MathUtil.getDelta(10, 9, 1));
  }


  @Test
  public void testMin() throws Exception {
    log.info("{}", MathUtil.min(10, 1, -1, 0));
  }

  @Test
  public void testMax() throws Exception {
    log.info("{}", MathUtil.max(20, 100, 11));
  }


  @Test
  public void run23() throws Exception {
    Assert.assertEquals(true, MathUtil.isPowerOfTwo(16));
    Assert.assertEquals(false, MathUtil.isPowerOfTwo(3));

    log.info("{}", MathUtil.findNextPositivePowerOfTwo(12));
    log.info("{}", MathUtil.findNextPositivePowerOfTwo(16));
    log.info("{}", MathUtil.safeFindNextPositivePowerOfTwo(-1));
  }


  String[] data = new String[]{"1", "2", "3", "4", "5"};

  @Test
  public void run44() throws Exception {
    List<String[]> list = new Combination(data).select(2);

    list.stream().forEach(item -> {
      String str = StringUtil.join(",", item);
      log.info("{}", str);
    });
  }

  @Test
  public void allSelectTest() throws Exception {
    //
    for (int i = 0; i < data.length; i++) {
      log.info("选择{}个", i + 1);
      List<String[]> list = new Combination(data).select(i + 1);
      list.stream().forEach(item -> {
        String str = StringUtil.join(",", item);
        log.info("{}", str);
      });
      log.info("-------------------");
    }
  }

  @Test
  public void run74() throws Exception {
    // 全组合
    List<String[]> list = MathUtil.combinationSelectAll(data);
    list.stream().forEach(item -> {
      String str = StringUtil.join(",", item);
      log.info("{}", str);
    });
  }

  @Test
  public void run58() throws Exception {
    List<String[]> list = MathUtil.combinationSelectAllNoOrder(data);
    list.stream().forEach(item -> {
      String str = StringUtil.join(",", item);
      log.info("{}", str);
    });
  }

  @Test
  public void run67() throws Exception {
    sort(new String[]{"1"});
    sort(new String[]{"1", "2"});
    sort(new String[]{"1", "2", "3"});
  }

  private static void sort(String[] data) {
    List<String[]> list = MathUtil.combinationSelectAllNoOrder(data);
    list.stream().forEach(item -> {
      String str = StringUtil.join(",", item);
      log.info("{}", str);
    });
    log.info("-------");
  }

  @Test
  public void testIsContinuous() throws Exception {

    log.info("{}", MathUtil.isContinuous(new int[]{1, 2, 3, 4, 5, 6}));
    log.info("{}", MathUtil.isContinuous(new int[]{1, 2, 3, 5, 5, 6}));
  }

  @Test
  public void testIsContinuous2() throws Exception {
    log.info("{}", MathUtil.findContinuousIndex(new int[]{1, 2, 3, 4, 5, 6}, 3));
    log.info("{}", MathUtil.findContinuousIndex(new int[]{1, 2, 5, 6, 7, 8}, 3));

  }

}
