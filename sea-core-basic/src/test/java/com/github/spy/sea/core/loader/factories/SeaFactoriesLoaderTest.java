package com.github.spy.sea.core.loader.factories;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.loader.IdGenerator;
import com.github.spy.sea.core.loader.SeaFactoriesLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/3/1
 * @since 1.0
 */
@Slf4j
public class SeaFactoriesLoaderTest extends BaseCoreTest {

    @Test
    public void test17() throws Exception {
        List<IdGenerator> list = SeaFactoriesLoader.loadFactories(IdGenerator.class, this.getClass().getClassLoader());
        log.info("id impl list={}", list);
    }
}
