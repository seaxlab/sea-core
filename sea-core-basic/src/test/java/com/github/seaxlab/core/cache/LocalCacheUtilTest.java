package com.github.seaxlab.core.cache;

import com.github.seaxlab.core.BaseCoreTest;
import com.github.seaxlab.core.cache.util.LocalCacheUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    private LoadingCache<String, Optional<String>> loadingCache = CacheBuilder.newBuilder()
                                                                              .expireAfterWrite(10, TimeUnit.MINUTES)
                                                                              .maximumSize(1000)
                                                                              .build(new CacheLoader<String, Optional<String>>() {
                                                                                  @Override
                                                                                  public Optional<String> load(String key) throws Exception {
                                                                                      log.info("load from db, key={}", key);

                                                                                      //重点：here cannot return null;
                                                                                      return Optional.ofNullable("");
                                                                                  }
                                                                              });

    @Test
    public void testLoadingCacheNull() throws Exception {
        String key = "key1";
        runInMultiThread(() -> {
            Optional optional = loadingCache.getUnchecked(key);
            log.info("value={}", optional.get());
        });
    }

}
