package org.apache.aphrodite.dataset;

import java.util.List;
import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class Form {

//    private List<Field> fields ;

    private Map<String,String> values ;

    private String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }
}
