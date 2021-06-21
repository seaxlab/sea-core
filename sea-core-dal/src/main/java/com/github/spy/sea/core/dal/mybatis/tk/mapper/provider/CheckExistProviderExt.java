package com.github.spy.sea.core.dal.mybatis.tk.mapper.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * 如果只判断是否存在只要一条即可
 * select 1 from table where column1='xx' limit 1
 *
 * @author spy
 * @version 1.0 2021/6/13
 * @since 1.0
 */
@Slf4j
public class CheckExistProviderExt extends MapperTemplate {
    public CheckExistProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String checkExist(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        // select count(*)>0 from tech_sys_corp where corp_code='3702011121' limit 1;

        sql.append("select count(*) > 0 ");
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        sql.append(" limit 1");

        return sql.toString();
    }

    public String checkExistByExample(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) > 0 ");
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(" limit 1");

        return sql.toString();
    }
}
