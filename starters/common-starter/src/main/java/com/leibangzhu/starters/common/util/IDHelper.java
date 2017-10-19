package com.leibangzhu.starters.common.util;

import java.util.Date;
import java.util.UUID;

public class IDHelper {

    private static final int UUID_LENGTH = 8;
    private static final int RIDE_UUID_LENGTH = 4;

    /**
     * RideId生成器 长度：16
     */
    public static String createRideID() {
        StringBuilder id = new StringBuilder(DateUtil.formatDate(new Date(), "yyMMddHHmmss"));
        id.append(UUID.randomUUID().toString().substring(0, RIDE_UUID_LENGTH));
        return id.toString();
    }

    public static String createID() {
        StringBuilder id = new StringBuilder(DateUtil.getDateTimes());
        id.append(UUID.randomUUID().toString().substring(0, UUID_LENGTH));
        return id.toString();
    }

    public static String createID(Date date) {
        StringBuilder id = new StringBuilder(DateUtil.formatDate(date, "yyMMddHHmmss"));
        id.append(UUID.randomUUID().toString().substring(0, UUID_LENGTH));
        return id.toString();
    }


    /**
     * 返回数字类型的唯一ID值
     */
    public static int createNumericID() {
        return UUID.randomUUID().hashCode();
    }
}
