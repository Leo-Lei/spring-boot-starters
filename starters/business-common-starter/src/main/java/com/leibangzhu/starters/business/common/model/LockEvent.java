package com.leibangzhu.starters.business.common.model;

import java.util.HashMap;
import java.util.Map;

public enum LockEvent {
    HEARTBEAT("heartbeat", "心跳"),
    LOCK("lock", "锁车"),
    UNLOCK("unlock", "开锁"),
    BEEP("beep", "寻车提示"),
    QUERY("select", "查询设备"),
    REGISTER("register", "注册设备"),
    CONFIG("config", "配置信息"),
    ORDER_LIFE_TIME("orderlifetime", "指令有效时间"),
    RESET("reset", "复位"),
    REBOOT("reboot", "重启"),
    UPGRADE("upgrade", "升级"),
    ALERT("alert", "告警"),
    /** 固件是否需要升级 */
    SELF_CHECK("self-check", "自检"),
    PLATFORM("platform", "重新烧号"),
    TRANSPORT("transport", "运输模式"),
    VERSION("version", "版本信息");

    private String type;
    private String desc;
    private static Map<String, LockEvent> allMap;

    LockEvent(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    private static void init() {
        LockEvent[] all = LockEvent.values();
        allMap = new HashMap<>(all.length);
        for (LockEvent b : all) {
            allMap.put(b.getType(), b);
        }
    }

    public static LockEvent convertFrom(String type) {
        if (null == allMap) {
            init();
        }
        return allMap.get(type);
    }
}