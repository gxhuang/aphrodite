package org.apache.aphrodite.service;

import org.apache.aphrodite.dao.JdbcDao;
import org.apache.aphrodite.dataset.PageView;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 *          <p>
 *          History: 2015年05月07日 15:33 huang.yuewen Created.
 */
public class JdbcServiceImpl implements JdbcService {

	private JdbcDao jdbcDao;

	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public void save(PageView pv) {
		jdbcDao.insert(pv);
	}

	public void update(PageView pv) {
		jdbcDao.update(pv);
	}

	public void select(PageView pv) {
		jdbcDao.select(pv);
	}

	public void delete(PageView pv) {

	}



}
