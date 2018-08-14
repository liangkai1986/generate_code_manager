package com.code.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.code.util.GenerateCodeUtils;
import com.jfinal.kit.StrKit;

public class TableInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public TableInfo() {
	}

	public TableInfo(Map<String, Object> baseTableInfoMap) {
		setBaseTableInfoMap(baseTableInfoMap);
	}

	/***
	 * select * from information_schema.TABLES where table_schema =
	 * 'generate_code_manager';
	 */
	private Map<String, Object> baseTableInfoMap;// 原始表信息【上面这条sql查出来的信息】
	private String tableName;// 表名称
	private String tableComment;// 表备注
	private String entityName;// 实体类名称
	private String entityNameFirstUpperCase;// 实体类名称首字母大写
	private List<ColumnInfo> listColumnInfo;// 列信息
	private List<String> listImportPkg = new ArrayList<String>();// 需要引入的package列表
	private List<String> listConfigImportPkg = new ArrayList<String>();// config需要引入的package列表

	private String keyColumnName;// 主键 字段名称
	private String keyColumnComment;// 主键 字段备注
	private String keyEntityName;// 主键 实体类名称
	private String keyEntityNameFirstUpperCase;// 主键 实体类名称首字母大写
	private String keyExtra;// 主键 类型 auto_increment:主键自增

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
		if (this.listColumnInfo != null && this.listColumnInfo.size() > 0) {
			for (int i = 0; i < listColumnInfo.size(); i++) {
				if ("PRI".equalsIgnoreCase(listColumnInfo.get(i).getColumnKey())) {
					this.keyColumnName = listColumnInfo.get(i).getColumnName();// 主键 字段名称
					this.keyColumnComment = listColumnInfo.get(i).getColumnComment();// 主键 字段备注
					this.keyEntityName = listColumnInfo.get(i).getEntityName();// 主键 实体类名称
					this.keyEntityNameFirstUpperCase = listColumnInfo.get(i).getEntityNameFirstUpperCase();// 主键
					this.keyExtra = String.valueOf(listColumnInfo.get(i).getBaseCoumnInfoMap().get("EXTRA"));// 主键 类型
				}
				listImportPkg.addAll(listColumnInfo.get(i).getListImportPkg());
				listConfigImportPkg.addAll(listColumnInfo.get(i).getListConfigImportPkg());
			}
			listConfigImportPkg = new ArrayList<String>(new HashSet<String>(listConfigImportPkg));
			listImportPkg = new ArrayList<String>(new HashSet<String>(listImportPkg));
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
		if (this.baseTableInfoMap != null) {
			this.tableName = GenerateCodeUtils.getToStr(baseTableInfoMap.get("TABLE_NAME"));
			this.tableComment = GenerateCodeUtils.getToStr(baseTableInfoMap.get("TABLE_COMMENT"));
			this.entityName = GenerateCodeUtils.toCamelCase(this.tableName);
			this.entityNameFirstUpperCase = StrKit.firstCharToUpperCase(this.entityName);
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

	public String getKeyColumnName() {
		return keyColumnName;
	}

	public void setKeyColumnName(String keyColumnName) {
		this.keyColumnName = keyColumnName;
	}

	public String getKeyColumnComment() {
		return keyColumnComment;
	}

	public void setKeyColumnComment(String keyColumnComment) {
		this.keyColumnComment = keyColumnComment;
	}

	public String getKeyEntityName() {
		return keyEntityName;
	}

	public void setKeyEntityName(String keyEntityName) {
		this.keyEntityName = keyEntityName;
	}

	public String getKeyEntityNameFirstUpperCase() {
		return keyEntityNameFirstUpperCase;
	}

	public void setKeyEntityNameFirstUpperCase(String keyEntityNameFirstUpperCase) {
		this.keyEntityNameFirstUpperCase = keyEntityNameFirstUpperCase;
	}

	public String getKeyExtra() {
		return keyExtra;
	}

	public void setKeyExtra(String keyExtra) {
		this.keyExtra = keyExtra;
	}

	public List<String> getListConfigImportPkg() {
		return listConfigImportPkg;
	}

	public void setListConfigImportPkg(List<String> listConfigImportPkg) {
		this.listConfigImportPkg = listConfigImportPkg;
	}
	
}
