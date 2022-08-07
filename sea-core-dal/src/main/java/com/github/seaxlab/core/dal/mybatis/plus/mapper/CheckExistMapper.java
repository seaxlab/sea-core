package com.github.seaxlab.core.dal.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.seaxlab.core.dal.mybatis.plus.mapper.provider.CheckExistProviderExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * check exist mappers
 *
 * @author spy
 * @version 1.0 2022/8/6
 * @since 1.0
 */
public interface CheckExistMapper<E> {
    /**
     * check exist one field
     *
     * @param column
     * @param val
     * @return
     */
    @SelectProvider(type = CheckExistProviderExt.class, method = "checkExistByFun1")
    boolean checkExist(SFunction<E, ?> column, @Param("val") Object val);

    /**
     * check exist two field
     *
     * @param column1
     * @param val1
     * @param column2
     * @param val2
     * @return
     */
    @SelectProvider(type = CheckExistProviderExt.class, method = "checkExistByFun2")
    boolean checkExist2(SFunction<E, ?> column1, @Param("val1") Object val1, SFunction<E, ?> column2, @Param("val2") Object val2);

}
