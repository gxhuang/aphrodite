package org.apache.aphrodite.service;

import org.apache.aphrodite.callback.Callback;
import org.apache.aphrodite.dao.BaseDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.exception.ServiceException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

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

    public <T> int  save(String sql,T t) {
        return baseDao.insert(sql,t);
    }

    public <T> int update(String sql,T t) {
        return baseDao.update(sql,t);
    }

    public <T> List<T> search(String sql,T t) {
        return baseDao.select(sql,t);
    }

    public void doService(Callback callback) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition() ;
        def.setName("tx");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(def) ;
        try{
//            Dataset ds = callback.getDataset() ;
            callback.doCall();
        }catch(Throwable t){
            txManager.rollback(txStatus);
            //throw exception
            throw new ServiceException(t.getMessage(),t.getCause());
        }

        txManager.commit(txStatus);
    }
}
