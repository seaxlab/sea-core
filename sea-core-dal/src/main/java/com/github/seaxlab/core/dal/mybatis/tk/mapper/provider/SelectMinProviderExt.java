package com.github.seaxlab.core.dal.mybatis.tk.mapper.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/4/26
 * @since 1.0
 */
@Slf4j
public class SelectMinProviderExt extends MapperTemplate {

    public SelectMinProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    public String selectMin(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        StringBuilder sql = new StringBuilder();

        return sql.toString();
    }

    public String selectMinList(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        StringBuilder sql = new StringBuilder();

        return sql.toString();
    }
}
