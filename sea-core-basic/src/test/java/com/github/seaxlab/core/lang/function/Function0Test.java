package com.github.seaxlab.core.lang.function;

import com.github.seaxlab.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/30
 * @since 1.0
 */
@Slf4j
public class Function0Test extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        Function0 function0 = () -> log.info("function0 execute.");
        function0.execute();

    }
}
