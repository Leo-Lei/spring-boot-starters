package com.leibangzhu.sample.common;

import com.leibangzhutech.starters.business.common.model.LockManufacturer;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoService {

    @Autowired
    private IMongoClient mongoClient;

    public String getLockId(String communicateId, LockManufacturer lockManufacturer) throws Exception {
        return mongoClient.find("hahaha",new HashedMap()).get("balabala").toString();
    }
}
