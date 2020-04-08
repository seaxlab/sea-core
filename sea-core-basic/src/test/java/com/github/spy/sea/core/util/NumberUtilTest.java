package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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
}
