package com.github.spy.sea.core.cache.guava;

import com.github.spy.sea.core.cache.BaseTest;
import com.github.spy.sea.core.model.ImmutableKey;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/27
 * @since 1.0
 */
@Slf4j
public class CacheTest extends BaseTest {

    @Test
    public void test17() throws Exception {

        ImmutableKey key = ImmutableKey.of("1", "2", "3");
        ImmutableKey key2 = ImmutableKey.of("1", "2", "3");

        LoadingCache<ImmutableKey, Object> cacheLoader = CacheBuilder.newBuilder()
                                                                     .build(new CacheLoader<ImmutableKey, Object>() {
                                                                         @Override
                                                                         public Object load(ImmutableKey key) throws Exception {
                                                                             log.info("load obj.");
                                                                             return new Object();
                                                                         }
                                                                     });
        Object obj1 = cacheLoader.get(key);
        Object obj2 = cacheLoader.get(key2);

        log.info("obj1={},obj2={}", obj1, obj2);

    }

}
