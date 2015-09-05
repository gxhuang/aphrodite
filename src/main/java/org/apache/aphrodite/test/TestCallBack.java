package org.apache.aphrodite.test;

import org.apache.aphrodite.dao.BaseDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.service.BaseService;
import org.apache.aphrodite.service.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class TestCallBack implements Callback {

    private BaseService baseService ;

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public void doCall(Dataset ds) {

        baseService.save(new PageView()) ;

//        int factor = 0 ;
//        int result = 1/0 ;
    }
}
