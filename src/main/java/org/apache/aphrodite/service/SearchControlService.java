package org.apache.aphrodite.service;

import org.apache.aphrodite.dataset.Grid;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Search;

public abstract class SearchControlService {
	
	public void get(Search search){
		PageView pv = new PageView() ;
		pv.setFields(search.getFields());
		pv.setGrid(new Grid());
//		aphrodi
		
		JdbcService jdbcService = new JdbcServiceImpl() ;
		jdbcService.select(pv);
		search.setRecords(pv.getGrid().getRecords());
	}

}
