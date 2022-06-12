package com.github.seaxlab.core.exception;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/8/30
 * @since 1.0
 */
@Slf4j
public class PreconditionTest extends BaseCoreTest {

    @Test
    public void testNotNull() throws Exception {
        User user = null;
        Precondition.checkNotNull(user, "user cannot be null");
    }

    @Test
    public void test25() throws Exception {
        String value = "";
        Precondition.checkNotEmpty(value, "value cannot be empty.");
    }

}
