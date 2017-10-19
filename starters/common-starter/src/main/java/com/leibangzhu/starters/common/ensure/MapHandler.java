package com.leibangzhu.starters.common.ensure;

import java.util.Map;

public class MapHandler<K, V> {

    private Map<K, V> map;

    public MapHandler(Map<K, V> map) {
        this.map = map;
    }

    public EnsureResult isNotEmpty(){

        if(null != map && !map.isEmpty()){

            return EnsureResult.pass();
        }else{

            return EnsureResult.fail();
        }
    }

}
