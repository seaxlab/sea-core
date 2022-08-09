package com.github.seaxlab.core.dal.mybatis.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Long and Date type handler
 *
 * @author spy
 * @version 1.0 2022/8/8
 * @since 1.0
 */
@Slf4j
@MappedTypes({String.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class LongAndDate2TypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date date, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, date == null ? new Date().getTime() : date.getTime());
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long value = rs.getLong(columnName);
        if (value == null || value <= 0) {
            // value=-1 or 0, no meaning.
            return null;
        }

        return new Date(value);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long value = rs.getLong(columnIndex);
        if (value == null || value <= 0) {
            return null;
        }

        return new Date(value);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long value = cs.getLong(columnIndex);
        if (value == null || value <= 0) {
            return null;
        }

        return new Date(value);
    }

}
