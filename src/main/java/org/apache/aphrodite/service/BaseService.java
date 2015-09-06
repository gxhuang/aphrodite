package org.apache.aphrodite.service;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;

import java.util.List;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public interface BaseService {

    public <T> int save(String sql,T t) ;

    public <T> int update(String sql,T t) ;

    public <T> List<T> search(String sql,T t) ;

    public void  doService(Callback callback,Dataset ds) ;
}
