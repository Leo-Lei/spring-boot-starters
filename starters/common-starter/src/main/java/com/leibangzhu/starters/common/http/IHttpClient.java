package com.leibangzhu.starters.common.http;

import java.io.IOException;
import java.util.Map;

public interface IHttpClient {

    String get(String url);
    String get(String urlStr, Map<String, Object> params);
    String post(String url);
    String post(String urlStr, Object body);
    String post(String url, Map<String, String> paramMap, Object body) throws IOException;
    String post(String url, Map<String, String> paramMap, Object body, Integer socketTimeout, Integer connectTimeout, Integer connectionRequestTimeout) throws IOException;
    String postJson(String url, Map<String, String> paramMap, Object body) throws IOException;
}
