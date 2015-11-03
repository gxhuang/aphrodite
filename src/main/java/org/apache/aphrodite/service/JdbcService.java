package org.apache.aphrodite.service;

import org.apache.aphrodite.callback.Callback;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日   15:33   huang.yuewen   Created.
 */
public interface JdbcService {

    public void save(PageView pv) ;

    public void update(PageView pv) ;

    public void select(PageView pv) ;

    public void delete(PageView pv) ;

    public void doService(Callback callback) ;
    
    public void commit() ;
    
    public void close();
    
    public void rollback();

}
