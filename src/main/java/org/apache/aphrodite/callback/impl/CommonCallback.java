package org.apache.aphrodite.callback.impl;

import org.apache.aphrodite.callback.Callback;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.service.JdbcService;


/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class CommonCallback implements Callback {

    private Dataset ds ;

    private JdbcService jdbcService ;


    /**
     * 取pageView name
     * @param pvName
     */
    public void doCall(String ... pvName) {


    }

    private void save(PageView pv){
        jdbcService.save(pv);
    }
}
