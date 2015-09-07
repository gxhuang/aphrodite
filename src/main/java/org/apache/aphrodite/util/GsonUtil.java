package org.apache.aphrodite.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.aphrodite.dataset.PageView;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public abstract class GsonUtil {

    public static<T> T toObject(String json,Class<T> clazz){
        Gson gson = new Gson() ;
        T t = gson.fromJson(json, clazz) ;
//        Type type = new TypeToken<List<String>>(){}.getType();
        return t ;
    }

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
        String str ="[1,2,3]" ;
        Type type = new TypeToken<List<Integer>>(){}.getType() ;
        Collection<Integer> collection = GsonUtil.toList(str, type) ;
        for(Integer obj : collection){
            System.out.println(obj);
        }

    }
}
