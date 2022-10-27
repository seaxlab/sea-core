package com.github.seaxlab.core.spring.aop.util;

import com.github.seaxlab.core.spring.BaseTest;
import com.github.seaxlab.core.spring.aop.enums.AopExpressionEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/10/27
 * @since 1.0
 */
@Slf4j
public class AopUtilTest extends BaseTest {

    @Test
    public void testGetByOr() throws Exception {
        String expression = AopUtil.getByOr(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, "com.github.abc,com.xxx.abc");
        log.info("{}", expression);
    }

    @Test
    public void testGetByAnd() throws Exception {
        String expression = AopUtil.getByAnd(AopExpressionEnum.EXECUTION_PACKAGE_AND_SUB, "com.github.abc,com.xxx.abc");
        log.info("{}", expression);
    }
}
