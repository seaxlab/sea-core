package com.github.spy.sea.core.springcloud.feign;

import com.github.spy.sea.core.common.SymbolConst;
import com.github.spy.sea.core.springcloud.common.Const;
import com.github.spy.sea.core.springcloud.feign.config.FeignMockProperties;
import com.github.spy.sea.core.thread.ThreadContext;
import com.github.spy.sea.core.util.FileUtil;
import com.github.spy.sea.core.util.PathUtil;
import feign.Client;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
public class MockLoadBalancerFeignClient extends LoadBalancerFeignClient {

    @Value("${spring.application.name:unknown}")
    private String appName;


    private FeignMockProperties apiMockProperties;

    public MockLoadBalancerFeignClient(Client delegate, CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory, FeignMockProperties apiMockProperties) {
        super(delegate, lbClientFactory, clientFactory);
        this.apiMockProperties = apiMockProperties;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {

        if (!apiMockProperties.getEnabled()) {
            return super.execute(request, options);
        }

        String feignCallResourceName = ThreadContext.get(Const.FEIGN_REQUEST_URI, "");

        if (StringUtils.hasText(feignCallResourceName)) {
            String mockApi = apiMockProperties.getMockApi(feignCallResourceName);
            if (StringUtils.hasText(mockApi)) {
                try {
                    String[] mockArray = mockApi.split(SymbolConst.PIPE);
                    String protocol = mockArray[0];
                    String realUri = mockArray[1];

                    switch (protocol) {
                        case "local":
                            log.info("[feign mock, local] load local file");
                            String path = PathUtil.join(PathUtil.getSeaHome(), "feign", appName, realUri);
                            if (FileUtil.exist(path)) {
                                if (log.isDebugEnabled()) {
                                    log.debug("[feign mock] file exist, so load it.");
                                }
                                return Response.builder().body(FileUtil.readFileToBytes(path)).build();
                            } else {
                                log.warn("feign mock local file is not exist [{}], so invoke default.", path);
                                super.execute(request, options);
                            }
                        case "remote":
                            log.info("[feign mock, remote] load remote uri={}", realUri);
                            Request newRequest = Request.create(request.method(), realUri, request.headers(), request.body(), request.charset());
                            return super.getDelegate().execute(newRequest, options);
                        default:
                            log.warn("unsupported feign mock protocol.");
                            break;
                    }

                } catch (Exception e) {
                    log.error("fail to invoke feign mock api, so invoke default, plz check", e);
                    super.execute(request, options);
                }
            }
        }

        return super.execute(request, options);
    }

}
