package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum LockManufacturer implements IEnum {
    Manual(1, "手动锁"),
    Noke(2, "深圳锁厂"),
    TJLock(3, "天津锁厂"),
    AliIoT_HangZhou(4, "阿里IoT杭州节点"),
    AliIoT_HuaDong2(7, "阿里IoT华东节点"),
    Jimi(6, "几米");

    private Integer code;
    private String desc;

    private static Map<Integer, LockManufacturer> allMap;

    LockManufacturer(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static LockManufacturer convertFrom(Integer type) {
        if (null == allMap) {
            init();
        }
        return allMap.get(type);
    }

    private static void init() {
        LockManufacturer[] all = LockManufacturer.values();
        allMap = new HashMap<>(all.length);
        for (LockManufacturer lockType : all) {
            allMap.put(lockType.getCode(), lockType);
        }
    }
}
