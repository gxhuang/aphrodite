package org.apache.aphrodite.service;

import org.apache.aphrodite.dataset.Dataset;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日   15:33   huang.yuewen   Created.
 */
public interface JdbcService {

    public void update(Dataset dataset) ;

    public Dataset select(Dataset dataset) ;

}
