package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum LockStage implements IEnum {
    INIT(Integer.valueOf(0), "锁厂"),
    BUSINESS(Integer.valueOf(1), "商用");

    private int code;
    private String desc;
    private static Map<Integer, LockStage> allMap;

    private LockStage(Integer code, String desc) {
        this.code = code.intValue();
        this.desc = desc;
    }

    public Integer getCode() {
        return Integer.valueOf(this.code);
    }

    public String getDesc() {
        return this.desc;
    }

    public static LockStage convertFrom(Integer type) {
        if(null == allMap) {
            init();
        }

        return (LockStage)allMap.get(type);
    }

    private static void init() {
        LockStage[] all = values();
        allMap = new HashMap(all.length);
        LockStage[] var1 = all;
        int var2 = all.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LockStage lockStage = var1[var3];
            allMap.put(lockStage.getCode(), lockStage);
        }

    }
}
