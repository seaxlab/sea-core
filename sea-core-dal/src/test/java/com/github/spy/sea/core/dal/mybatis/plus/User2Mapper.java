package com.github.spy.sea.core.dal.mybatis.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
public interface User2Mapper extends BaseMapper<User2> {
    Integer selectMaxAge();
}
