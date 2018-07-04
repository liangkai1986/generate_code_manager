package com.code.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.code.util.GenerateCodeUtils;
import com.jfinal.kit.StrKit;

public class TableInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public TableInfo() {}
	public TableInfo(Map<String, Object> baseTableInfoMap) {
		setBaseTableInfoMap(baseTableInfoMap);
	}
	
	/***
		select * from information_schema.TABLES
		where table_schema = 'generate_code_manager';
	 */
	private Map<String, Object> baseTableInfoMap ;//原始表信息
	private String tableName;
	private String tableComment;
	private String entityName;
	private String entityNameFirstUpperCase;
	private List<ColumnInfo> listColumnInfo;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public List<ColumnInfo> getListColumnInfo() {
		return listColumnInfo;
	}
	public void setListColumnInfo(List<ColumnInfo> listColumnInfo) {
		this.listColumnInfo = listColumnInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Map<String, Object> getBaseTableInfoMap() {
		return baseTableInfoMap;
	}
	public void setBaseTableInfoMap(Map<String, Object> baseTableInfoMap) {
		this.baseTableInfoMap = baseTableInfoMap;
		if(this.baseTableInfoMap!=null) {
			this.tableName = GenerateCodeUtils.getToStr(baseTableInfoMap.get("TABLE_NAME"));
			this.tableComment = GenerateCodeUtils.getToStr(baseTableInfoMap.get("TABLE_COMMENT"));
			this.entityName=GenerateCodeUtils.toCamelCase(this.tableName);
			this.entityNameFirstUpperCase =StrKit.firstCharToLowerCase(this.entityName);
		}
	}
	@Override
	public String toString() {
		return "TableInfo [baseTableInfoMap=" + baseTableInfoMap + ", tableName=" + tableName + ", tableComment="
				+ tableComment + ", entityName=" + entityName + ", listColumnInfo=" + listColumnInfo + "]";
	}
	public String getEntityNameFirstUpperCase() {
		return entityNameFirstUpperCase;
	}
	public void setEntityNameFirstUpperCase(String entityNameFirstUpperCase) {
		this.entityNameFirstUpperCase = entityNameFirstUpperCase;
	}
	
	
	
	
	
}
