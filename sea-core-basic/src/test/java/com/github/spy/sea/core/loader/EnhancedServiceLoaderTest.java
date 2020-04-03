package com.github.spy.sea.core.loader;

import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-19
 * @since 1.0
 */
@Slf4j
public class EnhancedServiceLoaderTest extends BaseCoreTest {


    @Test
    public void run18() throws Exception {

        List<Class> generators = EnhancedServiceLoader.getAllExtensionClass(IdGenerator.class);

        Assert.assertTrue(ListUtil.isNotEmpty(generators));

        // all instance
        List<IdGenerator> list = EnhancedServiceLoader.loadAll(IdGenerator.class);

        IdGenerator idGenerator = EnhancedServiceLoader.load(IdGenerator.class, "UUID");
        Assert.assertEquals(idGenerator.getId(), "0001");
    }

}
