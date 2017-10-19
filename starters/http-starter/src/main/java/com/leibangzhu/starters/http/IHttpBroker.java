package com.leibangzhu.starters.http;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Author: lili
 * Date: 2017/2/21
 * Time: 16:55
 */
public interface IHttpBroker {

    String get(String url);

    String get(String url, Map<String, Object> parameters, Map<String, Object> headers);

    String post(String url, Map<String, Object> parameters, Map<String, Object> headers);
}