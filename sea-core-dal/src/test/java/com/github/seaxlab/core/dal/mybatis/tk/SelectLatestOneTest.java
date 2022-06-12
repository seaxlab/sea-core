package com.github.seaxlab.core.dal.mybatis.tk;

import com.github.seaxlab.core.dal.mybatis.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/21
 * @since 1.0
 */
@Slf4j
public class SelectLatestOneTest extends BaseSpringTest {

    @Resource
    private User1Mapper user1Mapper;

    @Test
    public void testSelectLatestOne() throws Exception {
        User1 record = new User1();
        record.setAge(12);
        User1 user = user1Mapper.selectLatestOne(record, "id");
        log.info("user={}", user);
    }

    @Test
    public void testSelectLatestOne2() throws Exception {
        User1 record = new User1();
        record.setAge(12);
        User1 user = user1Mapper.selectLatestOne(record, "createTime");
        log.info("user={}", user);
    }

    @Test
    public void testSelectLatestOneByExample() throws Exception {
        Example example = new Example(User1.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("age", 12);

        User1 user = user1Mapper.selectLatestOneByExample(example, "id");
        log.info("user={}", user);
    }

    @Test
    public void testSelectLatestOneByExample2() throws Exception {
        Example example = new Example(User1.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("age", 12);

        User1 user = user1Mapper.selectLatestOneByExample(example, "createTime");
        log.info("user={}", user);
    }
}
