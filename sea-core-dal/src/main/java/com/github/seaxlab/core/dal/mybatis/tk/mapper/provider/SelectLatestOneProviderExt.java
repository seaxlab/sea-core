package com.github.seaxlab.core.dal.mybatis.tk.mapper.provider;

import com.github.seaxlab.core.dal.mybatis.tk.util.DynamicSqlUtil;
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
public class SelectLatestOneProviderExt extends MapperTemplate {
    public SelectLatestOneProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    // select * from t_order where code='111' order by id desc limit 1;

    public String selectLatestOne(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();

        sql.append(DynamicSqlUtil.selectTableAColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(DynamicSqlUtil.whereTableRecordFilterColumns(entityClass, isNotEmpty(), false));
        // 这里必须使用record.xxx
//        sql.append(" where <if test=' record.age != null'> age=#{record.age}</if>");
//
        // 会有多个主键
//        Set<EntityColumn> columnSet = EntityHelper.getPKColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
//        for (EntityColumn column : columnSet) {
//            sql.append(" AND ").append(column.getColumnEqualsHolder(entityName));
//        }

        sql.append(" ORDER BY `" + DynamicSqlUtil.getOrderByProperty() + "` DESC LIMIT 1");

        return sql.toString();
    }

    public String selectLatestOneByExample(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);

        StringBuilder sql = new StringBuilder();
        sql.append(DynamicSqlUtil.selectTableAColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(DynamicSqlUtil.exampleWhereClause());
        sql.append(" ORDER BY " + DynamicSqlUtil.getOrderByProperty2() + " DESC LIMIT 1");

        return sql.toString();
    }
}
