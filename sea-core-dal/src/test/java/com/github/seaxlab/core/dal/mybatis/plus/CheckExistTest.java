package com.github.seaxlab.core.dal.mybatis.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/7
 * @since 1.0
 */
@Slf4j
public class CheckExistTest extends BasePlusTest {

    /**
     * plus
     */
    @Resource
    private User2Mapper user2Mapper;

    @Test
    public void testSimple() throws Exception {
        //boolean exist = user2Mapper.checkExist(User2::getId, 1);
        //log.info("exist={}", exist);
    }

    @Test
    public void testWrapper() throws Exception {
        User2 user2 = new User2();
        QueryWrapper<User2> wrapper = Wrappers.query(user2);
        wrapper.eq("id", 100);

        boolean exist = user2Mapper.checkExist(wrapper);
        log.info("exist={}", exist);
    }
}
