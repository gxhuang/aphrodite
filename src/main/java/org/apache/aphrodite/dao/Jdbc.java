package org.apache.aphrodite.dao;

import org.apache.aphrodite.dataset.PageView;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public interface Jdbc {

    public int insert(PageView pv,Connection conn) ;

    public int update(PageView pv,Connection conn) ;

    public int delete(PageView pv,Connection conn) ;

    public int select(PageView pv,Connection conn) ;

}
