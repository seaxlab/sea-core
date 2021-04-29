package com.github.spy.sea.core.dal.mybatis.tk.mapper;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.provider.SelectMaxProviderExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/26
 * @since 1.0
 */
//@RegisterMapper
public interface SelectMaxMapper<T> {

//    /**
//     * 选择最大值对应的记录
//     *
//     * @param maxColumn 最大值列名
//     * @return
//     */
//    @SelectProvider(type = SelectMaxProviderExt.class, method = "dynamicSQL")
//    T selectMax(@Param("maxColumn") String maxColumn);
//
//    /**
//     * 选择最大值对应的记录
//     *
//     * @param maxColumn 最大值列名
//     * @return
//     */
//    @SelectProvider(type = SelectMaxProviderExt.class, method = "dynamicSQL")
//    List<T> selectMaxList(@Param("maxColumn") String maxColumn);

    // 正常情况下过滤条件肯定是有的

    /**
     * 选择最大值对应的记录
     *
     * @param record    对象过滤
     * @param maxColumn 最大值列名
     * @return
     */
    @SelectProvider(type = SelectMaxProviderExt.class, method = "dynamicSQL")
    T selectMax(@Param("record") T record, @Param("maxColumn") String maxColumn);

    /**
     * 选择最大值对应的记录列表
     *
     * @param record    对象过滤
     * @param maxColumn 最值列名
     * @return
     */
    @SelectProvider(type = SelectMaxProviderExt.class, method = "dynamicSQL")
    List<T> selectMaxList(@Param("record") T record, @Param("maxColumn") String maxColumn);
}
