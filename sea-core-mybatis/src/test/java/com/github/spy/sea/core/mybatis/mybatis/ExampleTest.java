package com.github.spy.sea.core.mybatis.mybatis;

import com.github.spy.sea.core.mybatis.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
public class ExampleTest {

    @Test
    public void test16() throws Exception {
        Example example = new Example(User.class);
    }
}
