package org.apache.aphrodite.service;

import org.apache.aphrodite.dataset.Dataset;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface Callback {

    public void doCall(Dataset ds) ;
}
