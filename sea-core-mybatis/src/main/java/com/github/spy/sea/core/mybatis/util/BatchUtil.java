package com.github.spy.sea.core.mybatis.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;

import java.util.List;

/**
 * 模块
 *
 * @author spy
 * @version 1.0 2019-07-30
 * @since 1.0
 */
@Slf4j
public class BatchUtil {

    private BatchUtil() {
    }

    /**
     * 一次批量插入大小
     */
    public static final Integer DB_BATCH_SIZE = 200;


    /**
     * 批量添加
     *
     * @param sqlSessionFactory sqlSessionFactory
     * @param mapperClass       xxMapper
     * @param list              数据
     * @param <T>
     */
    public static <T extends InsertSelectiveMapper> void insertByBatch(SqlSessionFactory sqlSessionFactory,
                                                                       Class<T> mapperClass,
                                                                       List list) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        T mapper = sqlSession.getMapper(mapperClass);

        for (int i = 0; i < list.size(); i++) {
            mapper.insertSelective(list.get(i));

            if (i % DB_BATCH_SIZE == (DB_BATCH_SIZE - 1)) {
                sqlSession.flushStatements();
            }
        }
        sqlSession.flushStatements();
        log.info("add data by batch.");
    }

}
