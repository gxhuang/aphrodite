package org.apache.aphrodite.callback;

import org.apache.aphrodite.dataset.Dataset;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public interface Callback {

    public Dataset getDataset() ;

    public void doCall(Dataset ds) ;
}

