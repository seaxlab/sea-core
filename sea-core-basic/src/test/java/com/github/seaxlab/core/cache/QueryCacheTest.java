package com.github.seaxlab.core.cache;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/9/10
 * @since 1.0
 */
@Slf4j
public class QueryCacheTest extends BaseCoreTest {

    @Test
    public void testQueryCache1() throws Exception {
        IQueryCache<String, String> userQueryCache = new UserQueryCache();
        IQueryCache<String, String> staffQueryCache = new StaffQueryCache();

        for (int i = 0; i < 5; i++) {
            Optional<String> optional = userQueryCache.get("" + i);
            log.info("id={}, user={}", i, optional.orElse("default"));
        }
        log.info("=======");
        // query from cache.
        for (int i = 0; i < 5; i++) {
            Optional<String> optional = userQueryCache.get("" + i);
            log.info("id={}, user={}", i, optional.orElse("default"));
        }

        log.info("load by batch {}", userQueryCache.getMap(Arrays.asList("1", "2", "3")));

    }

    public class UserQueryCache extends AbstractQueryCache<String, String> {

        @Override
        protected String getBizType() {
            return "user";
        }

        @Override
        protected Optional<String> fetchData(String key) {
            log.info("try to get user info");
            sleep(RandomUtil.nextInt(1, 3));
            return Optional.of(RandomUtil.alphabetic(10));
        }

    }

    public class StaffQueryCache extends AbstractQueryCache<String, String> {

        @Override
        protected String getBizType() {
            return "staff";
        }

        @Override
        protected Optional<String> fetchData(String key) {
            log.info("try to get staff info.");
            sleep(RandomUtil.nextInt(1, 3));

            return Optional.of(RandomUtil.alphabetic(10));
        }

    }
}
