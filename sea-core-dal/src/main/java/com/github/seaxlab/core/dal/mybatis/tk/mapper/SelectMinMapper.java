package com.github.seaxlab.core.dal.mybatis.tk.mapper;

import com.github.seaxlab.core.dal.mybatis.tk.mapper.provider.SelectMinProviderExt;
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
public interface SelectMinMapper<T> {
//
//    /**
//     * 选择最大值对应的记录
//     *
//     * @param maxColumn 最大值列名
//     * @return
//     */
//    T selectMin(@Param("maxColumn") String maxColumn);
//
//    /**
//     * 选择最大值对应的记录
//     *
//     * @param maxColumn 最大值列名
//     * @return
//     */
//    List<T> selectMinList(@Param("maxColumn") String maxColumn);

    /**
     * 选择最大值对应的记录
     *
     * @param record    对象过滤
     * @param maxColumn 最大值列名
     * @return
     */
    @SelectProvider(type = SelectMinProviderExt.class, method = "dynamicSQL")
    T selectMin(@Param("record") T record, @Param("maxColumn") String maxColumn);

    /**
     * 选择最大值对应的记录列表
     *
     * @param record    对象过滤
     * @param maxColumn 最值列名
     * @return
     */
    @SelectProvider(type = SelectMinProviderExt.class, method = "dynamicSQL")
    List<T> selectMinList(@Param("record") T record, @Param("maxColumn") String maxColumn);
}
