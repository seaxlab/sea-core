package com.github.seaxlab.core.springcloud.feign.config;

import com.github.seaxlab.core.util.StringUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "sea.springcloud.feign.mock")
public class FeignMockProperties {

    private Boolean enabled = false;

    /**
     * 资源：mock地址
     * <p>格式：GET:http://user-service/api/user/{userId}: remote|http://xxx.com/mock/api/1001</p>
     * <p>格式：GET:http://user-service/api/user/{userId}: local|100.json</p>
     * <p>格式：GET:http://user-service/api/user/{userId}: db_json|http://xxx.com/mock/api/10001</p>
     * <p>格式：GET:http://user-service/api/user/{userId}:db_remote|http://xxx.com/mock/api/10001</p>
     */
    private Map<String, String> apis;


    // --------- public api------------
    public String getMockApi(String resource) {
        if (!enabled) {
            return StringUtil.EMPTY;
        }

        if (CollectionUtils.isEmpty(apis)) {
            return StringUtil.EMPTY;
        }
        return apis.getOrDefault(resource, "");
    }

}
