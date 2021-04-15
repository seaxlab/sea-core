package com.github.spy.sea.core.dal.mybatis;

import com.github.spy.sea.core.dal.context.HintContext;
import com.github.spy.sea.core.dal.mybatis.tk.User1;
import com.github.spy.sea.core.dal.mybatis.tk.User1Mapper;
import com.github.spy.sea.core.test.AbstractCoreSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/15
 * @since 1.0
 */
@Slf4j
@ContextConfiguration("classpath:mybatis/spring.xml")
@Rollback(false)
public class HintTest extends AbstractCoreSpringTest {

    /**
     * tk
     */
    @Autowired
    private User1Mapper user1Mapper;

    @Test
    public void testHint() throws Exception {
        HintContext.put("/*mycat:schema=test000*/");
        User1 user = user1Mapper.selectByPrimaryKey(1);
        log.info("user={}", user);
    }

}
