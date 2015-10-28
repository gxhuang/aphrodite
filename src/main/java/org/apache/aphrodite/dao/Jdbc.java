package org.apache.aphrodite.dao;

import org.apache.aphrodite.dataset.PageView;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface Jdbc {

    public int insert(PageView pv,Connection conn) ;

    public int update(PageView pv,Connection conn) ;

    public int delete(PageView pv,Connection conn) ;

    public int select(PageView pv,Connection conn) ;

}