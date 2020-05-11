package com.github.spy.sea.core.es;

import com.sun.scenario.Settings;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/11
 * @since 1.0
 */
@Slf4j
public class AbstractEsTest {

    @Test
    public void run16() throws Exception {
        log.info("abc");
    }

//    protected TransportClient esClient() throws UnknownHostException {
//        Settings settings = Settings.builder()
//                                    .put("cluster.name", this.esName)
////                .put("cluster.name", "lcz")
//                                    .put("client.transport.sniff", true)
//                                    .build();
//
//        InetSocketTransportAddress master = new InetSocketTransportAddress(
//                InetAddress.getByName(esHost), esPort
////          InetAddress.getByName("127.0.0.1"), 9300
//        );
//
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(master);
//
//        return client;
//    }
}
