package com.github.spy.sea.core.dal.mybatis.tk.mapper;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.provider.SelectLatestOneProviderExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.entity.Example;

/**
 * select latest one
 *
 * @author spy
 * @version 1.0 2021/10/21
 * @since 1.0
 */
@RegisterMapper
public interface SelectLatestOneMapper<T> {

    /**
     * select latest one
     *
     * @param record          entity
     * @param orderByProperty 这里是属性名称不是字段名称
     * @return
     */
    @SelectProvider(type = SelectLatestOneProviderExt.class, method = "dynamicSQL")
    T selectLatestOne(@Param("record") T record, @Param("orderByProperty") String orderByProperty);

    /**
     * select latest one by example
     *
     * @param example
     * @param orderByProperty 这里是属性名称不是表字段名称
     * @return
     */
    @SelectProvider(type = SelectLatestOneProviderExt.class, method = "dynamicSQL")
    T selectLatestOneByExample(@Param("example") Example example, @Param("orderByProperty") String orderByProperty);
}
