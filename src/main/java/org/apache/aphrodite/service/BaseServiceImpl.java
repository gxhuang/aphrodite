package org.apache.aphrodite.service;

import org.apache.aphrodite.dao.BaseDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class BaseServiceImpl implements BaseService {

    private BaseDao baseDao ;

    private PlatformTransactionManager txManager ;

    public void setTxManager(PlatformTransactionManager txManager){
        this.txManager = txManager ;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public int save(PageView pv) {
        System.out.println("save");

        Map<String,String> map = new HashMap<String,String>() ;
        map.put("userId","1111") ;
        map.put("userName","1111") ;
        map.put("password", "1111") ;
        baseDao.insert("sysuserinsert",map);
        return 0;
    }

    public int update(PageView pv) {
        System.out.println("update");

        return 0;
    }

    public int search(PageView pv) {
        System.out.println("search");

        return 0;
    }

    public void doService(Callback callback,Dataset ds) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition() ;
        def.setName("tx");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def) ;
        try{
            callback.doCall(ds);
        }catch(Throwable t){
            txManager.rollback(txStatus);
            //throw exception
            throw new RuntimeException(t.getMessage(),t.getCause());
        }

        txManager.commit(txStatus);
    }
}
