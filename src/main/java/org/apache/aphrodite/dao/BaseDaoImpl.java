package org.apache.aphrodite.dao;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
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