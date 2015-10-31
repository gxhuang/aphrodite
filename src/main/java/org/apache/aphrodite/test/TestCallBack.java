package org.apache.aphrodite.test;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.service.BaseService;
import org.apache.aphrodite.callback.Callback;

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

    public void doCall() {


//        baseService.save(new PageView()) ;
//        baseService.update(new PageView()) ;
        int factor = 0 ;
        int result = 1/0 ;
    }

    public Dataset getDataset() {
        return null;
    }

	public void doCall(String... pvName) {
		// TODO Auto-generated method stub
		
	}
}
