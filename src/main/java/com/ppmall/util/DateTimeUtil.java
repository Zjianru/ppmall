package com.ppmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.util
 * 2019/1/15
 */
public class DateTimeUtil {


    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * String to Date in STANDARD_FORMAT
     * @param dateTimeStr String 类型 时间表示
     * @return Date
     */
    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * Date to String in STANDARD_FORMAT
     * @param date date类型时间
     * @return String类型转换完成
     */
    public static String dateToStr(Date date){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }


    /**
     * String to Date
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * Date to String
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

}
