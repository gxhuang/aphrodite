package org.apache.aphrodite.util;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class Constants {

    public static final String DATE_FORMAT = "yyyy-MM-dd" ;

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss" ;

    public static final String INSERT_FORMAT = "INSERT INTO %1s(%2s) VALUES(%3s)";

    public static final String UPDATE_FORMAT = "UPDATE %1s SET %2s WHERE %3s";

    public static final String SELECT_FORMAT = "SELECT %1s FROM %2s WHERE %3s";
    
    public static final String INSERT = "INSERT" ;
    
    public static final String DELETE = "DELETE" ;
    
    public static final String UPDATE = "UPDATE" ;
    
    public static final String SELECT = "SELECT" ;

}
