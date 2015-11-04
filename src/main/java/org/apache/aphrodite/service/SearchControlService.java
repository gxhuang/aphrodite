package org.apache.aphrodite.service;

import java.util.HashMap;

import org.apache.aphrodite.dataset.Form;
import org.apache.aphrodite.dataset.Grid;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Search;
import org.apache.aphrodite.util.ApplicationContextUtil;

public class SearchControlService {
	
	public void get(Search search){
		PageView pv = new PageView() ;
		pv.setFields(search.getFields());
		pv.setGrid(new Grid());
		pv.setForm(new Form());
		pv.getForm().setValues(new HashMap<String, String>());
		pv.getForm().getValues().put(search.getKey(), search.getCondition());
		pv.getField(search.getKey()).setOp(search.getOp());
		pv.setName(search.getTableName());
		
		JdbcService jdbcService = (JdbcService) ServiceProxyFactory.getInstance(ApplicationContextUtil.getApplicationContext().getBean("jdbcService",JdbcService.class)) ;
		jdbcService.select(pv);
		search.setRecords(pv.getGrid().getRecords());
	}

}
