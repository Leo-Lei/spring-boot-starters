package com.leibangzhu.starters.http;

import com.leibangzhu.starters.common.util.QibeiLogger;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

/**
 * Author: lili
 * Date: 2017/7/25
 * Time: 16:54
 */
@Component
public class HttpBroker implements IHttpBroker, InitializingBean, DisposableBean {

    private static final Logger logger = QibeiLogger.create(HttpBroker.class);
    private static final String DEFAULT_TEXT_ENCODING = "UTF-8";

    @Autowired(required = false)
    private HttpBrokerConfiguration config;

    private CloseableHttpClient innerClient;
    private CloseableHttpClient innerSSLClient;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (null == innerClient) {

            if (null == config) {

                config = HttpBrokerConfiguration.DEFAULT;
            }

            createInnerClient(config);
        }
    }

    @Override
    public void destroy() throws Exception {

        if(null != innerClient){

            innerClient.close();
        }
    }

    @Override
    public String get(String url){

        return get(url, null, null);
    }

    @Override
    public String get(String url, Map<String, Object> parameters, Map<String, Object> headers) {

        HttpGet request;
        CloseableHttpResponse response = null;
        String queryUrl = url;

        try {

            queryUrl = buildRequestQuery(url, parameters);
            request = new HttpGet(queryUrl);
            appendHeaders(headers, request);

            response = executeHttpRequest(request, queryUrl);

            return processResponse(response);

        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(null != response){

                try {
                    response.close();
                } catch (IOException e) {

                    logger.error(String.format("Error while closing http response for %s", queryUrl));
                }
            }
        }

        return null;
    }

    @Override
    public String post(String url, Map<String, Object> parameters, Map<String, Object> headers) {

        HttpPost request;
        CloseableHttpResponse response = null;
        String queryUrl = url;

        try {

            queryUrl = buildRequestQuery(url, null);
            request = new HttpPost(queryUrl);
            appendHeaders(headers, request);

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();

            if(null != parameters){

                for(Map.Entry<String, Object> pair : parameters.entrySet()){

                    formparams.add(new BasicNameValuePair(pair.getKey(), pair.getValue().toString()));
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, DEFAULT_TEXT_ENCODING);

            request.setEntity(entity);

            response = executeHttpRequest(request, queryUrl);

            return processResponse(response);

        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(null != response){

                try {
                    response.close();
                } catch (IOException e) {

                    logger.error(String.format("Error while closing http response for %s", queryUrl));
                }
            }
        }

        return null;
    }

    private String processResponse(CloseableHttpResponse response) throws IOException {

        HttpEntity entity = response.getEntity();
        if (null != entity) {

            InputStream instream = entity.getContent();
            try {
                return loadDataFromResponse(instream);
            } finally {
                instream.close();
            }
        }

        return null;
    }

    private CloseableHttpResponse executeHttpRequest(HttpRequestBase request, String queryUrl) throws IOException {

        StopWatch sw = new StopWatch();
        sw.start();
        logger.info(String.format("Http request for %s is starting on %s", queryUrl.toLowerCase().startsWith("https") ? "******" : queryUrl, sw));
        CloseableHttpResponse response = null;
        if(queryUrl.toLowerCase().startsWith("https")){

            response = innerSSLClient.execute(request);
        }else{

            response = innerClient.execute(request);
        }
        sw.split();
        logger.info(String.format("Http request for %s is finished on %s with elapse %s nano seconds", queryUrl.toLowerCase().startsWith("https") ? "******" : queryUrl, sw, sw.getTime()));

        return response;
    }

    private String buildRequestQuery(String url, Map<String, Object> parameters) throws MalformedURLException, URISyntaxException {

        URL urlInfo = new URL(url);
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(urlInfo.getProtocol())
                .setHost(urlInfo.getHost())
                .setPath(urlInfo.getPath())
                .setPort(urlInfo.getPort());

        if(null != parameters){

            for(Map.Entry<String, Object> pair : parameters.entrySet()){

                uriBuilder.addParameter(pair.getKey(), pair.getValue().toString());
            }
        }

        return uriBuilder.build().toString();
    }

    private String loadDataFromResponse(InputStream in) {

        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int size = 0;
        try {
            while ((size = in.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, DEFAULT_TEXT_ENCODING);
                builder.append(str);
            }
        } catch (IOException e) {
            logger.error("Error while parsing data from http response.", e);
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("Error while closing input stream of http response.", e);
                return null;
            }
        }

        return builder.toString();
    }

    private void createInnerClient(HttpBrokerConfiguration config) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(config.getMaxConnections());
        cm.setDefaultMaxPerRoute(config.getDefaultMaxConnectionsForEachRoute());

        Map<HttpRoute, Integer> maxConnectionsPerRoute = config.getMaxConnectionsPerRoute();
        if (null != maxConnectionsPerRoute && 0 < maxConnectionsPerRoute.size()) {

            for (Map.Entry<HttpRoute, Integer> pair : maxConnectionsPerRoute.entrySet()) {

                cm.setMaxPerRoute(pair.getKey(), pair.getValue());
            }
        }

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(config.getSocketTimeout()) //等待数据超时时间
                .setConnectTimeout(config.getConnectTimeout()) //连接超时时间
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout()) //从连接池中获取连接的超时时间
                .setCookieSpec(config.getCookieSpec())
                .build();

        innerClient =
                HttpClients.custom()
                        .setConnectionManager(cm)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build();


        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

            @Override
            public void verify(String host, SSLSocket ssl)
                    throws IOException {
            }

            @Override
            public void verify(String host, X509Certificate cert)
                    throws SSLException {
            }

            @Override
            public void verify(String host, String[] cns,
                               String[] subjectAlts) throws SSLException {
            }

        });

        innerSSLClient =
                HttpClients.custom()
                        .setConnectionManager(cm)
                        .setSSLSocketFactory(sslConnectionSocketFactory)
                        .setDefaultRequestConfig(defaultRequestConfig)
                        .build();
    }

    private void appendHeaders(Map<String, Object> headers, HttpRequestBase request) {
        if(null != headers){

            for(Map.Entry<String, Object> pair : headers.entrySet()){

                request.addHeader(pair.getKey(), pair.getValue().toString());
            }
        }
    }
}
