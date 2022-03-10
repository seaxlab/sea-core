package com.github.spy.sea.core.dal.mybatis.tk2.mybatis;

import com.github.spy.sea.core.dal.mybatis.tk2.BaseMybatisTest;
import com.github.spy.sea.core.dal.mybatis.tk2.domain.User;
import com.github.spy.sea.core.dal.mybatis.tk2.util.ExampleUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
public class ExampleTest extends BaseMybatisTest {

    @Before
    public void testUp() throws Exception {
        EntityHelper.initEntityNameMap(User.class, new Config());
        this.setUp();
    }

    @Test
    public void test16() throws Exception {
        Example example = new Example(User.class);

        ExampleUtil.setValueAll(example, "id", 1, "name", "cc");

        log.info("{}", example);
    }
}
