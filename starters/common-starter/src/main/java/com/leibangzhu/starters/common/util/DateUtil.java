package com.leibangzhu.starters.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date from(int year, int month, int day, int hour, int min, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1,day,hour,min,second);
        return calendar.getTime();
    }

    public static Date getBeiJingTime() {
        return new DateTime().withZone(DateTimeZone.forID(UTC_8_DATE_TIME_ZONE)).toLocalDateTime().toDate();
    }

    public static Date parseDateTick(long value){
        return  new Date(value*1000);
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (date == null) return "";
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String getDateTimes() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }


    /**
     * 获取当前为第几周(周一至周日为一周)
     *
     * @param calendar
     * @return
     */
    public static Integer getWeekOfYear(Calendar calendar) {
        if (calendar == null) calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(year, 0, 1);
        int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek == Calendar.SUNDAY ? DAY_COUNT_EACH_WEEK : dayOfWeek - 1;
        int offSet = DAY_COUNT_EACH_WEEK - dayOfWeek + 1;
        int week = (calendar.get(Calendar.DAY_OF_YEAR) - offSet + DAY_COUNT_EACH_WEEK - 1) / DAY_COUNT_EACH_WEEK + 1;
        return week;
    }

    private final static Integer DAY_COUNT_EACH_WEEK = 7;
    private static final String UTC_8_DATE_TIME_ZONE = "Asia/Shanghai";
}
