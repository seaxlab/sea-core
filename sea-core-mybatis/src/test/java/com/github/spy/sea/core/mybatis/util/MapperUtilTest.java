package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.mybatis.BaseMybatisTest;
import com.github.spy.sea.core.mybatis.dao.UserMapper;
import com.github.spy.sea.core.mybatis.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/9
 * @since 1.0
 */
@Slf4j
public class MapperUtilTest extends BaseMybatisTest {

    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void before() {
        sqlSession = getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }


    @Test
    public void testUpdateByVersion() throws Exception {

        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(1L);
        user.setVersion(1);
        users.add(user);

        user = new User();
        user.setId(2L);
        user.setVersion(1);
        users.add(user);

        User record = new User();
        record.setName("---");

        Example example = new Example(User.class);

        boolean sucFlag = MapperUtil.updateByVersion(userMapper, record, users);
        log.info("suc flag={}", sucFlag);

    }

    @After
    public void after() {
        sqlSession.close();
    }

}
