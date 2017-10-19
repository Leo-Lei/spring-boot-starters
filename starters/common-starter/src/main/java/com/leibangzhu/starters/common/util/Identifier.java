package com.leibangzhu.starters.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

public class Identifier {
    private static final int UUID_LENGTH = 8;
    private static final String DATE_TIME_PARAM_FORMAT = "yyyyMMddHHmmss";

    private Identifier(){}

    public static String createID() {

        StringBuilder id = new StringBuilder(DateFormatUtils.format(new Date(), DATE_TIME_PARAM_FORMAT));
        id.append(UUID.randomUUID().toString().substring(0, UUID_LENGTH));

        return id.toString();
    }

    public static int createNumericID() {
        return UUID.randomUUID().hashCode();
    }
}
