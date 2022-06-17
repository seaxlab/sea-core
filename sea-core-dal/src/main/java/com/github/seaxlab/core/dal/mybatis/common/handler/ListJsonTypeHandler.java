package com.github.seaxlab.core.dal.mybatis.common.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * List<T><=> JSON Array string.
 * <p>
 * at MappedTypes({List.class})
 * at MappedJdbcTypes({JdbcType.VARCHAR})
 * 引用对象上可引用，也可不引用
 * </p>
 *
 * @author spy
 * @version 1.0 2020/11/9
 * @since 1.0
 */
@Slf4j
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ListJsonTypeHandler<T> extends BaseTypeHandler<List<T>> {

    private Class clazz;

    public ListJsonTypeHandler() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.clazz = (Class<T>) params[0];
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> obj, JdbcType jdbcType) throws SQLException {
        if (obj == null) {
            obj = Collections.emptyList();
        }
        ps.setString(i, JSON.toJSONString(obj));
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return value == null ? Collections.emptyList() : JSON.parseArray(value, clazz);
    }


    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return value == null ? Collections.emptyList() : JSON.parseArray(value, clazz);
    }


    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return value == null ? Collections.emptyList() : JSON.parseArray(value, clazz);
    }

}
