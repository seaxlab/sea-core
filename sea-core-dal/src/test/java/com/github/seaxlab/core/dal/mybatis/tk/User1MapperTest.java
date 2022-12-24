package com.github.seaxlab.core.dal.mybatis.tk;

import com.github.seaxlab.core.dal.mybatis.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/6
 * @since 1.0
 */
@Slf4j
public class User1MapperTest extends BaseSpringTest {

  @Resource
  private User1Mapper user1Mapper;

  @Test
  public void test17() throws Exception {
    List<User1> data = user1Mapper.queryHistory(-10);
    log.info("data={}", data);
  }

  @Test
  public void testUpdate() throws Exception {
    User1 user = new User1();
    user.setId(1L);
    user.setName("abc2");
    user.setVersion(1);
    int count = user1Mapper.updateByPrimaryKeySelective(user);
    Assert.assertTrue(count > 0);
  }

  @Test
  public void testUpdateBlankField() throws Exception {
    User1 user = new User1();
    user.setId(1L);
    user.setName("");
    user.setVersion(3);
    int rowCount = user1Mapper.updateByPrimaryKeySelective(user);
    log.info("row count={}", rowCount);
  }

  @Test
  public void testUpdateBlankField2() throws Exception {
    User1 user = new User1();
    user.setName("");

    Example example = new Example(User1.class);
    Example.Criteria criteria = example.createCriteria();
    criteria.andEqualTo("id", 1L);

    int count = user1Mapper.updateByExampleSelective(user, example);
    log.info("count={}", count);
  }


}
