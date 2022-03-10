package com.github.spy.sea.core.dal.mybatis.tk.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JSON字符串与实体对象互相转换
 * <p>
 * 用法一:
 * 入库：#{jsonDataField, typeHandler=com.xxx.JSONTypeHandler}
 * 出库：
 * <resultMap>
 * <result property="jsonDataField" column="json_data_field" javaType="com.xxx.MyClass" typeHandler="com.xxx.JSONTypeHandler"/>
 * </resultMap>
 * </p>
 * <p>
 * 用法二：
 * 1）在mybatis-config.xml中指定handler:
 * <code>
 * <typeHandlers>
 * <typeHandler handler="com.xxx.JSONTypeHandler" javaType="com.xxx.MyClass"/>
 * </typeHandlers>
 * </code>
 * </p>
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class JSONTypeHandler<T extends Object> extends BaseTypeHandler<T> {

    private Class<T> clazz;

    public JSONTypeHandler() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.clazz = (Class<T>) params[0];
    }
//
//    public JSONTypeHandler(Class<T> clazz) {
//        if (clazz == null) {
//            throw new IllegalArgumentException("Type handler argument [clazz] cannot be null");
//        }
//        this.clazz = clazz;
//    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJSONString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObject(rs.getString(columnName), clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex), clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex), clazz);
    }


    private String toJSONString(T object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            log.error("fail to json string", e);
            throw new RuntimeException(e);
        }
    }

    private T toObject(String content, Class<T> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return JSON.parseObject(content, clazz);
            } catch (Exception e) {
                log.error("fail to json string", e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
