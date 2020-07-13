package com.github.spy.sea.core.es.manager;

import com.alibaba.fastjson.JSONArray;
import com.github.spy.sea.core.es.AbstractEsTest;
import com.github.spy.sea.core.es.dto.EsQueryDTO;
import com.github.spy.sea.core.es.manager.impl.EsManagerImpl;
import com.github.spy.sea.core.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void before() {
        super.before();

        esManager = new EsManagerImpl();
        esManager.init(client);
    }

    @Test
    public void queryMinValueTest() throws Exception {


        String indexName = "jaeger-span-2020-07-12";

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
        SearchRequest searchRequest = new SearchRequest("jaeger-span-2020-07-12");
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

}
