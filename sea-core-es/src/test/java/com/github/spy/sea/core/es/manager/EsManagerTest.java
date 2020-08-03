package com.github.spy.sea.core.es.manager;

import com.alibaba.fastjson.JSONArray;
import com.github.spy.sea.core.es.AbstractEsTest;
import com.github.spy.sea.core.es.dto.EsQueryDTO;
import com.github.spy.sea.core.es.manager.impl.EsManagerImpl;
import com.github.spy.sea.core.model.BaseResult;
import com.github.spy.sea.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/13
 * @since 1.0
 */
@Slf4j
public class EsManagerTest extends AbstractEsTest {

    EsManager esManager;
    String indexName = "test-spy-doc";

    @Before
    public void before() {
        super.before();

        esManager = new EsManagerImpl();
        esManager.init(client);
    }

    @Test
    public void queryMinValueTest() throws Exception {


        String indexName = "jaeger-span-2020-07-30";

        //creating search request
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1);

        AggregationBuilder aggBuilder = AggregationBuilders.min("min_start_time")
                                                           .field("startTime");

        searchSourceBuilder.aggregation(aggBuilder);

        // 排序
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("startTime");
        fieldSortBuilder.sortMode(SortMode.MIN);
        searchSourceBuilder.sort(fieldSortBuilder);
        //
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        // search Request.
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);


        //setting up for response
        EsQueryDTO esQueryDTO = new EsQueryDTO();
        esQueryDTO.setIndexName(indexName);
        esQueryDTO.setSearchSourceBuilder(searchSourceBuilder);
        BaseResult ret = esManager.query(esQueryDTO);

        JSONArray jsonArray = (JSONArray) ret.getData();

        for (int i = 0; i < jsonArray.size(); i++) {
            log.info("obj={}", jsonArray.get(i));
        }

    }


    @Test
    public void insertTest() throws Exception {

        String indexName = "test-spy-doc";

        Map<String, Object> doc = new HashMap<>();
        doc.put("productId", "9006387166");
        doc.put("productName", RandomUtil.alphabetic(10));
        doc.put("productDesc", RandomUtil.alphabetic(20));

        BaseResult ret = esManager.insertDoc(indexName, doc);
        log.info("ret={}", ret);
    }

    @Test
    public void bulkInsertTest() throws Exception {
        String indexName = "test-spy-doc";
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", RandomUtil.numeric(10));
            map.put("productName", RandomUtil.alphabetic(10));
            map.put("productDesc", RandomUtil.alphabetic(20));

            data.add(map);
        }

        BaseResult ret = esManager.insertDocByBulk(indexName, data);
        log.info("ret={}", ret);
    }

    @Test
    public void bulkDeleteTest() throws Exception {
        String indexName = "test-spy-doc";
        List<String> idList = Arrays.asList("4kZEonMBC5o1SZp5xzcP", "40ZEonMBC5o1SZp5xzcP");

        BaseResult ret = esManager.deleteDocByBulk(indexName, idList);
        log.info("ret={}", ret);
    }

    @Test
    public void updateByQueryTest() throws Exception {
        QueryBuilder queryBuilder = new TermQueryBuilder("productId", "9006387166");

        Map<String, Object> doc = new HashMap<>();
        doc.put("productName", "newName");

        esManager.updateDocByQuery(indexName, queryBuilder, doc);
    }

    @Test
    public void run143() throws Exception {
        QueryBuilder queryBuilder = new TermQueryBuilder("productId", "9006387166");
        esManager.deleteDocByQuery(indexName, queryBuilder);
    }

}
