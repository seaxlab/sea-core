package com.github.seaxlab.core.lang.math;

import com.github.seaxlab.core.util.ArrayUtil;
import com.github.seaxlab.core.util.SetUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * complex operation of math
 * <p>
 * Arrangement and Combination
 *
 * @author spy
 * @version 1.0 11/19/20
 * @since 1.0
 */
@Slf4j
public final class MathUtil {


  /**
   * 获取偏移量
   *
   * @param totalAmount    需求总数
   * @param providedAmount 已提供总数
   * @param dbAmount       提供方能提供数
   * @return
   */
  public static int getDelta(int totalAmount, int providedAmount, int dbAmount) {
    return Math.min(totalAmount - providedAmount, dbAmount);
  }


  /**
   * get min value
   *
   * @param numbers
   * @return
   */
  public static BigDecimal min(Number... numbers) {
    if (numbers == null || numbers.length == 0) {
      return null;
    }

    List<BigDecimal> list = new ArrayList<>(numbers.length);
    for (Number item : numbers) {
      if (item == null) {
        continue;
      }
      list.add(new BigDecimal(item.toString()));
    }
    list.sort(Comparator.comparing(BigDecimal::doubleValue));
    return list.get(0);
  }

  /**
   * get max value.
   *
   * @param numbers
   * @return
   */
  public static BigDecimal max(Number... numbers) {
    if (numbers == null || numbers.length == 0) {
      return null;
    }

    List<BigDecimal> list = new ArrayList<>(numbers.length);
    for (Number item : numbers) {
      if (item == null) {
        continue;
      }
      list.add(new BigDecimal(item.toString()));
    }
    list.sort(Comparator.comparing(BigDecimal::doubleValue).reversed());
    return list.get(0);
  }

  /**
   * check is power of 2.
   *
   * @param val
   * @return
   */
  public static boolean isPowerOfTwo(int val) {
    return (val & -val) == val;
  }

  /**
   * Fast method of finding the next power of 2 greater than or equal to the supplied value.
   *
   * <p>If the value is {@code <= 0} then 1 will be returned.
   * This method is not suitable for {@link Integer#MIN_VALUE} or numbers greater than 2^30.
   *
   * @param value from which to search for next power of 2
   * @return The next power of 2 or the value itself if it is a power of 2
   */
  public static int findNextPositivePowerOfTwo(final int value) {
    assert value > Integer.MIN_VALUE && value < 0x40000000;
    return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
  }

  /**
   * Fast method of finding the next power of 2 greater than or equal to the supplied value.
   * <p>This method will do runtime bounds checking and call {@link #findNextPositivePowerOfTwo(int)} if within a
   * valid range.
   *
   * @param value from which to search for next power of 2
   * @return The next power of 2 or the value itself if it is a power of 2.
   * <p>Special cases for return values are as follows:
   * <ul>
   *     <li>{@code <= 0} -> 1</li>
   *     <li>{@code >= 2^30} -> 2^30</li>
   * </ul>
   */
  public static int safeFindNextPositivePowerOfTwo(final int value) {
    return value <= 0 ? 1 : value >= 0x40000000 ? 0x40000000 : findNextPositivePowerOfTwo(value);
  }


  /**
   * 组合选择- 全组合
   *
   * @param data 待选列表
   * @return
   */
  public static List<String[]> combinationSelectAll(String[] data) {
    List<String[]> result = new ArrayList<>();
    backtrack(data, 0, new ArrayList<>(), result);
    return result;
  }

  private static void backtrack(String[] data, int start, List<String> current, List<String[]> result) {
    if (!current.isEmpty()) {
      // 将当前组合加入结果（转为 String[]）
      result.add(current.toArray(new String[0]));
    }

    for (int i = start; i < data.length; i++) {
      current.add(data[i]);           // 选择
      backtrack(data, i + 1, current, result); // 递归
      current.remove(current.size() - 1); // 撤销选择
    }
  }

  /**
   * 全组合后，无序
   * <p>
   * 待组合列表：1,2,3 (1),(2,3) (2),(1,3) (3),(1,2) (1,2),(3) // 重复 (1,3),(2) // 重复 (2,3),(1) (1,2,3)
   * </p>
   *
   * @param data
   * @return
   */
  public static List<String[]> combinationSelectAllNoOrder(String[] data) {
    List<String[]> list = combinationSelectAll(data);

    Set<String> allSet = SetUtil.toSet(data);
    int loop = list.size() % 2 == 0 ? list.size() / 2 : (list.size() / 2 + 1);
    for (int i = 0; i < loop; i++) {
      String[] item = list.get(i);
      if (item == null) {
        continue;
      }
      Set<String> first = SetUtil.toSet(item);

      for (int j = i; j < list.size(); j++) {
        String[] item2 = list.get(j);
        if (item2 == null) {
          continue;
        }
        Set<String> second = SetUtil.toSet(item2);
        if ((first.size() + second.size() == allSet.size()) && SetUtil.union(first, second).size() == allSet.size()) {
          list.set(j, null);
        }
      }
    }

    return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }

  /**
   * 找出第一个满足count个连续的数组的下标
   *
   * @param array
   * @param count
   * @return -1 不存在
   */
  public static int findContinuousIndex(int[] array, int count) {
    if (array.length < count) {
      log.warn("count is large than array,count={},array={}", count, array.length);
      return -1;
    }

    boolean flag;
    for (int i = count; i < array.length; i++) {
      int[] subArray = ArrayUtil.sub(array, i - count, i);
      flag = isContinuous(subArray);
      if (flag) {
        return i - count;
//        break;
      }
    }

    return -1;
  }

  /**
   * 判断整个数组是否连续
   *
   * @param array 升序数据
   * @return
   */
  public static boolean isContinuous(int[] array) {
    boolean flag = true;
    for (int i = 0; i < array.length - 1; i++) {
      int start = array[i];
      int next = array[i + 1];
      if (start + 1 != next) {
        flag = false;
        break;
      }
    }

    return flag;
  }

  /**
   * 判断是否连续，
   *
   * @param array 倒序数组
   * @return
   */
  public static boolean isContinuousDesc(int[] array) {
    boolean flag = true;
    for (int i = 0; i < array.length - 1; i++) {
      int start = array[i];
      int next = array[i + 1];
      if (start - 1 != next) {
        flag = false;
        break;
      }
    }

    return flag;
  }

}
