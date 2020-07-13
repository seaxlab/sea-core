package com.github.spy.sea.core.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/5/11
 * @since 1.0
 */
@Slf4j
public class AbstractEsTest {

    protected RestHighLevelClient client;

    protected String host = "172.16.67.175";

    protected String getHost() {
        return host;
    }

    @Before
    public void before() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(getHost(), 9200, "http")));

    }

//    @After
//    public void after() {
//        if (client != null) {
//            try {
//                client.close();
//            } catch (IOException e) {
//                log.error("es client close error", e);
//            }
//        }
//    }

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
