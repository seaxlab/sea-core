package com.github.seaxlab.core.dal.mybatis.tk2;

import com.github.seaxlab.core.dal.mybatis.tk2.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/10
 * @since 1.0
 */
@Slf4j
public class SimpleTest extends BaseMybatisTest {
    private SqlSession session;

    @Before
    public void before() {
        session = getSqlSession();
    }

    @Test
    public void queryAll() {

        ArrayList users = (ArrayList) session.selectList("com.github.seaxlab.core.mybatis.domain.UserMappers.queryAll");
        log.info("users={}", users);
    }

    @Test
    public void queryById() {
        UserMapper mapper = session.getMapper(UserMapper.class);
        log.info("users={}", mapper.queryById(1L));
    }

    @After
    public void destroy() {
        session.close();
    }

}
