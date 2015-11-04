package org.apache.aphrodite.service;

import org.apache.aphrodite.dao.JdbcDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.util.SqlType;

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

	public void update(Dataset dataset) {
		for(PageView pv : dataset.getPvs()){
			if (pv.exsits(SqlType.INSERT)) {
				jdbcDao.insert(pv);
			}
			if (pv.exsits(SqlType.UPDATE)) {
				jdbcDao.update(pv);
			}

			if (pv.exsits(SqlType.DELETE)) {
				jdbcDao.delete(pv);
			}
		}
		
	}

	public void select(Dataset dataset) {
		for(PageView pv : dataset.getPvs()){
			jdbcDao.select(pv);
		}
	}
}
