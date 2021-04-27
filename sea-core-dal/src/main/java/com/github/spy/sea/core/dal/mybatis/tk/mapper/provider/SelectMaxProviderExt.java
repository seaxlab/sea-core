package com.github.spy.sea.core.dal.mybatis.tk.mapper.provider;

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
public class SelectMaxProviderExt extends MapperTemplate {

    public SelectMaxProviderExt(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    public String selectMax(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        StringBuilder sql = new StringBuilder();

        return sql.toString();
    }

    public String selectMaxList(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);

        //SELECT
        //    *
        //FROM
        //    sys_user a,
        //    (SELECT
        //        MAX(USER_ID) AS 'MAX_USER_ID'
        //    FROM
        //        sys_user) b
        //WHERE
        //    a.USER_ID = b.MAX_USER_ID;

        StringBuilder sql = new StringBuilder();

        return sql.toString();
    }
}
