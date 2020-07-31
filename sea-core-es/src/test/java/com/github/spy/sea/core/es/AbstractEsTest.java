package com.github.spy.sea.core.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

    protected String host = "es-cn-m7r1rd2ve0001yhhc.elasticsearch.aliyuncs.com";

    protected String getHost() {
        return host;
    }

    @Before
    public void before() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "Yuantu123"));

        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(getHost(), 9200, "http"))
                          .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                                  .setDefaultCredentialsProvider(credentialsProvider)));

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
