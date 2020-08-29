package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/8/29
 * @since 1.0
 */
@Slf4j
public class BitUtilTest extends BaseCoreTest {

    // 二进制表示 0001
    public static final int LEFT = 1;
    // 二进制表示 0010
    public static final int RIGHT = LEFT << 1;
    // 二进制表示 0100
    public static final int TOP = LEFT << 2;
    // 二进制表示 1000
    public static final int BOTTOM = LEFT << 3;
    // 水平居中, 二进制表示 0011
    public static final int HORIZONTAL_CENTER = LEFT | RIGHT;
    // 垂直居中, 二进制表示 1100
    public static final int VERTICAL_CENTER = TOP | BOTTOM;
    // 居中, 二进制表示 1111
    public static final int CENTER = HORIZONTAL_CENTER | VERTICAL_CENTER;
    // 默认左上角, 二进制表示 0101
    public static final int DEFAULT = LEFT | TOP;


    @Test
    public void run17() throws Exception {

        int defaultValue = LEFT;

        defaultValue = BitUtil.addFlag(defaultValue, RIGHT);
        log.info("add right, ret={}", defaultValue);

        log.info("has right, ret={}", BitUtil.hasFlag(defaultValue, RIGHT));
        defaultValue = BitUtil.clearFlag(defaultValue, RIGHT);

        log.info("remove right, ret={}", BitUtil.clearFlag(defaultValue, RIGHT));

    }
}
