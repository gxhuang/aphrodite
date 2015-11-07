package org.apache.aphrodite.service;

import java.util.Date;

import org.apache.aphrodite.dao.JdbcDao;
import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Record;
import org.apache.aphrodite.util.Constants;
import org.apache.aphrodite.util.DateUtil;
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
				setCommonField(SqlType.INSERT,pv) ;
				jdbcDao.insert(pv);
			}
			if (pv.exsits(SqlType.UPDATE)) {
				setCommonField(SqlType.UPDATE,pv) ;
				jdbcDao.update(pv);
			}

			if (pv.exsits(SqlType.DELETE)) {
				setCommonField(SqlType.DELETE,pv) ;
				jdbcDao.delete(pv);
			}
		}		
	}
	
	private void setCommonField(SqlType sqlType,PageView pv){
		if(Constants.INSERT.equals(sqlType.name())){
			Field createdDate =  new Field();
			createdDate.setFormat(Constants.DATETIME_FORMAT);
			createdDate.setDataType("date");
			createdDate.setName("createdDate");
			pv.getFields().add(createdDate) ;
			
			Field rowStatus =  new Field();
			rowStatus.setDataType("String");
			rowStatus.setName("rowStatus");
			pv.getFields().add(rowStatus) ;
			
			for(Record record : pv.getGrid().getRecords()){
				record.getRecordVal().put("createdDate", DateUtil.toString(Constants.DATETIME_FORMAT, new Date())) ;
				record.getRecordVal().put("rowStatus", "01") ;
			}
			
//			Field createdUser =  new Field();
//			createdUser.setDataType("string");
//			createdUser.setName("createdUser");
		}else if(Constants.UPDATE.equals(sqlType.name()) || Constants.DELETE.equals(sqlType.name())){
			Field updatedDate =  new Field();
			updatedDate.setFormat(Constants.DATETIME_FORMAT);
			updatedDate.setDataType("date");
			updatedDate.setName("updatedDate");
			pv.getFields().add(updatedDate) ;
			for(Record record : pv.getGrid().getRecords()){
				record.getRecordVal().put("updatedDate", DateUtil.toString(Constants.DATETIME_FORMAT, new Date())) ;
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
