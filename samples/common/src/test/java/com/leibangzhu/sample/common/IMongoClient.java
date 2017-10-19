package com.leibangzhu.sample.common;

import java.util.Map;

public interface IMongoClient {

    Map<String,Object> find(String collection,Map<String,Object> query);

}
