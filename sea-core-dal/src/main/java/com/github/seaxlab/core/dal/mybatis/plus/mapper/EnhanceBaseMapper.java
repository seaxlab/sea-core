package com.github.seaxlab.core.dal.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * enhance base mapper
 *
 * @author spy
 * @version 1.0 2022/8/7
 * @since 1.0
 */
public interface EnhanceBaseMapper<T> extends BaseMapper<T> {

    /**
     * check exist by wrapper
     *
     * @param wrapper
     * @return
     */
    boolean checkExist(@Param(Constants.WRAPPER) Wrapper<T> wrapper);

}
