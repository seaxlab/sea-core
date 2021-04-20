package com.github.spy.sea.core.dal.mybatis.tk.mapper;

import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * 聚合多种Mapper扩展
 *
 * @author spy
 * @version 1.0 2021/4/20
 * @since 1.0
 */
@RegisterMapper
public interface EnhancedMapper<T> extends InsertOrUpdateMapper<T> {
}
