package com.leibangzhu.starters.common.http;


import com.alibaba.fastjson.JSONObject;
import com.leibangzhu.starters.common.http.properties.HttpClientProperties;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpClient implements IHttpClient{

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private final int MAX_CONNECTION_TOTAL;
    private final int MAX_CONNECTION_PER_ROUTE;
    private final int SOCKET_TIMEOUT;
    private final int CONNECT_TIMEOUT;
    private final int CONNECT_REQUEST_TIMEOUT;

    private PoolingHttpClientConnectionManager connectionManager = null;
    private RequestConfig defaultRequestConfig = null;
    private HttpClientBuilder httpBuilder = null;

    public HttpClient(HttpClientProperties properties){
        this.MAX_CONNECTION_TOTAL = properties.getPool().getMaxTotal();
        this.SOCKET_TIMEOUT = properties.getSocketTimeout();
        this.CONNECT_TIMEOUT = properties.getConnectTimeout();
        this.CONNECT_REQUEST_TIMEOUT = properties.getConnectRequestTimeout();
        this.MAX_CONNECTION_PER_ROUTE = properties.getPool().getMaxPerRoute();

        init();
    }

    public HttpClient(){
        this(new HttpClientProperties());
    }

    private void init(){
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (KeyManagementException e) {
            logger.error(e.getMessage(), e);
        } catch (KeyStoreException e) {
            logger.error(e.getMessage(), e);
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, new TrustAnyHostnameVerifier());
        Registry registry = RegistryBuilder
                .create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslsf).build();

        connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(MAX_CONNECTION_TOTAL);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTION_PER_ROUTE);

        httpBuilder = HttpClients.custom();
        httpBuilder.setConnectionManager(connectionManager);

        defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(2000) //等待数据超时时间
                .setConnectTimeout(3000) //连接超时时间
                .setConnectionRequestTimeout(200) //从连接池中获取连接的超时时间
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                .build();
    }

    private CloseableHttpClient getConnection() {
        CloseableHttpClient httpClient = httpBuilder.build();
        return httpClient;
    }

    private org.apache.http.client.HttpClient getHttpClient() {
        org.apache.http.client.HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setConnectionManager(connectionManager).build();
        return httpClient;
    }

    private RequestConfig buildRequestConfig(Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) {
        return RequestConfig.custom()
                .setSocketTimeout(null == socketTimeout ? SOCKET_TIMEOUT : socketTimeout)
                .setConnectTimeout(null == connectTimeout ? CONNECT_TIMEOUT : connectTimeout)
                .setConnectionRequestTimeout(null == connectionRequestTimeout ? CONNECT_REQUEST_TIMEOUT : connectionRequestTimeout)
                .build();
    }

    public String get(String url) {

        //logger.info("url:{}", url);
        org.apache.http.client.HttpClient httpclient = getHttpClient();
        HttpGet get = null;
        try {
            //URL中的特殊字符转义
            URL u = new URL(url);
//            URI uri = new URI(u.getProtocol(), u.getHost(), u.getPath(), u.getQuery(), null);
            URI uri = new URIBuilder()
                    .setScheme(u.getProtocol())
                    .setHost(u.getHost())
                    .setPath(u.getPath())
                    .setPort(u.getPort())
                    .build();

            get = new HttpGet(uri);
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                return loadData(instreams);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            get.abort();
        }
        return null;
    }

    public String get(String urlStr, Map<String, Object> params) {
        logger.info("url:{}", urlStr);
        HttpGet get = null;
        try {
            URL url = new URL(urlStr);
            URI uri = new URIBuilder()
                    .setScheme(url.getProtocol())
                    .setHost(url.getHost())
                    .setPath(url.getPath())
                    .setPort(url.getPort())
                    .setParameters(buildParams(params))
                    .build();
            get = new HttpGet(uri);
            HttpResponse response = getHttpClient().execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                return loadData(instreams);
            }
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            get.abort();
        }
        return null;
    }

    public String post(String url) {
        logger.info("url:{}", url);
        org.apache.http.client.HttpClient httpclient = getHttpClient();
        HttpPost post = null;
        try {
            //URL中的特殊字符转义
            URL u = new URL(url);
            URI uri = new URI(u.getProtocol(), u.getHost(), u.getPath(), u.getQuery(), null);
            post = new HttpPost(uri);
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                return loadData(instreams);
            }
        } catch (IllegalStateException | URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            post.abort();
        }
        return null;
    }

    public String post(String urlStr, Object body) {
        HttpPost post = null;
        try {
            URL url = new URL(urlStr);
            URI uri = new URIBuilder()
                    .setScheme(url.getProtocol())
                    .setHost(url.getHost())
                    .setPath(url.getPath())
                    .setPort(url.getPort())
                    .build();
            post = new HttpPost(uri);
            post.setEntity(buildStringEntity(body, ContentType.APPLICATION_JSON));
            HttpResponse response = getHttpClient().execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                return loadData(instreams);
            }
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != post) {
                post.abort();
            }
        }
        return null;
    }

    public String post(String url, Map<String, String> paramMap, Object body) throws IOException {
        HttpUriRequest reqMethod = RequestBuilder.post()
                .setUri(url)
                .setEntity(buildStringEntity(body, ContentType.APPLICATION_JSON))
                .addParameters(params(paramMap))
                .setConfig(buildRequestConfig(null, null, null))
                .build();
        return execute(reqMethod);
    }

    public String post(String url, Map<String, String> paramMap, Object body, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout)
            throws IOException {
        HttpUriRequest reqMethod = RequestBuilder.post()
                .setUri(url)
                .setEntity(buildStringEntity(body, ContentType.APPLICATION_JSON))
                .addParameters(params(paramMap))
                .setConfig(buildRequestConfig(socketTimeout, connectTimeout, connectionRequestTimeout))
                .build();
        return execute(reqMethod);
    }


    public String postJson(String url, Map<String, String> paramMap, Object body) throws IOException {
        HttpUriRequest reqMethod = RequestBuilder.post()
                .setUri(url)
                .setEntity(buildStringEntity(body, ContentType.APPLICATION_JSON))
                .addParameters(params(paramMap))
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .setConfig(buildRequestConfig(null, null, null))
                .build();
        return execute(reqMethod);
    }

    private static BasicNameValuePair[] params(Map<String, String> paramMap) {
        List<BasicNameValuePair> params = new ArrayList<>();
        if (MapUtils.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> e : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
            }
        }
        return params.toArray(new BasicNameValuePair[params.size()]);
    }

    private List<NameValuePair> buildParams(Map<String, Object> params) {
        if (MapUtils.isEmpty(params)) {
            return null;
        }

        List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }
        return nvps;
    }

    private HttpEntity buildStringEntity(Object obj, ContentType type) {
        if (null == obj) {
            return null;
        }
        return new StringEntity(JSONObject.toJSONString(obj), type);
    }

    private String execute(HttpUriRequest reqMethod) throws IOException {
        HttpResponse response = getConnection().execute(reqMethod);
        String message = null;
        if (200 == response.getStatusLine().getStatusCode()) {
            message = EntityUtils.toString(response.getEntity(), "utf-8");
            logger.debug("Http Request is Ok. {}", message);
        } else {
            reqMethod.abort();
            logger.warn("Http Request is fail.");
        }
        return message;
    }

    /**
     * 数据加载和编码转换
     *
     * @param is
     * @return
     */
    private String loadData(InputStream is) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = new byte[1024];
        int size = 0;
        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb.append(str);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return sb.toString();
    }

    public void release() {
        if (this.connectionManager != null) {
            this.connectionManager.shutdown();
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}