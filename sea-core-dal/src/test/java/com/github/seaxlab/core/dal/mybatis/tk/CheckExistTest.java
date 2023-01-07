package com.github.seaxlab.core.dal.mybatis.tk;

import com.github.seaxlab.core.dal.mybatis.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/21
 * @since 1.0
 */
@Slf4j
public class CheckExistTest extends BaseSpringTest {

  @Resource
  private User1Mapper user1Mapper;

  @Test
  public void testCheckExist() throws Exception {
    List<User1> users = user1Mapper.selectAll();
    log.info("users={}", users);

    User1 user = new User1();
    user.setId(1L);
    boolean exist = user1Mapper.checkExist(user);
    log.info("exist={}", exist);

    user.setId(100L);
    exist = user1Mapper.checkExist(user);
    log.info("exist={}", exist);

    //user1Mapper.selectByExampleAndRowBounds()
  }
}
