package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
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
    public void run17() throws Exception {
        println(NumberUtil.substract(1, 10, 20));
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
}
