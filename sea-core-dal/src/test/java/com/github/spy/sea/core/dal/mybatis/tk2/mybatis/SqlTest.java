package com.github.spy.sea.core.dal.mybatis.tk2.mybatis;

import com.github.spy.sea.core.dal.mybatis.tk2.BaseMybatisTest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/12/18
 * @since 1.0
 */
@Slf4j
public class SqlTest extends BaseMybatisTest {

    @Data
    public static class Admin {
        private String name;
        private String email;

        public Admin(String name, String email) {
            this.email = email;
            this.name = name;
        }
    }

    @Test
    public void selectAdminLike() {
        Admin admin = new Admin("name", "aa@1.com");

        // {{ ... }} 表示初始化
        String sql = new SQL() {{
            SELECT("A.name,A.email,A.id");
            FROM("ADMIN A");
            if (admin.getName() != null) {
                WHERE("A.name = '" + admin.getName() + "'");
            }
            if (admin.getEmail() != null) {
                WHERE("A.email = " + admin.getEmail());
            }
        }}.toString();
        log.info("sql={}", sql);
    }

    @Test
    public void variableLengthArgumentOnIntoColumnsAndValues() {
        final String sql = new SQL() {{
            INSERT_INTO("TABLE_A")
                    .INTO_COLUMNS("a", "b")
                    .INTO_VALUES("#{a}")
                    .INTO_VALUES("#{b}");
        }}.toString();
        log.info("sql={}", sql);
        assertEquals("INSERT INTO TABLE_A\n (a, b)\nVALUES (#{a}, #{b})", sql);
    }
}
