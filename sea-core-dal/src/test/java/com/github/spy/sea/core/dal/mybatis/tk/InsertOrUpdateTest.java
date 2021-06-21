package com.github.spy.sea.core.dal.mybatis.tk;

import com.github.spy.sea.core.dal.mybatis.BaseSpringTest;
import com.github.spy.sea.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@Slf4j
public class InsertOrUpdateTest extends BaseSpringTest {

    @Resource
    private User1Mapper user1Mapper;

    @Test
    public void testInsert() throws Exception {
        User1 user1 = new User1();
        user1.setName("abc");
        user1Mapper.insertSelective(user1);
    }

    @Test
    public void testInsertOrUpdate() throws Exception {
        List<User1> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String random = RandomUtil.numeric(6);
            log.info("random={}", random);
            User1 user1 = new User1();
            user1.setId(Long.valueOf(i));
            user1.setName("name" + random);
            user1.setCardNo(random);
            data.add(user1);
        }


        int dbCount = user1Mapper.insertOrUpdateSelective(data, new String[]{"name", "cardNo"}, new String[]{"name", "cardNo"});
        log.info("db count={}", dbCount);
    }
}
