package com.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private Map<String, Object> baseTableInfoMap ;//原始表信息【上面这条sql查出来的信息】
	private String tableName;//表名称
	private String tableComment;//表备注
	private String entityName;//实体类名称
	private String entityNameFirstUpperCase;//实体类名称首字母大写
	private List<ColumnInfo> listColumnInfo;//列信息
	private List<String> listImportPkg=new ArrayList<String>();//需要引入的package列表
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
		if(this.listColumnInfo!=null&&this.listColumnInfo.size()>0) {
			Set<String> setTmp = new HashSet<String>();
			for (int i = 0; i < listColumnInfo.size(); i++) {
				setTmp.addAll(listColumnInfo.get(i).getListImportPkg());
			}
			listImportPkg.addAll(setTmp);
		}
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
			this.entityNameFirstUpperCase =StrKit.firstCharToUpperCase(this.entityName);
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
	public List<String> getListImportPkg() {
		return listImportPkg;
	}
	public void setListImportPkg(List<String> listImportPkg) {
		this.listImportPkg = listImportPkg;
	}
	
}
