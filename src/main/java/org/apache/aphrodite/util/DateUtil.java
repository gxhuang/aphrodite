package org.apache.aphrodite.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
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