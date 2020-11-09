package com.github.spy.sea.core.algorithm;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/10/30
 * @since 1.0
 */
@Slf4j
public class CombineUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        int[] a = {1, 2, 3, 4};  // 初始数组
        CombineUtil.arrangementSelect(a, 4);
        CombineUtil.combinationSelect(a, 3);
    }

    @Test
    public void run24() throws Exception {

        String str[] = {"A", "B", "C", "D", "E"};

        int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));

        for (int i = 1; i <= nBit; i++) {
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    System.out.print(str[j]);
                }
            }
            System.out.println("");
        }
    }
}
