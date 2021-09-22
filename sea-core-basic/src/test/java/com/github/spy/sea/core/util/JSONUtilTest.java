package com.github.spy.sea.core.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.spy.sea.core.BaseCoreTest;
import com.github.spy.sea.core.domain.User;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019/8/29
 * @since 1.0
 */
@Slf4j
public class JSONUtilTest extends BaseCoreTest {

    @Test
    public void run17() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("smith");

        String text = JSONUtil.toStr(user);
        log.info("text={}", text);
//        User userCopy = JSONUtil.toObj(text,User.class);

        User userCopy = JSONUtil.toObj(text, new TypeReference<User>() {
        }.getType());
        log.info("userCopy={}", userCopy);

    }


    @Test
    public void test37() throws Exception {
        log.info("{}", JSONUtil.toStr(""));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("empty", "true");
        log.info("{}", JSONUtil.toStr(jsonObj));
    }

    @Test
    public void testToResult() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("smith");

        BaseResult<User> userResult = BaseResult.success(user);
        String text = JSONUtil.toStr(userResult);

        BaseResult<User> result = JSONUtil.toResult(text, User.class);
        log.info("result={}", result);
    }

    @Test
    public void testToResultList() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(1L);
            user.setName("smith");
            users.add(user);
        }

        BaseResult<List<User>> userResult = BaseResult.success(users);
        String text = JSONUtil.toStr(userResult);

        BaseResult<List<User>> result = JSONUtil.toResultList(text, User.class);
        log.info("result={}", result);

    }

}
