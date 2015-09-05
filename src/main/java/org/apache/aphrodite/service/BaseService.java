package org.apache.aphrodite.service;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface BaseService {

    public int save(PageView pv) ;

    public int update(PageView pv) ;

    public int search(PageView pv) ;

    public void doService(Callback callback,Dataset ds) ;
}
