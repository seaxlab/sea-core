package com.github.seaxlab.core.security;

import cn.hutool.core.codec.Base62;
import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.security.util.Base62Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/5/5
 * @since 1.0
 */
@Slf4j
public class Base62UtilTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        log.info("{}", Base62.encode("1234576890"));
    }

    @Test
    public void testAll() {
        Assert.assertEquals("1A0afZkifxhHiC", Base62Util.encode("1234576890"));
        Assert.assertEquals("1234576890", Base62Util.decode("1A0afZkifxhHiC"));
    }
}
