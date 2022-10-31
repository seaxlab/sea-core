package com.github.seaxlab.core.springcloud.feign.mock;

import com.github.seaxlab.core.exception.ExceptionHandler;
import com.github.seaxlab.core.springcloud.feign.FeignMockProperties;
import com.github.seaxlab.core.springcloud.feign.util.KeyUtil;
import com.github.seaxlab.core.util.FileUtil;
import com.github.seaxlab.core.util.PathUtil;
import com.google.common.collect.Lists;
import feign.Client;
import feign.Request;
import feign.Response;
import feign.Util;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

/**
 * mock load balance feign client
 */
@Slf4j
public class MockLoadBalancerFeignClient extends FeignBlockingLoadBalancerClient {

  private FeignMockProperties apiMockProperties;

  public MockLoadBalancerFeignClient(Client delegate, LoadBalancerClient loadBalancerClient,
      LoadBalancerProperties properties, LoadBalancerClientFactory clientFactory,
      FeignMockProperties apiMockProperties) {
    super(delegate, loadBalancerClient, properties, clientFactory);
    this.apiMockProperties = apiMockProperties;
  }

  @Override
  public Response execute(Request request, Request.Options options) throws IOException {

    if (!apiMockProperties.getEnabled()) {
      return super.execute(request, options);
    }

    String requestResource = KeyUtil.getKey(request);
    if (!StringUtils.hasText(requestResource)) {
      return super.execute(request, options);
    }

    String mockApi = apiMockProperties.getMockApi(requestResource.toLowerCase());
    if (!StringUtils.hasText(mockApi)) {
      return super.execute(request, options);
    }
    try {
      return parseProtocol(request, options, mockApi);
    } catch (Exception e) {
      log.error("fail to invoke feign mock api, so invoke default, plz check", e);
    }

    return super.execute(request, options);
  }


  /**
   * parse protocol
   *
   * @param request
   * @param options
   * @param mockApi
   * @return
   */
  private Response parseProtocol(Request request, Request.Options options, String mockApi)
      throws IOException {
    String[] mockArray = mockApi.split("\\|");
    if (mockArray.length != 2) {
      ExceptionHandler.publishMsg("mock api format is illegal. [protocol|url]");
    }

    String protocol = mockArray[0];
    String realUri = mockArray[1];

    switch (protocol.trim()) {
      case "local":
        log.info("====");
        log.info("====[feign mock, local] load local file={}", realUri);
        log.info("====");

        String path = PathUtil.join(apiMockProperties.getConfigPath(), realUri.trim());
        if (FileUtil.exist(path)) {
          Map<String, Collection<String>> headers = new HashMap<>();
          headers.put(HttpHeaders.CONTENT_TYPE,
              Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE));

          return Response.builder() //
              .headers(headers) //
              .status(HttpStatus.OK.value()) //
              .request(request) //
              .body(FileUtil.readFileToBytes(path))//
              .build();
        } else {
          ExceptionHandler.publishMsg("feign mock local file is not exist [{}], so invoke default.",
              path);
        }
      case "remote":
        log.info("====");
        log.info("====[feign mock, remote] load remote uri={}", realUri);
        log.info("====");

        Request newRequest = Request.create(Request.HttpMethod.GET, realUri.trim(),
            Collections.emptyMap(), null, Util.UTF_8, null);
        return this.getDelegate().execute(newRequest, options);
      default:
        ExceptionHandler.publishMsg("unsupported feign mock protocol, so do origin remote call.");
        break;
    }

    return null;
  }

}
