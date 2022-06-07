package com.github.spy.sea.core.dal.mybatis.common.handler;

import com.alibaba.fastjson.JSON;
import com.github.spy.sea.core.util.SetUtil;
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
import java.util.Set;

/**
 * Set<T><=> JSON Array string.
 *
 * @author spy
 * @version 1.0 2020/11/9
 * @since 1.0
 */
@Slf4j
@MappedTypes({Set.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class SetJsonTypeHandler<T> extends BaseTypeHandler<Set<T>> {

    private Class clazz;

    public SetJsonTypeHandler() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.clazz = (Class<T>) params[0];
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<T> obj, JdbcType jdbcType) throws SQLException {
        if (obj == null) {
            obj = Collections.emptySet();
        }
        ps.setString(i, JSON.toJSONString(obj));
    }

    @Override
    public Set<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);

        return toSet(value);
    }


    @Override
    public Set<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return toSet(value);
    }


    @Override
    public Set<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return toSet(value);
    }

    private Set<T> toSet(String value) {
        return value == null ? Collections.emptySet() : SetUtil.toSet(JSON.parseArray(value, clazz));
    }

}
