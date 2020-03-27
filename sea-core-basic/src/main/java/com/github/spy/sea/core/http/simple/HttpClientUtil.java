package com.github.spy.sea.core.http.simple;

import com.alibaba.fastjson.JSONObject;
import com.github.spy.sea.core.common.CoreErrorConst;
import com.github.spy.sea.core.exception.ExceptionHandler;
import com.github.spy.sea.core.model.BaseResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http client util
 */
public class HttpClientUtil {

    private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static CloseableHttpClient httpClient = null;

    /**
     * 连接超时时间10秒
     */
    public static int connectionTimeout = 10000;

    /**
     * 请求连接超时时间5秒
     */
    public static int connectionRequestTimeout = 5000;

    /**
     * 最大并发数
     */
    public static int managerMaxTotal = 300;

    /**
     * 3秒sokect无反应强制断开
     */
    public static int socketTimeOut = 3000;

    public static String DEFAULT_CHARSET = "utf-8";

    public static void init() {
        //初始化httpclient（支持并发）
        if (httpClient == null) {
            SSLContext ctx;//绕过https证书验证
            try {
                ctx = SSLContext.getInstance("TLS");

                X509TrustManager tm = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", ssf)
                        .build();
                //manager
                PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
                manager.setMaxTotal(managerMaxTotal);
                //socketConfig
                SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeOut).build();
                httpClient = HttpClients.custom()
                                        .setConnectionManager(manager)
                                        .setDefaultSocketConfig(socketConfig)
                                        .build();
            } catch (Exception ex) {
                log.error("httpClient——初始化——报错，错误信息", ex);
            }
        }
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param map
     * @param charset
     * @return
     * @throws Exception
     */
    public static String post(String url, Map map, String charset) {
        init();//初始化检查

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> list = new ArrayList<>();
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry elem = (Entry) iterator.next();
            list.add(new BasicNameValuePair(String.valueOf(elem.getKey()), String.valueOf(elem.getValue())));
        }
        String result = null;

        try {
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            //requestConfig
            RequestConfig requestConfig = RequestConfig.custom()
                                                       .setConnectionRequestTimeout(connectionRequestTimeout)
                                                       .setConnectTimeout(connectionTimeout)
                                                       .build();
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, charset);
                    }
                }
            } finally {
                if (response != null)
                    response.close();
            }
        } catch (Exception e) {
            log.error("http exception", e);
            ExceptionHandler.publish(CoreErrorConst.HTTP_ERR, "http请求异常");
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String post(String url, Map map) {
        return post(url, map, DEFAULT_CHARSET);
    }


    /**
     * 发送POST, application/json请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String postJSON(final String url) {
        return postJSON(url, null);
    }

    /**
     * 发送POST, application/json请求
     *
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String postJSON(String url, Object obj) {
        init();//初始化检查
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("Content-Type", "application/json;charset=utf8");
        log.info("request url={}", url);

        if (obj != null) {
            String jsonStr;
            if (obj instanceof String) {
                jsonStr = (String) obj;
            } else {
                jsonStr = JSONObject.toJSONString(obj);
            }
            StringEntity entity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
        }
        //requestConfig
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectionRequestTimeout(connectionRequestTimeout)
                                                   .setConnectTimeout(connectionTimeout)
                                                   .build();
        httpPost.setConfig(requestConfig);

        String result = null;
        CloseableHttpResponse response = null;

        try {

            try {
                response = httpClient.execute(httpPost);
                if (response != null) {
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        result = EntityUtils.toString(resEntity, DEFAULT_CHARSET);
                    }
                }
            } finally {
                if (response != null)
                    response.close();
            }
        } catch (Exception e) {
            log.error("http exception", e);
            ExceptionHandler.publish(CoreErrorConst.HTTP_ERR, "http请求异常");
        }
        return result;
    }

    /**
     * post with no exception
     *
     * @param url
     * @param obj
     * @return
     */
    public static BaseResult postJSONSafe(String url, Object obj) {
        BaseResult result = BaseResult.fail();

        try {
            String response = postJSON(url, obj);
            result.setData(response);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("http exception", e);
            result.setErrorMessage(e.getMessage());
        }

        return result;
    }


    public static BaseResult getSafe(final String url, Map<String, Object> param) {
        BaseResult result = BaseResult.fail();

        try {
            String content = get(url, param);
            result.setData(content);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 发送GET请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String get(final String url, Map<String, Object> param) throws IOException {

        List<NameValuePair> list = new ArrayList<>();
        Iterator iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry elem = (Entry) iterator.next();
            list.add(new BasicNameValuePair(String.valueOf(elem.getKey()), String.valueOf(elem.getValue())));
        }

        String queryStr = URLEncodedUtils.format(list, DEFAULT_CHARSET);

        String finalUrl = url + (url.indexOf("?") > 0 ? "&" : "?") + queryStr;

        return get(finalUrl);
    }

    public static BaseResult getSafe(final String url) {
        BaseResult result = BaseResult.fail();

        try {
            String content = get(url);
            result.setData(content);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
        }

        return result;
    }

    /**
     * 发送GET请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(final String url) {
        log.info("http get method, url=[{}]", url);
        init();

        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectionRequestTimeout(connectionRequestTimeout)
                                                   .setConnectTimeout(connectionTimeout)
                                                   .build();

        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        request.setHeader("User-Agent", "Chrome/Sea");


        String result = null;
        CloseableHttpResponse response = null;

        try {
            try {
                response = httpClient.execute(request);

                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
                    } else {
                        log.warn("http status not 200, status code={}", response.getStatusLine().getStatusCode());
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (Exception e) {
            log.error("http exception", e);
            ExceptionHandler.publish(CoreErrorConst.HTTP_ERR, "http请求异常");
        }

        return result;
    }
}
