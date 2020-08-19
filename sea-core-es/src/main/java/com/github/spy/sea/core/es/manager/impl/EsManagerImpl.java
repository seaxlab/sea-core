package com.github.spy.sea.core.es.manager.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.es.dto.EsQueryDTO;
import com.github.spy.sea.core.es.manager.EsManager;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.model.KeyValuePair;
import com.github.spy.sea.core.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/13
 * @since 1.0
 */
@Slf4j
public class EsManagerImpl implements EsManager {

    private RestHighLevelClient client;

    @Override
    public void init(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public void destroy() {
        if (this.client != null) {
            try {
                this.client.close();
            } catch (Exception e) {
                log.error("fail to close es", e);
            }
        }
    }

    @Override
    public BaseResult createIndex(String indexName) {
        boolean exist = checkIndexExist(indexName);
        if (exist) {
            log.info("indexName={} has exist", indexName);
            return BaseResult.success();
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            log.info("response={}", createIndexResponse);

            return BaseResult.success(createIndexResponse);
        } catch (Exception e) {
            log.error("fail to create es index", e);
        }
        return BaseResult.fail();
    }

    @Override
    public boolean checkIndexExist(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        getIndexRequest.humanReadable(true);
        try {
            return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("fail to check index exist", e);
        }
        return false;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (IOException e) {
            log.error("fail to delete index={}", JSON.toJSONString(indexName), e);
        }
        return acknowledged;
    }

    // 没有批量删除索引接口
//    public BaseResult deleteIndexByBulk(List<String> indexNameList) {
//        BulkRequest bulkRequest = new BulkRequest();
//        indexNameList.forEach(item -> {
//            DeleteIndexRequest request = new DeleteIndexRequest(item);
//            bulkRequest.add(request);
//        });
//        //同步
//        try {
//            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
//            log.info("resp={}", JSONObject.toJSON(response));
//
//            return BaseResult.success();
//        } catch (IOException e) {
//            log.info("fail to bulk insert into es.", e);
//        }
//
//        return BaseResult.fail();
//    }


    @Override
    public BaseResult createMapping(String indexName, XContentBuilder builder) {
        try {
            PutMappingRequest request = new PutMappingRequest(indexName);
            request.source(builder);

            AcknowledgedResponse resp = client.indices().putMapping(request, RequestOptions.DEFAULT);

            return BaseResult.success(resp.isAcknowledged());
        } catch (IOException e) {
            log.error("fail to create mapping", e);
        }
        return BaseResult.failMsg("create mapping error");
    }

    @Override
    public BaseResult insertDoc(String indexName, Map<String, Object> docMap) {
        return insertDoc(indexName, JSONObject.toJSONString(docMap));
    }

    @Override
    public BaseResult insertDoc(String indexName, String docJsonStr) {
        IndexRequest indexRequest = new IndexRequest(indexName);
        indexRequest.source(docJsonStr, XContentType.JSON);
        try {
            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            if (response != null) {
                ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    log.info("shardInfo={}", JSONObject.toJSON(shardInfo));
                }
                // 如果有分片副本失败，可以获得失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        log.info("副本失败原因,reason={}", reason);
                    }
                }
            }
            return BaseResult.success();
        } catch (IOException e) {
            log.error("fail to insert into es", e);
        }
        return BaseResult.fail();
    }

    @Override
    public BaseResult insertDocByBulk(String indexName, List<Map<String, Object>> docMapList) {
        BulkRequest bulkRequest = new BulkRequest();
        docMapList.forEach(each -> {
            IndexRequest indexRequest = new IndexRequest(indexName).source(each, XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        //同步
        try {
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));

            checkBulkResponse(response);

            return BaseResult.success();
        } catch (IOException e) {
            log.error("fail to bulk insert into es.", e);
        }

        return BaseResult.fail();
    }

    @Override
    public BaseResult insertDocByBulk2(String indexName, List<String> docJsonStrList) {
        BulkRequest bulkRequest = new BulkRequest();
        docJsonStrList.forEach(each -> {
            IndexRequest indexRequest = new IndexRequest(indexName).source(each, XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        //同步
        try {
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));

            checkBulkResponse(response);

            return BaseResult.success();
        } catch (IOException e) {
            log.error("fail to insert doc by bulk2", e);
        }

        return BaseResult.fail();
    }

    @Override
    public BaseResult updateDoc(String indexName, String id, Map<String, Object> docMap) {
        UpdateRequest updateRequest = new UpdateRequest(indexName, id);
        updateRequest.doc(docMap);
        try {
            UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));
            return BaseResult.success();
        } catch (IOException e) {
            log.error("fail to update doc", e);
        }

        return BaseResult.fail();
    }

    @Override
    public BaseResult updateDocByBulk(String indexName, List<KeyValuePair<String, Map<String, Object>>> docMapList) {

        BulkRequest bulkRequest = new BulkRequest();
        docMapList.forEach(item -> {
            UpdateRequest updateRequest = new UpdateRequest(indexName, item.getKey());
            updateRequest.doc(item.getValue());
            bulkRequest.add(updateRequest);
        });

        try {
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));

            checkBulkResponse(response);

            return BaseResult.success(response);
        } catch (IOException e) {
            log.error("fail to update doc by bulk", e);
        }

        return BaseResult.fail();
    }

    @Override
    public BaseResult updateDocByQuery(String indexName, QueryBuilder query, Map<String, Object> document) {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(indexName);
        updateByQueryRequest.setQuery(query);
        StringBuilder script = new StringBuilder();
        Set<String> keys = document.keySet();
        for (String key : keys) {
            String appendValue = "";
            Object value = document.get(key);
            if (value instanceof Number) {
                appendValue = value.toString();
            } else if (value instanceof String) {
                appendValue = "'" + value.toString() + "'";
            } else if (value instanceof List) {
                appendValue = JSONUtil.toStr(value);
            } else {
                appendValue = value.toString();
            }
            script.append("ctx._source.").append(key).append("=").append(appendValue).append(";");
        }
        updateByQueryRequest.setScript(new Script(script.toString()));

        try {
            BulkByScrollResponse resp = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSON.toJSONString(resp));
            return BaseResult.success(resp);
        } catch (Exception e) {
            log.error("fail to update by query", e);
        }

        return BaseResult.failMsg("");
    }

    @Override
    public BaseResult deleteDoc(String indexName, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName);
        deleteRequest.id(id);
        try {
            DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));
            return BaseResult.success(response);
        } catch (IOException e) {
            log.error("fail to delete doc", e);
        }
        return BaseResult.fail();
    }


    @Override
    public BaseResult deleteDocByBulk(String indexName, List<String> idList) {
        BulkRequest bulkRequest = new BulkRequest();
        idList.forEach(item -> {
            DeleteRequest deleteRequest = new DeleteRequest(indexName);
            deleteRequest.id(item);
            bulkRequest.add(deleteRequest);
        });
        //同步
        try {
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSONObject.toJSON(response));

            checkBulkResponse(response);

            return BaseResult.success(response);
        } catch (IOException e) {
            log.error("fail to delete doc by bulk", e);
        }

        return BaseResult.fail();
    }

    @Override
    public BaseResult deleteDocByQuery(String indexName, QueryBuilder queryBuilder) {
        DeleteByQueryRequest queryRequest = new DeleteByQueryRequest(indexName);
        queryRequest.setQuery(queryBuilder);

        try {
            BulkByScrollResponse resp = client.deleteByQuery(queryRequest, RequestOptions.DEFAULT);
            log.info("resp={}", JSON.toJSONString(resp));
            return BaseResult.success(resp);
        } catch (Exception e) {
            log.error("fail to delete by query", e);
        }

        return BaseResult.failMsg("fail to delete by query.");
    }

    @Override
    public BaseResult query(EsQueryDTO dto) {
        SearchRequest searchRequest = new SearchRequest(dto.getIndexName());
        searchRequest.source(dto.getSearchSourceBuilder());
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();
            JSONArray jsonArray = new JSONArray();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                JSONObject jsonObject = JSON.parseObject(sourceAsString);
                jsonArray.add(jsonObject);
            }

            return BaseResult.success(jsonArray);
        } catch (IOException e) {
            log.error("fail to query es", e);
        }

        return BaseResult.fail();
    }

    private void checkBulkResponse(BulkResponse response) {
        if (response != null && response.hasFailures()) {
            log.error("es bulk response msg={}", response.buildFailureMessage());
        }
    }
}
