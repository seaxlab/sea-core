package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 金额分配
 *
 * @author spy
 * @version 1.0 2025/12/16
 * @since 1.0
 */
@Slf4j
public class PriceAllocateUtil {

  /**
   * 尾差处理策略
   */
  public enum RemainderMode {
    DISCARD,           // 舍弃尾差（不推荐用于金额）
    LAST,              // 加到最后一个
    FIRST,             // 加到第一个
    DISTRIBUTE_ROUND_ROBIN // 循环分配（按最小货币单位，如 0.01）
  }


  // ===== 便捷重载方法（默认 scale=2, roundingMode=HALF_UP） =====

  public static List<BigDecimal> allocate(BigDecimal totalAmount, int numParts, RemainderMode mode) {
    return allocate(totalAmount, numParts, 2, RoundingMode.HALF_UP, mode);
  }

  public static List<BigDecimal> allocate(double totalAmount, int numParts, RemainderMode mode) {
    return allocate(BigDecimal.valueOf(totalAmount), numParts, 2, RoundingMode.HALF_UP, mode);
  }

  /**
   * 分摊金额（支持多种尾差处理模式）
   *
   * @param totalAmount   总金额（必须 >= 0）
   * @param numParts      分摊份数（> 0）
   * @param scale         保留小数位数（如人民币为 2）
   * @param roundingMode  基础分摊时的舍入模式（建议使用 HALF_UP 或 DOWN）
   * @param remainderMode 尾差处理模式
   * @return 分摊结果列表
   */
  public static List<BigDecimal> allocate(BigDecimal totalAmount, int numParts, int scale, RoundingMode roundingMode,
                                          RemainderMode remainderMode) {

    if (totalAmount == null || numParts <= 0 || scale < 0) {
      throw new IllegalArgumentException("参数非法：totalAmount 不能为 null，numParts > 0，scale >= 0");
    }
    if (roundingMode == null || remainderMode == null) {
      throw new IllegalArgumentException("roundingMode 和 remainderMode 不能为 null");
    }

    BigDecimal zero = BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP);
    totalAmount = totalAmount.setScale(scale, RoundingMode.HALF_UP);

    if (totalAmount.compareTo(zero) == 0) {
      List<BigDecimal> result = new ArrayList<>();
      for (int i = 0; i < numParts; i++) {
        result.add(zero);
      }
      return result;
    }

    // 基础每份金额（使用指定舍入模式）
    BigDecimal perPart = totalAmount.divide(BigDecimal.valueOf(numParts), scale, roundingMode);
    BigDecimal sumSoFar = perPart.multiply(BigDecimal.valueOf(numParts)).setScale(scale, RoundingMode.HALF_UP);
    BigDecimal remainder = totalAmount.subtract(sumSoFar).setScale(scale, RoundingMode.HALF_UP);

    List<BigDecimal> parts = new ArrayList<>();
    for (int i = 0; i < numParts; i++) {
      parts.add(perPart);
    }

    // 处理尾差
    if (remainder.compareTo(BigDecimal.ZERO) != 0) {
      switch (remainderMode) {
        case DISCARD:
          // 不做任何事，直接丢弃尾差（总和会少 remainder）
          break;

        case LAST:
          int lastIndex = parts.size() - 1;
          parts.set(lastIndex, parts.get(lastIndex).add(remainder));
          break;

        case FIRST:
          parts.set(0, parts.get(0).add(remainder));
          break;

        case DISTRIBUTE_ROUND_ROBIN:
          distributeRemainderRoundRobin(parts, remainder, scale);
          break;

        default:
          throw new UnsupportedOperationException("未支持的尾差模式: " + remainderMode);
      }
    }

    // 最终统一设置 scale 防止精度漂移
    for (int i = 0; i < parts.size(); i++) {
      parts.set(i, parts.get(i).setScale(scale, RoundingMode.HALF_UP));
    }

