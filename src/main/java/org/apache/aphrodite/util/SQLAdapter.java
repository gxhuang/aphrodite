package org.apache.aphrodite.util;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class SQLAdapter {

    private String sql ;

    public SQLAdapter(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}