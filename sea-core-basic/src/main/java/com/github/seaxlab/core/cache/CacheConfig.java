package com.github.seaxlab.core.cache;

import com.github.seaxlab.core.model.DTO;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/3
 * @since 1.0
 */
@Data
public class CacheConfig extends DTO {
    private String type;

    private String url;
    private Integer timeout = 5000;
    private int maxIdle = 100;
    private int poolSize = 200;
}
