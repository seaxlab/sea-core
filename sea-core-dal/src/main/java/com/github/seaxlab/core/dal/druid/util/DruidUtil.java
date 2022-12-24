package com.github.seaxlab.core.dal.druid.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.github.seaxlab.core.dal.druid.model.DataBaseInfo;
import com.github.seaxlab.core.util.MapUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

/**
 * druid util
 *
 * @author spy
 * @version 1.0 2021/4/27
 * @since 1.0
 */
@Slf4j
public final class DruidUtil {

  /**
   * 获取指定的数据源
   *
   * @param dsName
   * @return
   */
  public static DruidDataSource getDataSource(String dsName) {
    DruidDataSource ds = null;
    for (DruidDataSource datasource : DruidDataSourceStatManager.getDruidDataSourceInstances()) {
      if (dsName.equals(datasource.getName())) {
        ds = datasource;
        break;
      }
    }
    return ds;
  }

  /**
   * 获取指定数据源的统计信息
   *
   * @param dsName
   * @return
   */
  public static Map<String, Object> getDataSourceStat(String dsName) {
    DruidDataSource ds = getDataSource(dsName);
    return ds != null ? ds.getStatData() : MapUtil.empty();
  }


  /**
   * 创建数据库连接池
   *
   * @param database
   * @return
   * @throws SQLException
   */
  public static DruidDataSource createDataSource(DataBaseInfo database) {

    DruidDataSource dataSource = null;
    String dsName = database.getId();
    if ((dataSource = getDataSource(dsName)) == null) {
      dataSource = new DruidDataSource();
      dataSource.setName(dsName);
      dataSource.setUrl(database.getUrl());
      dataSource.setDriverClassName(database.getDriver());
      dataSource.setUsername(database.getUsername());
      dataSource.setPassword(database.getPassword());

      // TODO 暂时写死后期优化
      dataSource.setInitialSize(5);
      dataSource.setMinIdle(5);
      dataSource.setMaxActive(30);
      dataSource.setMaxWait(6000);
      dataSource.setMinEvictableIdleTimeMillis(300000);
      dataSource.setRemoveAbandoned(false);//超过时间限制是否回收
      dataSource.setRemoveAbandonedTimeout(180);//超过时间限制多长
      dataSource.setTimeBetweenEvictionRunsMillis(60000);//配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      dataSource.setTestWhileIdle(true);
      dataSource.setTestOnBorrow(false);
      dataSource.setTestOnReturn(false);
      dataSource.setPoolPreparedStatements(true);
      dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
      dataSource.setValidationQuery("SELECT 1 from dual");
      dataSource.setTimeBetweenLogStatsMillis(3600000);
      dataSource.addConnectionProperty("remarksReporting", "true");

      //sql中有中文会报错，所以在此注释掉  stat监控统计用   wall防sql注入  log4j日志
      //dataSource.setFilters("stat,wall");
      //dataSource.setFilters("stat");
    }
    return dataSource;
  }

  /**
   * 关闭数据库连接池
   *
   * @param dsName
   */
  public static void closeDataSource(String dsName) {
    getDataSource(dsName).close();
  }

//    Connection connection = dataSource.getConnection();
//    Statement statement = connection.createStatement();
//    ResultSet result = statement.executeQuery("SELECT 1 from dual");
//     ...biz
//    result.close();
//    statement.close();
//    connection.close();

}
