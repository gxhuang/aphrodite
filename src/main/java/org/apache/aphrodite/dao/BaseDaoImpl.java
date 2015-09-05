package org.apache.aphrodite.dao;

import org.apache.aphrodite.exception.DaoException;
import org.apache.ibatis.session.SqlSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class BaseDaoImpl implements BaseDao {

    private SqlSession sqlSession ;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public <T> int insert(String sql, T param) {
        int result = sqlSession.insert(sql,param) ;
        return result;
    }

    public <T> int update(String sql, T param) {
        return sqlSession.update(sql,param);
    }

    public <T> List<T> select(String sql, T param) {
        return sqlSession.selectList(sql,param);
    }

    public <T> int delete(String sql, T param) {
        return sqlSession.delete(sql,param);
    }

}
