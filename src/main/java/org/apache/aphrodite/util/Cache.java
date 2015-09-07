package org.apache.aphrodite.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public abstract class Cache {

    private static Map<String,Map<String,String>>  cache = new HashMap<String, Map<String, String>>() ;

    public static Map<String,Map<String,String>> getCache(){
        return cache ;
    }

    public static void init(){
        //Ĭ��key��code,value��name
        Map<String,String> map = new HashMap<String, String>() ;
        map.put("yc","����") ;
        map.put("nh","����") ;
        map.put("dh","�»�") ;
        cache.put("location",map) ;
    }
}