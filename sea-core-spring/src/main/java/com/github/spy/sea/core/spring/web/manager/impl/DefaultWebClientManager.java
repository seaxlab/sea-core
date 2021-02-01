package com.github.spy.sea.core.spring.web.manager.impl;

import com.github.spy.sea.core.spring.web.manager.WebClientManager;
import com.github.spy.sea.core.spring.web.manager.model.WebClientConfig;
import com.github.spy.sea.core.util.IntegerUtil;
import com.github.spy.sea.core.util.LongUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/1/31
 * @since 1.0
 */
@Slf4j
public class DefaultWebClientManager implements WebClientManager {

    private String name;

    private int maxConnections = 2_000;

    private Duration maxIdleTime = Duration.ofMillis(40_000);

    private Duration pendingAcquireTimeout = Duration.ofMillis(6_000);

    private long connReadTimeout = 20_000;

    private long connWriteTimeout = 20_000;

    private int chConnTimeout = 20_000;

    private boolean chTcpNoDelay = true;

    private boolean chSoKeepAlive = true;

    private boolean compress = false;

    public DefaultWebClientManager(String name) {
        this.name = name;
    }

    @Override
    public WebClient init(WebClientConfig config) {

        this.maxConnections = IntegerUtil.defaultIfNull(config.getMaxConnection(), 2_000);
        this.maxIdleTime = Duration.ofMillis(LongUtil.defaultIfNull(config.getMaxIdleTime(), 60_000L));
        this.pendingAcquireTimeout = Duration.ofMillis(LongUtil.defaultIfNull(config.getPendingAcquireTimeout(), 10_000L));
        this.connReadTimeout = LongUtil.defaultIfNull(config.getConnReadTimeout(), 30_000L);
        this.connWriteTimeout = LongUtil.defaultIfNull(config.getConnWriteTimeout(), 30_000L);


        ConnectionProvider cp = getConnectionProvider();
        LoopResources lr = getLoopResources();
        HttpClient httpClient = HttpClient.create(cp)
                                          .compress(compress)
                                          .tcpConfiguration(
                                                  tcpClient -> {
                                                      return tcpClient.runOn(lr, false)
                                                                      .doOnConnected(
                                                                              connection -> {
                                                                                  connection.addHandlerLast(new ReadTimeoutHandler(connReadTimeout, TimeUnit.MILLISECONDS))
                                                                                            .addHandlerLast(new WriteTimeoutHandler(connWriteTimeout, TimeUnit.MILLISECONDS));
                                                                              }
                                                                      )
                                                                      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, chConnTimeout)
                                                                      .option(ChannelOption.TCP_NODELAY, chTcpNoDelay)
                                                                      .option(ChannelOption.SO_KEEPALIVE, chSoKeepAlive)
                                                                      // .option(ChannelOption.ALLOCATOR, PreferHeapByteBufAllocator.DEFAULT);
                                                                      // .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                                                                      .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT);
                                                  }
                                          );
        return WebClient.builder()
                        .exchangeStrategies(ExchangeStrategies.builder()
                                                              .codecs(configurer -> configurer.defaultCodecs()
                                                                                              .maxInMemorySize(-1))
                                                              .build())
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .build();
    }

    private ConnectionProvider getConnectionProvider() {
        String cpName = name + "-cp";
        ConnectionProvider cp = ConnectionProvider.builder(cpName)
                                                  .maxConnections(maxConnections)
                                                  .pendingAcquireTimeout(pendingAcquireTimeout)
                                                  .maxIdleTime(maxIdleTime)
                                                  .build();
        log.info(cpName + ' ' + cp);
        return cp;
    }

    private LoopResources getLoopResources() {
        String elPrefix = name + "-el";
        LoopResources lr = LoopResources.create(elPrefix, Runtime.getRuntime().availableProcessors(), true);
        lr.onServer(false);
        log.info(name + "-lr " + lr);
        return lr;
    }


}
