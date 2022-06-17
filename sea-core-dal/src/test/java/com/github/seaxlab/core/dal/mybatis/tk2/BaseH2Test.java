package com.github.seaxlab.core.dal.mybatis.tk2;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/9
 * @since 1.0
 */
@Slf4j
public abstract class BaseH2Test {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public final void init() {
        try {
            Reader reader = getConfigFileAsReader();
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
            //配置通用 Mapper
            configMapperHelper();
            //执行初始化 SQL
            runSql(getSqlFileAsReader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 配置通用 Mapper
     */
    protected void configMapperHelper() {
        SqlSession session = getSqlSession();
        try {
            //创建一个MapperHelper
            MapperHelper mapperHelper = new MapperHelper();
            //设置配置
            mapperHelper.setConfig(getConfig());
            //配置完成后，执行下面的操作
            mapperHelper.processConfiguration(session.getConfiguration());
        } finally {
            session.close();
        }
    }

    /**
     * 执行 Sql
     *
     * @param reader
     */
    protected void runSql(Reader reader) {
        if (reader == null) {
            return;
        }
        SqlSession sqlSession = getSqlSession();
        try {
            Connection conn = sqlSession.getConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.runScript(reader);
            try {
                reader.close();
            } catch (IOException e) {
            }
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 获取 Mapper 配置
     *
     * @return
     */
    protected Config getConfig() {
        return new Config();
    }

    /**
     * 获取 mybatis 配置
     *
     * @return
     */
    protected Reader getConfigFileAsReader() throws IOException {
        URL url = BaseH2Test.class.getClassLoader().getResource("mybatis/tk2/mybatis-config.xml");
        return toReader(url);
    }

    ;

    /**
     * 获取初始化 sql
     *
     * @return
     */
    protected Reader getSqlFileAsReader() throws IOException {
        URL url = BaseH2Test.class.getClassLoader().getResource("mybatis/tk2/mybatis.sql");
        return toReader(url);
    }

    ;

    /**
     * 转为 Reader
     *
     * @param url
     * @return
     * @throws IOException
     */
    protected Reader toReader(URL url) throws IOException {
        return Resources.getUrlAsReader(url.toString());
    }

    /**
     * 获取Session
     *
     * @return
     */
    protected SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
}
