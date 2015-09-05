package org.apache.aphrodite.dao;

import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public interface BaseDao {

    public <T> int insert(String sql,T param );

    public <T> int update(String sql,T param);

    public <T> List<T> select(String sql,T param) ;

    public <T> int delete(String sql,T param) ;




}
