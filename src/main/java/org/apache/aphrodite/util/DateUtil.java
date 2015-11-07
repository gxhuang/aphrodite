package org.apache.aphrodite.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author huang.yuewen 2015年11月7日上午10:40:14
 *
 */
public abstract class DateUtil {


    public static Date toDate(String date){
        DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT) ;
        Date result = null ;
        try {
            result = df.parse(date) ;
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(),e) ;
        }
        return result ;
    }

    public static Date toDateTime(String date){
        DateFormat df = new SimpleDateFormat(Constants.DATETIME_FORMAT) ;
        Date result = null ;
        try {
            result = df.parse(date) ;
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(),e) ;
        }
        return result ;
    }
    
    public static String toString(String format, Date date){
        DateFormat df = new SimpleDateFormat(format) ;
        String result = null ;
        result = df.format(date) ;
        return result ;
    }

    public static String toString(String format, java.sql.Date date){
        DateFormat df = new SimpleDateFormat(format) ;
        String result = null ;
        result = df.format(new Date(date.getTime())) ;
        return result ;
    }

    public static void main(String[] args){
        String date = "2015-10-28 14:00:15" ;
        System.out.println(DateUtil.toDateTime(date));
    }

}