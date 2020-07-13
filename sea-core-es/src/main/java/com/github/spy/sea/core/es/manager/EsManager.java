package com.github.spy.sea.core.es.manager;

import com.github.spy.sea.core.es.dto.EsQueryDTO;
import com.github.spy.sea.core.model.BaseResult;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;
import java.util.Map;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/13
 * @since 1.0
 */
public interface EsManager {

    /**
     * 初始化
     */
    void init(RestHighLevelClient client);

    void destroy();

    /**
     * 创建索引
     *
     * @param indexName
     * @return
     */
    BaseResult createIndex(String indexName);

    /**
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    boolean checkIndexExist(String indexName);

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     */
    boolean deleteIndex(String indexName);

    /**
     * 插入文档
     *
     * @param indexName
     * @param docMap
     * @return
     */
    BaseResult insertDoc(String indexName, Map<String, Object> docMap);

    /**
     * 插入文档
     *
     * @param indexName
     * @param docJsonStr
     * @return
     */
    BaseResult insertDoc(String indexName, String docJsonStr);

    /**
     * 批量插入文档
     *
     * @param indexName
     * @param docMapList
     * @return
     */
    BaseResult insertDocByBulk(String indexName, List<Map<String, Object>> docMapList);

    /**
     * 批量插入文档
     *
     * @param indexName
     * @param docJsonStrList
     * @return
     */
    BaseResult insertDocByBuck(String indexName, List<String> docJsonStrList);

    /**
     * 更新文档
     *
     * @param indexName
     * @param id
     * @param docMap
     * @return
     */
    BaseResult updateDoc(String indexName, String id, Map<String, Object> docMap);

    /**
     * 删除文档
     *
     * @param indexName
     * @param id
     * @return
     */
    boolean deleteDoc(String indexName, String id);


    BaseResult query(EsQueryDTO dto);

}
