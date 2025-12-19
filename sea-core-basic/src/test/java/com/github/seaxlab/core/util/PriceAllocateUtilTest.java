package com.github.seaxlab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.github.seaxlab.core.util.PriceAllocateUtil.RemainderMode;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2025/12/16
 * @since 1.0
 */
@Slf4j
public class PriceAllocateUtilTest {

  // 简单分配
  @Test
  public void testAllocate() {
    BigDecimal amount = new BigDecimal("100.07");
    int n = 3;

    System.out.println("总金额: " + amount + ", 份数: " + n);
    System.out.println("LAST:        " + PriceAllocateUtil.allocate(amount, n, PriceAllocateUtil.RemainderMode.LAST));
    System.out.println("FIRST:       " + PriceAllocateUtil.allocate(amount, n, PriceAllocateUtil.RemainderMode.FIRST));
    System.out.println("ROUND_ROBIN: " + PriceAllocateUtil.allocate(amount, n, PriceAllocateUtil.RemainderMode.DISTRIBUTE_ROUND_ROBIN));
    System.out.println("DISCARD:     " + PriceAllocateUtil.allocate(amount, n, PriceAllocateUtil.RemainderMode.DISCARD));

    // 验证总和
    List<BigDecimal> last = PriceAllocateUtil.allocate(amount, n, PriceAllocateUtil.RemainderMode.LAST);
    BigDecimal sum = last.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    System.out.println("\nLAST 模式总和: " + sum + " (应等于 " + amount + ")");
  }


  // 按权重分配
  @Test
  public void testAllocateByWeight() {
    double total = 100.00;
    double[] weights = {3, 5, 2}; // 比例 3:5:2 → 应得 30, 50, 20
    System.out.println("=== 正常比例 ===");
    System.out.println("LAST: " + PriceAllocateUtil.allocateByWeights(total, weights, RemainderMode.LAST));

    // 测试有尾差的情况
    total = 100.01;
    weights = new double[]{1, 1, 1}; // 100.01 / 3 = 33.33666...
    System.out.println("\n=== 有尾差（100.01 / 3）===");
    System.out.println("LAST:        " + PriceAllocateUtil.allocateByWeights(total, weights, RemainderMode.LAST));
    System.out.println("ROUND_ROBIN: " + PriceAllocateUtil.allocateByWeights(total, weights, RemainderMode.DISTRIBUTE_ROUND_ROBIN));

    // 验证总和
    List<BigDecimal> result = PriceAllocateUtil.allocateByWeights(total, weights, RemainderMode.DISTRIBUTE_ROUND_ROBIN);
    BigDecimal sum = result.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    System.out.println("\n总和: " + sum + " (应等于 " + total + ")");
  }

}
