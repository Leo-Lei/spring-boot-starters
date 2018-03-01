package com.leibangzhu.starters.common.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Author: lili
 * Date: 2017/3/2
 * Time: 14:38
 */
public final class DateTime {

    private GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

    public DateTime(int year, int month, int day){

        calendar.set(year, month, day);
    }

    public DateTime(int year, int month, int day, int hour, int minute, int second){

        calendar.set(year, month, day, hour, minute, second);
    }

    public DateTime(Date date){

        calendar.setTime(date);
    }

    public static DateTime now(){

        return new DateTime(new Date());
    }

    public static DateTime parse(String dateString, String... pattern) throws ParseException {

        return new DateTime(DateUtils.parseDate(dateString, pattern));
    }

    public String toLongDateString(){

        return DateFormatUtils.format(calendar, "dddd, MMMM dd, yyyy");
    }

    public String toLongTimeString(){

        return DateFormatUtils.format(calendar, "h:mm:ss tt");
    }

    public String toShortDateString(){

        return DateFormatUtils.format(calendar, "yyyy-M-d");
    }

    public String toShortTimeString(){

        return DateFormatUtils.format(calendar, "h:mm tt");
    }

    public String toFullDateTimeString(){

        return DateFormatUtils.format(calendar, "yyyy-M-d h:mm:ss tt");
    }

    public String toString(String pattern){

        return DateFormatUtils.format(calendar, pattern);
    }

    public boolean isLeapYear(){

        int year = calendar.get(Calendar.YEAR);

        if ((year % 4 == 0) && year % 100 != 0)
        {
            return true;
        }
        else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void addYears(int value){

        calendar.add(Calendar.YEAR, value);
    }

    public void addMonths(int value){

        calendar.add(Calendar.MONTH, value);
    }

    public void addDays(int value){

        calendar.add(Calendar.DATE, value);
    }

    public void addHours(int value){

        calendar.add(Calendar.HOUR, value);
    }

    public void addMinutes(int value){

        calendar.add(Calendar.MINUTE, value);
    }

    public void addSeconds(int value){

        calendar.add(Calendar.SECOND, value);
    }

    public Date toDate(){

        return calendar.getTime();
    }

    public int compareTo(DateTime right){

        return calendar.compareTo(right.calendar);
    }

    public boolean after(DateTime right){

        return calendar.after(right.calendar);
    }

    public boolean before(DateTime right){

        return calendar.before(right.calendar);
    }

    public static DateTime min() {

        return new DateTime(1970, 1, 1);
    }

    public static DateTime max() {

        return new DateTime(2100, 12, 31);
    }

    public int getYear() {

        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {

        return calendar.get(Calendar.MONTH);
    }

    public int getDay() {

        return calendar.get(Calendar.DATE);
    }

    public String toTimeTickString() {

        return String.valueOf(calendar.getTime().getTime());
    }

    public DateTime clone(){

        return new DateTime(calendar.getTime());
    }

    public long getPeroidTo(DateTime dateTime, TimeUnits unit){

        long microSeconds = dateTime.toDate().getTime() - this.toDate().getTime();

        if(microSeconds < 0){

            microSeconds *= -1;
        }

        switch (unit){

            case DAYS:

                return TimeUnit.MILLISECONDS.toDays(microSeconds);
            case HOURS:

                return TimeUnit.MILLISECONDS.toHours(microSeconds);
            case MINUTES:

                return TimeUnit.MILLISECONDS.toMinutes(microSeconds);
            case SECONDS:
            default:

                return TimeUnit.MILLISECONDS.toSeconds(microSeconds);
        }
    }
}
