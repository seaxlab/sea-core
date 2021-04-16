package com.github.spy.sea.core.dal.mybatis;

import com.github.spy.sea.core.test.AbstractCoreSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
@ContextConfiguration("classpath:mybatis/spring.xml")
@Rollback(false)
public class BaseSpringTest extends AbstractCoreSpringTest {
}
