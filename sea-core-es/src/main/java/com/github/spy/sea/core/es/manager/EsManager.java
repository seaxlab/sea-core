package com.github.spy.sea.core.es.manager;

import com.github.spy.sea.core.es.dto.EsQueryDTO;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.model.KeyValuePair;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;

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

    /**
     * 销毁
     */
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
     * 批量删除索引
     *
     * @param indexNameList
     * @return
     */
    //没有批量删除索引接口
//    BaseResult deleteIndexByBulk(List<String> indexNameList);

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
    BaseResult insertDocByBulk2(String indexName, List<String> docJsonStrList);

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
     * 批量更新文档
     *
     * @param indexName
     * @param docMapList
     * @return
     */
    BaseResult updateDocByBulk(String indexName, List<KeyValuePair<String, Map<String, Object>>> docMapList);

    /**
     * 根据指定条件,更新一批文档
     *
     * @param indexName
     * @param query
     * @param document
     * @return
     */
    BaseResult updateDocByQuery(String indexName, QueryBuilder query, Map<String, Object> document);

    /**
     * 删除文档
     *
     * @param indexName
     * @param id
     * @return
     */
    BaseResult deleteDoc(String indexName, String id);

    /**
     * 批量删除
     *
     * @param indexName
     * @param idList
     * @return
     */
    BaseResult deleteDocByBulk(String indexName, List<String> idList);

    /**
     * 根据条件，删除一批文档
     *
     * @param indexName
     * @param queryBuilder
     * @return
     */
    BaseResult deleteDocByQuery(String indexName, QueryBuilder queryBuilder);

    /**
     * 复杂查询
     *
     * @param dto
     * @return
     */
    BaseResult query(EsQueryDTO dto);

}
