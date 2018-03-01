package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum LockConnectChannel implements IEnum {
    NOKE_PLATFORM(2, "tcp锁连在深圳锁平台"),
    LOCK_PLATFORM(3, "tcp锁连在骑呗锁平台");

    private Integer code;
    private String desc;
    private static Map<Integer, LockConnectChannel> allMap;

    private LockConnectChannel(int code, String desc) {
        this.code = Integer.valueOf(code);
        this.desc = desc;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static LockConnectChannel convertFrom(Integer type) {
        if(null == allMap) {
            init();
        }

        return (LockConnectChannel)allMap.get(type);
    }

    private static void init() {
        LockConnectChannel[] all = values();
        allMap = new HashMap(all.length);
        LockConnectChannel[] var1 = all;
        int var2 = all.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LockConnectChannel lockType = var1[var3];
            allMap.put(lockType.getCode(), lockType);
        }

    }
}
