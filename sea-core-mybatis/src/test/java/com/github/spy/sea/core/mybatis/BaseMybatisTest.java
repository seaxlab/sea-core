package com.github.spy.sea.core.mybatis;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.github.spy.sea.core.config.ConfigurationFactory;
import com.github.spy.sea.core.mybatis.dao.UserMapper;
import com.github.spy.sea.core.test.AbstractCoreTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.util.ArrayList;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-28
 * @since 1.0
 */
@Slf4j
public class BaseMybatisTest extends AbstractCoreTest {

    @Test
    public void run16() throws Exception {
        log.info("{}", 1);
    }

    private SqlSessionFactory sf;


    private void startMySQL() throws Exception {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(3306);
        String userHome = ConfigurationFactory.getInstance().getString("user.home");
        configBuilder.setDataDir(userHome + "/db"); // just an example

        DB db = DB.newEmbeddedDB(configBuilder.build());

        db.start();
        db.createDB("mybatis", "admin", "admin");
//        db.source("mybatis.sql", "mybatis");
    }

    @Before
    public void setUp() throws Exception {

        startMySQL();

        log.info("starting up myBatis tests");
        String resource = "mybatis-config.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        sf = new SqlSessionFactoryBuilder().build(reader, "dev"); //we're using the 'testing'
    }

    @After
    public void tearDown() throws Exception {
        log.info("closing down myBatis tests");
    }


    @Test
    public void queryAll() {

        SqlSession session = sf.openSession();
        try {
            ArrayList users = (ArrayList) session.selectList("com.github.spy.sea.core.mybatis.domain.UserMappers.queryAll");
            log.info("users={}", users);
        } finally {
            session.close();
        }

    }

    @Test
    public void queryById() {

        SqlSession session = sf.openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            log.info("users={}", mapper.queryById(1L));
        } finally {
            session.close();
        }

    }
}
