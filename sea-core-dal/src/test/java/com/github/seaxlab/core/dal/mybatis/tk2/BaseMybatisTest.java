package com.github.seaxlab.core.dal.mybatis.tk2;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.github.seaxlab.core.dal.mybatis.tk2.dao.UserMapper;
import com.github.seaxlab.core.test.AbstractCoreTest;
import com.github.seaxlab.core.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.After;
import org.junit.Before;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.sql.DataSource;
import java.io.Reader;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class BaseMybatisTest extends AbstractCoreTest {

  private SqlSessionFactory sf;

  @Before
  public void setUp() throws Exception {

    startMySQL();

    log.info("starting up myBatis tests");
    String resource = "mybatis/tk2/mybatis-config.xml";
    Reader reader = Resources.getResourceAsReader(resource);

    // 直接测试example时需要加上这行代码
    //EntityHelper.initEntityNameMap(User.class, new Config());

    sf = new SqlSessionFactoryBuilder().build(reader, "dev");

    //create user mapper
    SqlSession session = sf.openSession();
    try {
      //创建一个MapperHelper
      MapperHelper mapperHelper = new MapperHelper();
      //设置配置
      mapperHelper.setConfig(new Config());
      //配置完成后，执行下面的操作
      mapperHelper.processConfiguration(session.getConfiguration());
    } finally {
      session.close();
    }
  }

  @After
  public void tearDown() throws Exception {
    log.info("closing down myBatis tests");
  }


  protected SqlSession getSqlSession() {
    return sf.openSession();
  }


  private void startMySQL() throws Exception {
    DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
    configBuilder.setPort(3306);
    String userHome = PathUtil.getUserHome();
    configBuilder.setDataDir(userHome + "/db"); // just an example

    DB db = DB.newEmbeddedDB(configBuilder.build());

    db.start();
    db.createDB("mybatis", "admin", "admin");
    // 执行初始化脚本
    db.source("mybatis/tk2/mybatis.sql", "mybatis");
  }

  /**
   * start h2 db.
   */
  private void startH2() {
    DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:file:./testDB", "root", "root");

    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("test2", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration.addMapper(UserMapper.class);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    try (SqlSession session = sqlSessionFactory.openSession()) {
      UserMapper userMapper = session.getMapper(UserMapper.class);
      //这里能够执行findAll说明userMapper是一个实例，那一定是在getMapper中发生了实例化
      userMapper.queryAll().forEach(System.out::println);
    }
  }

}
