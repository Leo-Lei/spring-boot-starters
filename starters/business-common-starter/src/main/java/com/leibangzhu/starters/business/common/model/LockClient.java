package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum  LockClient implements IEnum {
    QiBei(Integer.valueOf(0), "骑呗"),
    OFO(Integer.valueOf(1), "ofo");

    private Integer code;
    private String desc;
    private static Map<Integer, LockClient> allMap;

    private LockClient(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    private static void init() {
        LockClient[] all = values();
        allMap = new HashMap();
        LockClient[] var1 = all;
        int var2 = all.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LockClient b = var1[var3];
            allMap.put(b.getCode(), b);
        }

    }

    public static LockClient convertFrom(Integer status) {
        if(null == allMap) {
            init();
        }

        return (LockClient)allMap.get(status);
    }
}
