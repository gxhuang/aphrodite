package org.apache.aphrodite.dataset;

import java.util.List;

public class Search {
	
	private List<Field> fields ;
	
	private String tableName ;
	
	private Boolean isTreeGrid ;
	
	private String condition ;
	
	private List<Record> records ;
	
	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getIsTreeGrid() {
		return isTreeGrid;
	}

	public void setIsTreeGrid(Boolean isTreeGrid) {
		this.isTreeGrid = isTreeGrid;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	

}
