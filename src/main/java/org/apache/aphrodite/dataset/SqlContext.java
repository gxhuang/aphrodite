package org.apache.aphrodite.dataset;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class SqlContext {

    private String head ;

    private String tail ;

    private String[] fieldName ;

    public SqlContext(String head, String tail, String[] fieldName) {
        this.head = head;
        this.tail = tail;
        this.fieldName = fieldName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String[] getFieldName() {
        return fieldName;
    }

    public void setFieldName(String[] fieldName) {
        this.fieldName = fieldName;
    }
}