package com.github.seaxlab.core.dal.mybatis.plus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.seaxlab.core.dal.mybatis.plus.UserTypeEnum;
import com.github.seaxlab.core.dal.mybatis.plus.entity.User2;
import com.github.seaxlab.core.dal.mybatis.plus.util.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/8
 * @since 1.0
 */
@Slf4j
public class WrapperUtilTest {

  @Test
  public void test16() throws Exception {
    LambdaQueryWrapper<User2> wrapper = Wrappers.lambdaQuery();

    WrapperUtil.set(wrapper, User2::getCode, "12");
  }

  @Test
  public void testIBaseEnum() throws Exception {
    LambdaQueryWrapper<User2> wrapper = Wrappers.lambdaQuery();
    WrapperUtil.set(wrapper, User2::getUserType, UserTypeEnum.V1);

    log.info("wrapper={}", wrapper.toString());

  }

}
