package com.github.seaxlab.core.dal.mybatis.plus.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/8/7
 * @since 1.0
 */
@Slf4j
public class CheckExist extends AbstractMethod {

    private static final String METHOD_NAME = "checkExist";

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        // SELECT count(*)>0 FROM %s WHERE %s = #{val} limit 1
        String format = "<script>SELECT count(*)>0 FROM %s %s limit 1\n</script>";

        String sql = String.format(format, tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addSelectMappedStatementForTable(mapperClass, METHOD_NAME, sqlSource, tableInfo);
    }


    /**
     * 查询
     */
    protected MappedStatement addSelectMappedStatementForTable(Class<?> mapperClass, String id, SqlSource sqlSource,
                                                               TableInfo table) {
        String resultMap = table.getResultMap();
        if (null != resultMap) {
            /* 返回 resultMap 映射结果集 */
            return addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.SELECT, null,
                    resultMap, Boolean.class, new NoKeyGenerator(), null, null);
        } else {
            /* 普通查询 */
            return addSelectMappedStatementForOther(mapperClass, id, sqlSource, Boolean.class);
        }
    }
}