    return parts;
  }

  public static List<BigDecimal> allocateByWeights(BigDecimal totalAmount, List<BigDecimal> weights, RemainderMode mode) {
    return allocateByWeights(totalAmount, weights, 2, RoundingMode.HALF_UP, mode);
  }

  public static List<BigDecimal> allocateByWeights(double totalAmount, double[] weights, RemainderMode mode) {
    List<BigDecimal> weightList = new ArrayList<>();
    for (double w : weights) {
      weightList.add(BigDecimal.valueOf(w));
    }
    return allocateByWeights(BigDecimal.valueOf(totalAmount), weightList, 2, RoundingMode.HALF_UP, mode);
  }

  /**
   * 按权重比例分摊金额（核心方法）
   *
   * @param totalAmount   总金额（>=0）
   * @param weights       权重列表（每个 >=0，不能为空，长度 >=1）
   * @param scale         金额小数位数（如 2）
   * @param roundingMode  基础计算舍入方式（建议 HALF_UP）
   * @param remainderMode 尾差处理策略
   * @return 分摊结果列表，顺序与 weights 一致
   */
  public static List<BigDecimal> allocateByWeights(BigDecimal totalAmount, List<BigDecimal> weights, int scale,
    RoundingMode roundingMode, RemainderMode remainderMode) {

    if (totalAmount == null || weights == null || weights.isEmpty()) {
      throw new IllegalArgumentException("总金额或权重列表不能为空");
    }
    if (scale < 0 || roundingMode == null || remainderMode == null) {
      throw new IllegalArgumentException("参数非法");
    }

    // 标准化金额和权重精度
    totalAmount = totalAmount.setScale(scale, RoundingMode.HALF_UP);
    List<BigDecimal> normalizedWeights = new ArrayList<>();
    for (BigDecimal w : weights) {
      if (w == null || w.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("权重不能为 null 或负数");
      }
      normalizedWeights.add(w.stripTrailingZeros()); // 去除无意义零
    }

    // 特殊情况：总金额为 0
    if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
      List<BigDecimal> result = new ArrayList<>();
      for (int i = 0; i < normalizedWeights.size(); i++) {
        result.add(BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP));
      }
      return result;
    }

    // 计算总权重
    BigDecimal totalWeight = normalizedWeights.stream()
                                              .reduce(BigDecimal.ZERO, BigDecimal::add);

    // 如果总权重为 0，则所有分摊为 0（避免除零）
    if (totalWeight.compareTo(BigDecimal.ZERO) == 0) {
      List<BigDecimal> result = new ArrayList<>();
      for (int i = 0; i < normalizedWeights.size(); i++) {
        result.add(BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP));
      }
      return result;
    }

    // 初步计算每份金额（按比例）
    List<BigDecimal> allocated = new ArrayList<>();
    BigDecimal sumSoFar = BigDecimal.ZERO;

    for (BigDecimal weight : normalizedWeights) {
      // 比例 = weight / totalWeight
      BigDecimal ratio = weight.divide(totalWeight, 10, RoundingMode.HALF_UP); // 高精度中间计算
      BigDecimal amount = totalAmount.multiply(ratio).setScale(scale, roundingMode);
      allocated.add(amount);
      sumSoFar = sumSoFar.add(amount);
    }

    sumSoFar = sumSoFar.setScale(scale, RoundingMode.HALF_UP);
    BigDecimal remainder = totalAmount.subtract(sumSoFar).setScale(scale, RoundingMode.HALF_UP);

    // 处理尾差
    if (remainder.compareTo(BigDecimal.ZERO) != 0) {
      switch (remainderMode) {
        case DISCARD:
          break; // 直接丢弃
        case LAST:
          int lastIndex = allocated.size() - 1;
          allocated.set(lastIndex, allocated.get(lastIndex).add(remainder));
          break;
        case FIRST:
          allocated.set(0, allocated.get(0).add(remainder));
          break;
        case DISTRIBUTE_ROUND_ROBIN:
          distributeRemainderRoundRobin(allocated, remainder, scale);
          break;
        default:
          throw new UnsupportedOperationException("不支持的尾差模式: " + remainderMode);
      }
    }

    // 最终统一设置 scale
    for (int i = 0; i < allocated.size(); i++) {
      allocated.set(i, allocated.get(i).setScale(scale, RoundingMode.HALF_UP));
    }

    return allocated;
  }

  //-------------------------------------------------------------------------------------------------------------------

  /**
   * 循环分配尾差（以最小单位 0.01 为例）
   * 例如 remainder = 0.03，则前3人各 +0.01
   */
  private static void distributeRemainderRoundRobin(List<BigDecimal> parts, BigDecimal remainder, int scale) {
    // 计算最小货币单位（如 scale=2 => 0.01）
    BigDecimal unit = BigDecimal.ONE.movePointLeft(scale); // 10^(-scale)
    BigDecimal absRemainder = remainder.abs();
    int units = absRemainder.divide(unit, 0, RoundingMode.DOWN).intValue(); // 可分配的单位数

    BigDecimal delta = remainder.signum() >= 0 ? unit : unit.negate();

    for (int i = 0; i < units && i < parts.size(); i++) {
      parts.set(i, parts.get(i).add(delta));
    }
  }


}
