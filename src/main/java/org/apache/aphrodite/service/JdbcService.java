package org.apache.aphrodite.service;

import java.util.List;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.Record;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日   15:33   huang.yuewen   Created.
 */
public interface JdbcService {

    public void update(Dataset dataset) ;

    public List<Record> select(Dataset dataset) ;

}
