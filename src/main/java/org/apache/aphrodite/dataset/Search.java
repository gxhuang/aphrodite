package org.apache.aphrodite.dataset;

import java.util.List;

public class Search {
	
	private List<Field> fields ;
	
	private String tableName ;
	
	private Boolean isTreeGrid ;
	
	/**
	 * 如果是多个字段，请用逗号分开
	 */
	private String key ; 
	
	private String op ;
	
	private String condition ;
	
	private List<Record> records ;
	
	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

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
