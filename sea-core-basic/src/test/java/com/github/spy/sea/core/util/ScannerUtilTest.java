package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Scanner;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/3/28
 * @since 1.0
 */
@Slf4j
public class ScannerUtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        Scanner sc = new Scanner(System.in);
        // 读取一个整数
        sc.nextInt();

        // 读取一行字符串
        sc.nextLine();
    }

}
