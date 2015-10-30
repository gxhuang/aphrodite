package org.apache.aphrodite.dao;

import org.apache.aphrodite.dataset.PageView;

import java.sql.*;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface JdbcDao {

//    public void setConnection(Connection conn) ;

    public void begin();

    public void rollback();

    public void commit();

    public void close();

    public int insert(PageView pv) ;

    public int update(PageView pv) ;

    public int delete(PageView pv) ;

    public void select(PageView pv) ;

}
