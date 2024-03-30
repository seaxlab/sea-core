package com.github.seaxlab.core.dal.jdbc.util;

import static com.github.seaxlab.core.test.util.TestUtil.getConfig;

import com.github.seaxlab.core.dal.BaseCoreDalTest;
import com.github.seaxlab.core.test.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/10/19
 * @since 1.0
 */
@Slf4j
public class JdbcUtilTest extends BaseCoreDalTest {

  @Test
  public void testConnectionAndToList() throws Exception {
    String url = getConfig("db1_url");
    String username = getConfig("db1_username");
    String pwd = getConfig("db1_pwd");
    String table = getConfig("db1_table");
    String database = JdbcUtil.getDatabaseName(url);
    Connection conn = JdbcUtil.getConnection("com.mysql.jdbc.Driver", url, username, pwd);

    String sql = "select ORDINAL_POSITION,COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_DEFAULT from information_schema.COLUMNS where TABLE_SCHEMA='%s' and TABLE_NAME='%s' order by ORDINAL_POSITION";
    PreparedStatement pstmt = conn.prepareStatement(String.format(sql, database, table));
    ResultSet rs = pstmt.executeQuery();
    try {
      List<Map<String, Object>> data = JdbcUtil.toList(rs);
      log.info("data={}", data);
    } finally {
      JdbcUtil.close(rs);
      JdbcUtil.close(pstmt);
      JdbcUtil.close(conn);
    }
  }


  @Test
  public void testGetDatabaseName() throws Exception {
    log.info("{}", JdbcUtil.getDatabaseName("jdbc:mysql://127.0.0.1:3306/mylab?useSSL=false"));
    log.info("{}", JdbcUtil.getDatabaseName("jdbc:mysql://127.0.0.1:3306/mylab"));
    log.info("{}", JdbcUtil.getDatabaseName("jdbc:oracle:thin:@host:port:SID"));
    log.info("{}", JdbcUtil.getDatabaseName("jdbc:oracle:thin:@//host:port/service_name"));
  }
}
