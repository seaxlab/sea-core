package com.github.seaxlab.core.dal.mybatis.tk2.util;

import com.github.seaxlab.core.dal.mybatis.tk.util.MapperUtil;
import com.github.seaxlab.core.dal.mybatis.tk2.BaseMybatisTest;
import com.github.seaxlab.core.dal.mybatis.tk2.dao.UserMapper;
import com.github.seaxlab.core.dal.mybatis.tk2.domain.User;
import com.github.seaxlab.core.model.PageInfo;
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
    public void testUpdateBlankField() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("");
        int rowCount = userMapper.updateByPrimaryKeySelective(user);
        log.info("row count={}", rowCount);
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

    @Test
    public void testToPage() throws Exception {
        Example example = new Example(User.class);
        PageInfo pageInfo = PageInfo.of(1, 2);
        List<User> users = userMapper.selectByExampleAndRowBounds(example, MapperUtil.toPage(pageInfo));

        log.info("users={}", users);
    }

    @After
    public void after() {
        sqlSession.close();
    }

}
