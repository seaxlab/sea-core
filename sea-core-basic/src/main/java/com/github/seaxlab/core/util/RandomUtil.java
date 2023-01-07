package com.github.seaxlab.core.util;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * random util
 *
 * @author spy
 * @version 1.0 2019-06-19
 * @since 1.0
 */
@Slf4j
public final class RandomUtil {

  private RandomUtil() {
  }

  /**
   * uuid
   *
   * @return -
   */
  public static String uuid() {
    return UUID.randomUUID().toString();
  }


  /**
   * 生成不带中划线的UUID
   *
   * @return -
   */
  public static String shortUUID() {
    return uuid().replace("-", "");
  }

  /**
   * 默认一次性字符串长度
   */
  private static final int DEFAULT_NONCE_COUNT = 16;

  /**
   * 一次性字符串长度16位
   *
   * @return 16位
   */
  public static String nonceStr() {
    return nonceStr(DEFAULT_NONCE_COUNT);
  }

  /**
   * 一次性字符串长度N位
   *
   * @param count 长度
   * @return
   */
  public static String nonceStr(int count) {
    if (count <= 0) {
      count = DEFAULT_NONCE_COUNT;
    }
    return RandomStringUtils.random(count, true, true);
  }

  /**
   * numeric
   *
   * @param count -
   * @return -
   */
  public static String numeric(int count) {

    return RandomStringUtils.randomNumeric(count);
  }

  /**
   * next int
   *
   * @return int
   */
  public static int nextInt() {
    return nextInt(0, Integer.MAX_VALUE);
  }

  /**
   * next [start,end)
   *
   * @param startInclusive start include
   * @param endExclusive   end exclusive
   * @return int
   */
  public static int nextInt(final int startInclusive, final int endExclusive) {
    Preconditions.checkState(startInclusive <= endExclusive,
      "Start value must be smaller or equal to end value.");
    Preconditions.checkState(startInclusive >= 0, "Both range values must be non-negative.");

    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + ThreadLocalRandom.current().nextInt(endExclusive - startInclusive);
  }

  /**
   * next long
   *
   * @return long
   */
  public static long nextLong() {
    return nextLong(0, Long.MAX_VALUE);
  }

  /**
   * next long
   *
   * @param startInclusive start include
   * @param endExclusive   end exclusive
   * @return long
   */
  public static long nextLong(final long startInclusive, final long endExclusive) {
    Preconditions.checkState(startInclusive <= endExclusive,
      "Start value must be smaller or equal to end value.");
    Preconditions.checkState(startInclusive >= 0, "Both range values must be non-negative.");

    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + ThreadLocalRandom.current().nextLong(endExclusive - startInclusive);
  }

  /**
   * next long
   *
   * @return float
   */
  public static float nextFloat() {
    return nextFloat(0, Float.MAX_VALUE);
  }

  /**
   * next float
   *
   * @param startInclusive start include
   * @param endExclusive   end exclusive
   * @return float
   */
  public static float nextFloat(final float startInclusive, final float endExclusive) {
    Preconditions.checkState(startInclusive <= endExclusive,
      "Start value must be smaller or equal to end value.");
    Preconditions.checkState(startInclusive >= 0, "Both range values must be non-negative.");

    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + (endExclusive - startInclusive) * ThreadLocalRandom.current().nextFloat();
  }

  /**
   * next double
   *
   * @return double
   */
  public static double nextDouble() {
    return nextDouble(0, Double.MAX_VALUE);
  }

  /**
   * next double
   *
   * @param startInclusive start include
   * @param endExclusive   end exclusive
   * @return double
   */
  public static double nextDouble(final double startInclusive, final double endExclusive) {
    Preconditions.checkState(startInclusive <= endExclusive,
      "Start value must be smaller or equal to end value.");
    Preconditions.checkState(startInclusive >= 0, "Both range values must be non-negative.");

    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + ThreadLocalRandom.current().nextDouble(endExclusive - startInclusive);
  }

  /**
   * create letters
   *
   * @param count -
   * @return -
   */
  public static String alphabetic(int count) {
    return RandomStringUtils.randomAlphabetic(count);
  }

  /**
   * get random one of
   *
   * @param candidates -
   * @return -
   */
  public static String oneOf(String... candidates) {
    Preconditions.checkNotNull(candidates, "candidates can not be null");
    return candidates[nextInt(0, candidates.length)];
  }


  /**
   * get random one of
   *
   * @param candidates -
   * @return -
   */
  public static String oneOf(List<String> candidates) {
    Preconditions.checkNotNull(candidates, "candidates can not be null");
    return candidates.get(nextInt(0, candidates.size()));
  }

}
