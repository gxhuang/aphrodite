package org.apache.aphrodite.mvc;

import com.google.gson.reflect.TypeToken;
import org.apache.aphrodite.util.GsonUtil;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class CommonServlet extends AphroditeServlet {



    @Override
    public String doService(String message) {

        Type type = new TypeToken<List<String>>(){}.getType() ;
        Collection<String> names = GsonUtil.toList(message, type) ;

        Map<String,Map<String,String>> datas = new HashMap<String, Map<String, String>>() ;

        Type rType = new TypeToken<Map<String,Map<String,String>>>(){}.getType() ;
        String json = GsonUtil.toJson(datas,rType) ;

        return json;
    }
}