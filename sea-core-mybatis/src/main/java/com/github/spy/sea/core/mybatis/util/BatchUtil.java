package com.github.spy.sea.core.mybatis.util;

import com.github.spy.sea.core.util.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;
import tk.mybatis.mapper.common.base.update.UpdateByPrimaryKeySelectiveMapper;

import java.util.List;

/**
 * batch util
 *
 * @author spy
 * @version 1.0 2019-07-30
 * @since 1.0
 */
@Slf4j
public final class BatchUtil {

    private BatchUtil() {
    }

    /**
     * 一次批量插入大小
     */
    public static final Integer DB_BATCH_SIZE = 200;


    /**
     * 批量添加，必须开启事务<br/>
     * 重点：这段代码中开启了session，所以外层中必须开启事务
     *
     * @param sqlSessionFactory sqlSessionFactory
     * @param mapperClass       xxMapper
     * @param list              数据
     * @param <T>
     */
    public static <T extends InsertSelectiveMapper> void insertByBatch(SqlSessionFactory sqlSessionFactory,
                                                                       Class<T> mapperClass,
                                                                       List list) {
        if (ListUtil.isEmpty(list)) {
            log.warn("list is empty");
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("WARNING you MUST use @transactional in your biz service.");
        }

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        T mapper = sqlSession.getMapper(mapperClass);

        for (int i = 0; i < list.size(); i++) {
            mapper.insertSelective(list.get(i));

            if (i % DB_BATCH_SIZE == (DB_BATCH_SIZE - 1)) {
                sqlSession.flushStatements();
            }
        }
        sqlSession.flushStatements();
        log.info("add data by batch. list.size={}", list.size());
    }

    /**
     * 批量插入，必须开启事务<br/>
     * 重点：这段代码中开启了session，所以外层中必须开启事务
     *
     * @param sqlSessionFactory
     * @param mapperClass
     * @param list
     * @param <T>
     */
    public static <T extends InsertSelectiveMapper> void insert(SqlSessionFactory sqlSessionFactory,
                                                                Class<T> mapperClass,
                                                                List list) {
        insertByBatch(sqlSessionFactory, mapperClass, list);
    }


    /**
     * 批量更新，必须开启事务<br/>
     * 重点：这段代码中开启了session，所以外层中必须开启事务
     *
     * @param sqlSessionFactory
     * @param mapperClass
     * @param list
     * @param <T>
     */
    public static <T extends UpdateByPrimaryKeySelectiveMapper> void updateByPrimaryKeySelective(SqlSessionFactory sqlSessionFactory,
                                                                                                 Class<T> mapperClass,
                                                                                                 List list) {
        if (ListUtil.isEmpty(list)) {
            log.warn("list is empty");
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("WARNING you MUST use @transactional in your biz service.");
        }

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        T mapper = sqlSession.getMapper(mapperClass);

        for (int i = 0; i < list.size(); i++) {
            mapper.updateByPrimaryKeySelective(list.get(i));

            if (i % DB_BATCH_SIZE == (DB_BATCH_SIZE - 1)) {
                sqlSession.flushStatements();
            }
        }
        sqlSession.flushStatements();
        log.info("update data by batch. list.size={}", list.size());
    }

}
