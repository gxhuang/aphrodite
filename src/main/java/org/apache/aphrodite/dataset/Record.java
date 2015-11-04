package org.apache.aphrodite.dataset;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Record {

    private Map<String,String> recordVal ;

    //status的取值与sqlType一致，但并不会取到select值
    private String status ;

    public Map<String, String> getRecordVal() {
        return recordVal;
    }

    public void setRecordVal(Map<String, String> recordVal) {
        this.recordVal = recordVal;
    }

    public void addRecordVal(String key,String value){
        if(recordVal == null){
            recordVal = new HashMap<String, String>() ;
        }
        recordVal.put(key,value) ;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
