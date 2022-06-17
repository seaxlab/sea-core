package com.github.seaxlab.core.dal.mybatis;

import com.github.seaxlab.core.dal.mybatis.plus.User2;
import com.github.seaxlab.core.dal.mybatis.plus.User2Mapper;
import com.github.seaxlab.core.dal.mybatis.tk.User1;
import com.github.seaxlab.core.dal.mybatis.tk.User1Mapper;
import com.github.seaxlab.core.test.AbstractCoreSpringTest;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
@Slf4j
@ContextConfiguration("classpath:mybatis/spring.xml")
@Rollback(false)
public class TkAndPlusTest extends AbstractCoreSpringTest {

    /**
     * tk
     */
    @Resource
    private User1Mapper user1Mapper;
    /**
     * plus
     */
    @Resource
    private User2Mapper user2Mapper;

    @Test
    public void queryTest() throws Exception {
        User1 user1 = user1Mapper.selectByPrimaryKey(1);
        log.info("user1={}", user1);

        User2 user2 = user2Mapper.selectById(1l);
        log.info("user2={}", user2);
    }

    //重点： 在同一个事务中
    @Test
    @Transactional
    public void addTest() throws Exception {
        String name = RandomUtil.alphabetic(6);
        User1 user1 = new User1();
        user1.setName(name);
        int dbRowCount = user1Mapper.insertSelective(user1);
        log.info("db row count={}", dbRowCount);


        User2 user2 = new User2();
        user2.setName(name);
        dbRowCount = user2Mapper.insert(user2);
        log.info("db row count={}", dbRowCount);

    }
}
