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
		for(PageView pv : dataset.getPageViews()){
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

	/**
	 * 每次只查询一个表的数据，主表、子表情况通过异步实现
	 */
	public Dataset select(Dataset dataset) {
		Dataset result = new Dataset() ;
		for(PageView pv : dataset.getPageViews()){
			jdbcDao.select(pv);
		}
		result.setPageViews(dataset.getPageViews());
		
		for(PageView pv : result.getPageViews()){
			pv.setFields(null);
		}
		
		return result ;
	}
}
