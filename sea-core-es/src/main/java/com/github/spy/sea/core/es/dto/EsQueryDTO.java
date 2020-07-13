package com.github.spy.sea.core.es.dto;

import com.github.spy.sea.core.model.BaseRequestDTO;
import lombok.Data;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/7/13
 * @since 1.0
 */

@Data
public class EsQueryDTO extends BaseRequestDTO {

    private String indexName;

    private SearchSourceBuilder searchSourceBuilder;

}
