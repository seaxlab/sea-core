package com.github.spy.sea.core.dal.mybatis.tk;

import com.github.spy.sea.core.dal.mybatis.BaseSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class SelectMaxTest extends BaseSpringTest {

    @Autowired
    private User1Mapper user1Mapper;

    @Test
    public void testSelectMax() throws Exception {
        User1 user1 = new User1();
        user1.setName("abc");
        User1 max = user1Mapper.selectMax(user1, "id");
        log.info("max={}", max);
    }

    @Test
    public void testSelectMaxList() throws Exception {
        User1 user1 = new User1();
        user1.setName("abc");
        List<User1> data = user1Mapper.selectMaxList(user1, "age");
        log.info("max list={}", data);
    }
}
