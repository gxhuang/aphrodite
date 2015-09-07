package org.apache.aphrodite.mvc;

import com.google.gson.reflect.TypeToken;
import org.apache.aphrodite.util.Cache;
import org.apache.aphrodite.util.GsonUtil;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class CommonServlet extends AphroditeServlet {



    @Override
    public String doService(String message) {

        Type type = new TypeToken<List<String>>(){}.getType() ;
        Collection<String> keys = GsonUtil.toList(message, type) ;

        Map<String,Map<String,String>> datas = new HashMap<String, Map<String, String>>() ;

        for(String key:keys){
            if(Cache.get(key) != null){
                datas.put(key, Cache.get(key)) ;
            }

        }


//        Type rType = new TypeToken<Map<String,Map<String,String>>>(){}.getType() ;
        String json = GsonUtil.toJson(datas) ;

        return json;
    }
}
