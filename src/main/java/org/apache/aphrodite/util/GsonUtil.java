package org.apache.aphrodite.util;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;

/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:17:06
 *
 */
public abstract class GsonUtil {

    public static<T> T toObject(String json,Class<T> clazz){
        Gson gson = new Gson() ;
        T t = gson.fromJson(json, clazz) ;
        return t ;
    }

    @SuppressWarnings("rawtypes")
	public static Collection toList(String json, Type type) {
        Gson gson = new Gson() ;
        Collection collection = gson.fromJson(json, type) ;
        return collection ;
    }

    public static <T> String toJson(T t){
        Gson gson = new Gson() ;
        return gson.toJson(t) ;
    }

    public static <T> String toJson(T t, Type type){
        Gson gson = new Gson() ;
        return gson.toJson(t,type) ;
    }

    public static void main(String[] args){
//        String str ="[1,2,3]" ;
//        Type type = new TypeToken<List<Integer>>(){}.getType() ;
//        Collection<Integer> collection = GsonUtil.toList(str, type) ;
//        for(Integer obj : collection){
//            System.out.println(obj);
//        }

    }
}
