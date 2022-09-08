package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-21
 * @since 1.0
 */
@Slf4j
public class NumberUtilTest extends BaseCoreTest {


    @Test
    public void testAddInt() throws Exception {
        log.info("{}", NumberUtil.addInt("1", "-1", "", "-0"));
    }

    @Test
    public void testAddInt2() throws Exception {
        log.info("{}", NumberUtil.addInt("1", "2.000"));
    }

    @Test
    public void testAddLong() throws Exception {
        Assert.assertEquals(100, NumberUtil.addLong("1", "-1", "", "100"));
    }

    @Test
    public void testStr() throws Exception {
        log.info("{}", NumberUtil.addStr("1", "2", "3.0001"));
    }


    @Test
    public void testSubstract() throws Exception {
        log.info("{}", NumberUtil.substract(1, 10, 20));
    }

    @Test
    public void testSubstract2() throws Exception {
        log.info("{}", NumberUtil.substract("1.0", "0.0001", "0.000005"));
    }


    @Test
    public void run23() throws Exception {
        //0,1,2,3
        Assert.assertEquals(NumberUtil.median(0, 3), 2);
        //1,2,3
        Assert.assertEquals(NumberUtil.median(1, 3), 2);

        //0,1,2,3
        Assert.assertEquals(NumberUtil.medianBefore(0, 3), 1);
        //1,2,3
        Assert.assertEquals(NumberUtil.medianBefore(1, 3), 2);
    }

    @Test
    public void run37() throws Exception {

        Assert.assertEquals(NumberUtil.min(3, 1, 3), 1);
        Assert.assertEquals(NumberUtil.max(3, 1, 3, 10), 10);

    }

    @Test
    public void run45() throws Exception {
        log.info("{}", NumberUtil.scaleUp(1.1141d, 2));
        log.info("{}", NumberUtil.scaleUp(1.1151d, 2));
    }

    @Test
    public void run51() throws Exception {
        log.info("a={}", 1 / 200.0);


        double a = NumberUtil.scaleUp(1 / 200.0, 3).doubleValue();
        log.info("a={}", a);

//        NumberUtil.divide()

        BigDecimal bd = new BigDecimal("" + (1 / 200.0));
        bd.setScale(3, RoundingMode.HALF_UP);
        log.info("v={}", bd.doubleValue()); //0.005

        BigDecimal bd2 = new BigDecimal(1 / 200.0); // here is not 0.005
        bd2.setScale(3, RoundingMode.UP);
        log.info("v={}", bd2.doubleValue()); //0.005

        BigDecimal c = NumberUtil.divide(1.0, 200.0, 3, RoundingMode.UP);
        log.info("c={}", c.doubleValue());
    }

    @Test
    public void test10T062() throws Exception {
        log.info("{}", NumberUtil.convert10To62(123456));
        log.info("{}", NumberUtil.convert10To62(100000000000000L));
        log.info("{}", NumberUtil.convert10To62(1000000000000000000L));
        log.info("{}", NumberUtil.convert62To10("ZZZZZZZZZZZZZ"));
    }

    @Test
    public void testConvert() throws Exception {
        int i = 666;
        String hexString = Integer.toHexString(i);
        System.out.println("十进制转换为十六进制：" + hexString);

        int hexInt = Integer.parseInt(hexString, 16);
        //如果是负数十六进制转为十进制，使用 BigInteger 获取
        int hexNegativeInt = new BigInteger(hexString, 16).intValue();

        String binaryString = Integer.toBinaryString(i);
        System.out.println("十进制转换为二进制：" + binaryString);

        int binaryInt = Integer.parseInt(binaryString, 2);

        String octalString = Integer.toOctalString(i);

        System.out.println("十进制转换为八进制：" + octalString);
        int octalInt = Integer.parseInt(octalString, 8);
        System.out.println("十六进制反转：" + hexInt + " 二进制反转：" + binaryInt + " 八进制反转：" + octalInt);
    }

    @Test
    public void testConvert2() {
        Assert.assertEquals("10100", NumberUtil.convert10To2(20));
        Assert.assertEquals("24", NumberUtil.convert10To8(20));
        Assert.assertEquals("14", NumberUtil.convert10To16(20));
        Assert.assertEquals("k", NumberUtil.convert10To62(20));

        Assert.assertEquals(20, NumberUtil.convert2To10("10100").intValue());
        Assert.assertEquals(20, NumberUtil.convert8To10("24").intValue());
        Assert.assertEquals(20, NumberUtil.convert16To10("14").intValue());
        Assert.assertEquals(20, NumberUtil.convert62To10("k"));

    }

    @Test
    public void testRatio() throws Exception {
        log.info("{}", NumberUtil.ratio(10, 30));
    }

    @Test
    public void testToPercent() throws Exception {
        log.info("{}", NumberUtil.toPercentStr(BigDecimal.valueOf(0.123)));
        log.info("{}", NumberUtil.toPercentStr(BigDecimal.valueOf(3.14526)));
        log.info("{}", NumberUtil.toPercentStr2(BigDecimal.valueOf(3.14526)));
    }

    @Test
    public void testToString() throws Exception {
        log.info("{}", NumberUtil.toString(BigDecimal.valueOf(0.123)));

    }

    @Test
    public void testComparisonRatio() throws Exception {
        log.info("{}", NumberUtil.comparisonRatio(10, 30));

    }
}

