package com.github.spy.sea.core.dal.mybatis.tk.mapper;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.provider.CheckExistProviderExt;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Example;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/6/13
 * @since 1.0
 */
@RegisterMapper
public interface CheckExistMapper<T> {

    @SelectProvider(type = CheckExistProviderExt.class, method = "dynamicSQL")
    boolean checkExist(T record);

    @SelectProvider(type = CheckExistProviderExt.class, method = "dynamicSQL")
    boolean checkExistByExample(Example example);
}
