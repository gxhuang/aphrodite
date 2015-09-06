package org.apache.aphrodite.test;

import org.apache.aphrodite.dao.BaseDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.service.BaseService;
import org.apache.aphrodite.service.Callback;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class TestCallBack implements Callback {

    private BaseService baseService ;

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    public void doCall(Dataset ds) {


//        baseService.save(new PageView()) ;
//        baseService.update(new PageView()) ;
        int factor = 0 ;
        int result = 1/0 ;
    }
}
