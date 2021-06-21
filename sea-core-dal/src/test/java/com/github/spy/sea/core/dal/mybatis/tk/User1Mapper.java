package com.github.spy.sea.core.dal.mybatis.tk;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.CheckExistMapper;
import com.github.spy.sea.core.dal.mybatis.tk.mapper.InsertOrUpdateMapper;
import com.github.spy.sea.core.dal.mybatis.tk.mapper.SelectMaxMapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/2/11
 * @since 1.0
 */
public interface User1Mapper extends
        BaseMapper<User1>,
        InsertOrUpdateMapper<User1>,
        SelectMaxMapper<User1>,
        CheckExistMapper<User1> {
}
