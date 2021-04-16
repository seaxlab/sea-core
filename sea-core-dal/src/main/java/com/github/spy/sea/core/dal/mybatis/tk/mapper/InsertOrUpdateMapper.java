package com.github.spy.sea.core.dal.mybatis.tk.mapper;

import com.github.spy.sea.core.dal.mybatis.tk.mapper.provider.InsertOrUpdateProviderExt;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * insert or update.
 *
 * @author spy
 * @version 1.0 2021/4/16
 * @since 1.0
 */
@RegisterMapper
public interface InsertOrUpdateMapper<T> {

    /**
     * insert or update data insql
     * 重点：必须在mysql层指定唯一键,例如主键或者其他 unique key
     *
     * @param records       待插入数据
     * @param insertColumns 新增的字段
     * @param updateColumns 唯一键存在时更新列
     * @return
     */
    @InsertProvider(type = InsertOrUpdateProviderExt.class, method = "dynamicSQL")
    int insertOrUpdateSelective(@Param("records") List<T> records,
                                @Param("insertColumns") String[] insertColumns,
                                @Param("updateColumns") String[] updateColumns);
}
