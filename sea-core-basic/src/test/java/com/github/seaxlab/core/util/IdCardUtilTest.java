package com.github.seaxlab.core.util;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Slf4j
public class IdCardUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        String idNo = "320902198812016079";

        log.info("{}", IdCardUtil.parse(idNo));
    }
}
