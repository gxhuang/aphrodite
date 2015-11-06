package org.apache.aphrodite.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015月05月07日 15:33   huang.yuewen   Created.
 */
public abstract class Cache {

    private static Map<String,Map<String,String>>  cache = new HashMap<String, Map<String, String>>() ;

    public static Map<String,Map<String,String>> getCache(){
        return cache ;
    }

    public static Map<String,String> get(String key){

        if(cache.size() == 0){
            init() ;
        }

        return cache.get(key) ;
    }

    public static void init(){
        //
        Map<String,String> map = new HashMap<String, String>() ;
        map.put("00","无效") ;
        map.put("01","有效") ;
        cache.put("valid",map) ;
    }
}
