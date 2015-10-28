package org.apache.aphrodite.dao;

import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class JdbcImpl implements Jdbc {

    public int insert(PageView pv, Connection conn) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null ;
        try {
            pstmt = conn.prepareStatement(sql);
//            pstmt.e
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int update(PageView pv, Connection conn) {

        return 0;
    }

    public int delete(PageView pv, Connection conn) {

        return 0;
    }

    public int select(PageView pv, Connection conn) {

        return 0;
    }

    private void close(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            try {
                if(pstmt != null){
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e);
            }
        }
    }
}
