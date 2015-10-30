package org.apache.aphrodite.service;

import org.apache.aphrodite.callback.Callback;
import org.apache.aphrodite.dao.JdbcDao;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class JdbcServiceImpl implements JdbcService {

    private JdbcDao jdbcDao;

    public void begin(){
        jdbcDao.begin();
    }

    public void rollback(){
        jdbcDao.rollback();
    }

    public void commit(){
        jdbcDao.commit();
    }

    public void close(){
        jdbcDao.close();
    }

    public void save(PageView pv) {
        jdbcDao.insert(pv) ;
    }

    public void update(PageView pv) {
        jdbcDao.update(pv) ;
    }

    public void select(PageView pv) {
        jdbcDao.select(pv); ;
    }

    public void delete(PageView pv) {

    }

    public void doService(Callback callback) {
        try{
            begin();

            commit();
        }catch(Throwable e){
            rollback();
        }


    }
}
