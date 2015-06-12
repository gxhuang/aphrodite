package org.apache.aphrodite.dataset;

import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class Record {

    private Map<String,String> recordVal ;

    private String status ;

    public Map<String, String> getRecordVal() {
        return recordVal;
    }

    public void setRecordVal(Map<String, String> recordVal) {
        this.recordVal = recordVal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
