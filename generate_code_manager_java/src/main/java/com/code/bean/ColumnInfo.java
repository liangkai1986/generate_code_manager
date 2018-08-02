package com.code.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.code.util.GenerateCodeUtils;
import com.code.util.SqlTypeUtil.DbColumnType;
import com.code.util.SqlTypeUtil.MySqlTypeConvert;
import com.jfinal.kit.StrKit;

public class ColumnInfo {
	/**
	 * 这条sql查询出来的字段 SELECT FROM information_schema.COLUMNS WHERE table_schema =
	 * 'generate_code_manager' and TABLE_NAME='jdbc_config';
	 */
	private Map<String, Object> baseCoumnInfoMap;// 原始字段信息
	// private boolean keyFlag = false;// 是否是主键
	private String columnType;// 字段类型
	private String columnKey;// 字段主键key PRI主键约束；UNI唯一约束；MUL可以重复。
	private Integer columnTypeLength;// 字段类型长度
	private String columnComment;// 字段备注
	private String columnName;// 字段名称
	private boolean columnIsNull = false; // 字段是否可以为空 可以为空：true 不能为空：false
	private String entityType;// 实体类类型
	private String entityName;//实体类名称
	private String entityNameFirstUpperCase;//实体类名称首字母大写
	private ColumnConfig columnConfig;// 配置
	private List<String> listImportPkg = new ArrayList<String>();// 需要引入的package列表

	public ColumnInfo() {

	}

	@SuppressWarnings("unused")
	public ColumnInfo(Map<String, Object> baseCoumnInfoMap) {
		this.baseCoumnInfoMap = baseCoumnInfoMap;
		System.out.println("baseCoumnInfoMap:" + this.baseCoumnInfoMap);
		if (baseCoumnInfoMap != null) {
			this.columnName = String.valueOf(this.baseCoumnInfoMap.get("COLUMN_NAME"));
			this.entityName = GenerateCodeUtils.toCamelCase(this.columnName);
			this.entityNameFirstUpperCase =StrKit.firstCharToUpperCase(this.entityName);
			
			this.columnKey = String.valueOf(this.baseCoumnInfoMap.get("COLUMN_KEY"));
			this.columnIsNull = "YES"
					.equalsIgnoreCase(GenerateCodeUtils.getToStr(this.baseCoumnInfoMap.get("IS_NULLABLE")));
			// 根据这个map生成 对应的字段
			this.columnComment = GenerateCodeUtils.getToStr(this.baseCoumnInfoMap.get("COLUMN_COMMENT"));
			this.columnType = GenerateCodeUtils.getToStr(this.baseCoumnInfoMap.get("COLUMN_TYPE"));
			if (StringUtils.isNotBlank(columnType) && columnType.indexOf("(") != -1) {
				try {
					String columnTypeLengthStr = StringUtils.substringBetween(this.columnType, "(", ")");
					columnTypeLength = Integer.valueOf(columnTypeLengthStr);
				} catch (Exception e) {
					System.out.println("获取字段类型长度报错  不要让这个错影响主业务");
				}
			}
			DbColumnType dbColumnType = new MySqlTypeConvert().processTypeConvert(this.columnType);
			entityType = dbColumnType.getType();
			if (dbColumnType.getPkg() != null) {
				listImportPkg.add(dbColumnType.getPkg());
			}
		}
	}

	public List<String> getListImportPkg() {
		return listImportPkg;
	}

	public void setListImportPkg(List<String> listImportPkg) {
		this.listImportPkg = listImportPkg;
	}

	public Map<String, Object> getBaseCoumnInfoMap() {
		return baseCoumnInfoMap;
	}

	public void setBaseCoumnInfoMap(Map<String, Object> baseCoumnInfoMap) {
		this.baseCoumnInfoMap = baseCoumnInfoMap;
	}
	
	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityNameFirstUpperCase() {
		return entityNameFirstUpperCase;
	}

	public void setEntityNameFirstUpperCase(String entityNameFirstUpperCase) {
		this.entityNameFirstUpperCase = entityNameFirstUpperCase;
	}

	public Integer getColumnTypeLength() {
		return columnTypeLength;
	}

	public void setColumnTypeLength(Integer columnTypeLength) {
		this.columnTypeLength = columnTypeLength;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public boolean isColumnIsNull() {
		return columnIsNull;
	}

	public void setColumnIsNull(boolean columnIsNull) {
		this.columnIsNull = columnIsNull;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}

	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
		if (columnConfig != null) {
			listImportPkg.addAll(columnConfig.getListImportPkg());
			listImportPkg = new ArrayList<String>(new HashSet<String>(listImportPkg));
		}
	}

	@Override
	public String toString() {
		return "ColumnInfo [baseCoumnInfoMap=" + baseCoumnInfoMap + ", columnType=" + columnType + ", columnTypeLength="
				+ columnTypeLength + ", columnComment=" + columnComment + ", columnName=" + columnName
				+ ", columnIsNull=" + columnIsNull + ", entityType=" + entityType + ", columnConfig=" + columnConfig
				+ "]";
	}

}
