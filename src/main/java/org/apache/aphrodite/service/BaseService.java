package org.apache.aphrodite.service;

import org.apache.aphrodite.callback.Callback;
import org.apache.aphrodite.dataset.Dataset;

import java.util.List;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface BaseService {

    public <T> int save(String sql,T t) ;

    public <T> int update(String sql,T t) ;

    public <T> List<T> search(String sql,T t) ;

    public void  doService(Callback callback) ;
}