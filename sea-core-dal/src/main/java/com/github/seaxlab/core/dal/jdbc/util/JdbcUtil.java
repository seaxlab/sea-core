package com.github.seaxlab.core.dal.jdbc.util;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.Precondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

/**
 * jdbc util
 *
 * @author spy
 * @version 1.0 2023/10/19
 * @since 1.0
 */
@Slf4j
public final class JdbcUtil {

  public static final String MYSQL_PREFIX = "jdbc:mysql://";
  public static final String ORACLE_PREFIX_THIN = "jdbc:oracle:thin:";
  public static final String POSTGRESQL_PREFIX_THIN = "jdbc:postgresql://";

  /**
   * get connection
   *
   * @param driver
   * @param url
   * @param username
   * @param password
   * @return
   */
  public static Connection getConnection(String driver, String url, String username, String password) {
    Properties properties = new Properties();
    properties.put("user", username);
    properties.put("password", password);
    return getConnection(driver, url, properties);
  }

  /**
   * get connection
   *
   * @param driver
   * @param url
   * @param properties
   * @return
   */
  public static Connection getConnection(String driver, String url, Properties properties) {
    if (properties == null) {
      properties = new Properties();
    }
    if (Objects.isNull(properties.get("connectTimeout"))) {
      properties.put("connectTimeout", "5000");
    }

    Connection connection = null;
    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(url, properties);
      log.info("get connection success");
    } catch (Exception e) {
      log.warn("fail to get connection", e);
    }
    return connection;
  }

  /**
   * 遍历 result set
   *
   * @param rs
   * @return
   */
  public static List<Map<String, Object>> toList(ResultSet rs) {
    List<Map<String, Object>> data = new ArrayList<>();

    try {
      ResultSetMetaData md = rs.getMetaData();
      int columns = md.getColumnCount();
      while (rs.next()) {
        Map<String, Object> row = new HashMap<>(columns);
        for (int i = 1; i <= columns; ++i) {
          row.put(md.getColumnName(i), rs.getObject(i));
        }
        data.add(row);
      }
    } catch (SQLException e) {
      log.warn("sql exception", e);
      throw new RuntimeException(e);
    }
    return data;
  }


  /**
   * get database name from connection url
   *
   * @param url
   * @return
   */
  public static String getDatabaseName(final String url) {
    Precondition.checkNotNull(url);
    //
    String dbName = "";
    //jdbc:mysql://127.0.0.1:3306/mylab?useSSL=false
    //jdbc:postgresql://10.20.1.231:5432/postgres?binaryTransfer=false&forceBinary=false&reWriteBatchedInserts=true
    if (url.startsWith(MYSQL_PREFIX) || url.startsWith(POSTGRESQL_PREFIX_THIN)) {
      if (url.indexOf("?") > 0) {
        dbName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
      } else {
        dbName = url.substring(url.lastIndexOf("/") + 1);
      }
      return dbName;
    } else if (url.startsWith(ORACLE_PREFIX_THIN)) {
      //格式1：jdbc:oracle:thin:@host:port:SID
      //格式2：jdbc:oracle:thin:@//host:port/service_name
      if (url.lastIndexOf("/") > 0) {
        dbName = url.substring(url.lastIndexOf("/") + 1);
      } else {
        dbName = url.substring(url.lastIndexOf(":") + 1);
      }
    } else {
      log.warn("unknown database url format[{}]", url);
      throw new BaseAppException("不支持的url格式");
    }


    return dbName;
  }


  /**
   * close prepared statement
   *
   * @param ps
   */
  public static void close(PreparedStatement ps) {
    if (Objects.isNull(ps)) {
      return;
    }
    try {
      ps.close();
    } catch (Exception e) {
      log.warn("PreparedStatement close fail", e);
    }
  }


  /**
   * close result set
   *
   * @param resultSet
   */
  public static void close(ResultSet resultSet) {
    if (Objects.isNull(resultSet)) {
      return;
    }
    try {
      resultSet.close();
    } catch (Exception e) {
      log.warn("result set close fail", e);
    }
  }

  /**
   * close connection
   *
   * @param connection
   */
  public static void close(Connection connection) {
    if (Objects.isNull(connection)) {
      return;
    }
    try {
      connection.close();
    } catch (Exception e) {
      log.warn("connection close fail", e);
    }
  }

}
