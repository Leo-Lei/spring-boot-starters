package com.leibangzhu.starters.common.eventaggregator;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaseEvent implements IEvent{

    private Map<String,Object> params = new LinkedHashMap<>();

    public void addParam(String key, Object value){
        params.put(key,value);
    }

    public String getName() {

        return getClass().getSimpleName();
    }

    @Override
    public Map<String, Object> getParams() {
        return params;
    }
}
