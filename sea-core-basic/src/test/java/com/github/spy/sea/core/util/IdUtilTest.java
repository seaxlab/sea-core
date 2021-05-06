package com.github.spy.sea.core.util;

import com.github.spy.sea.core.BaseCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/23
 * @since 1.0
 */
@Slf4j
public class IdUtilTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("{}={}", i, IdUtil.getSimpleId());
        }
    }

    /**
     * 判断是否有重复
     */
    @Test
    public void testSimpleIdDuplicated() {
        Set<String> ids = new HashSet<>();

        for (int i = 0; i < 1000_000; i++) {
            String id = IdUtil.getSimpleId();
            if (ids.contains(id)) {
                log.warn("id={} is duplicated.", id);
            } else {
                ids.add(id);
            }
        }
    }

}
