package com.leibangzhu.starters.business.common.model;

import com.leibangzhu.starters.common.IEnum;

import java.util.HashMap;
import java.util.Map;

public enum LockStatus implements IEnum {

    LOCKED(0, "已锁车"),
    UNLOCKED(1, "已开锁"),
    ERROR(2, "锁异常"),
    STOLEN(3, "失窃"),
    Unlocking(11, "开锁中"),
    Transporting(30, "运输中"),
    Aging(40, "老化"),
    Initialize(50, "初始化");

    private Integer code;
    private String desc;
    private static Map<Integer, LockStatus> allMap;

    LockStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private static void init() {
        LockStatus[] all = LockStatus.values();
        allMap = new HashMap<>(all.length);
        for (LockStatus b : all) {
            allMap.put(b.getCode(), b);
        }
    }

    public static LockStatus convertFrom(Integer code) {
        if (null == allMap) {
            init();
        }
        return allMap.get(code);
    }

}
