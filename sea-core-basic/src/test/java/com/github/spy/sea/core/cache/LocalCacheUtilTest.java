package com.github.spy.sea.core.cache;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.cache.util.LocalCacheUtil;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/3
 * @since 1.0
 */
@Slf4j
public class LocalCacheUtilTest extends BaseCoreTest {

    @Test
    public void testCache() throws Exception {
        Cache<String, String> cache = LocalCacheUtil.getInstance("order");

        //TODO 这里需要检测 execution exception

        String key = "order1";
        runInMultiThread(() -> {
            String orderInfo = null;
            try {
                orderInfo = cache.get(key, () -> {
                    log.info("loading real data");
                    return "---";
                });
            } catch (ExecutionException e) {
            }
            log.info("order={}", orderInfo);
        });


    }

}
