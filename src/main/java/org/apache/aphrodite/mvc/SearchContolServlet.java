package org.apache.aphrodite.mvc;

import com.google.gson.reflect.TypeToken;

import org.apache.aphrodite.dataset.Search;
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
public class SearchContolServlet extends AphroditeServlet {



    @Override
    public String doService(String message) {

        Type type = new TypeToken<Search>(){}.getType() ;
        Search search = GsonUtil.toObject(message, Search.class) ;

        Map<String,Map<String,String>> datas = new HashMap<String, Map<String, String>>() ;

        

        String json = GsonUtil.toJson(datas) ;

        return json;
    }
}
