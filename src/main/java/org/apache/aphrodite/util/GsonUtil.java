package org.apache.aphrodite.util;

import com.google.gson.Gson;
import org.apache.aphrodite.dataset.PageView;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public abstract class GsonUtil {

    public static PageView toPageView(String json){
        Gson gson = new Gson() ;
        PageView pv = gson.fromJson(json, PageView.class) ;
        return pv ;
    }

    public static String toJson(PageView pv){
        Gson gson = new Gson() ;
        return gson.toJson(pv) ;
    }
}
