package com.code.bean;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.code.util.GenerateCodeUtils;
import com.code.util.SqlTypeUtil.DbColumnType;
import com.code.util.SqlTypeUtil.MySqlTypeConvert;

public class ColumnInfo {
	/**
	 * 这条sql查询出来的字段 SELECT
	 *
	 * 
	 * FROM information_schema.COLUMNS WHERE table_schema = 'generate_code_manager'
	 * and TABLE_NAME='jdbc_config';
	 */
	private Map<String, Object> baseCoumnInfoMap;// 原始字段信息

	public ColumnInfo() {

	}

	@SuppressWarnings("unused")
	public ColumnInfo(Map<String, Object> baseCoumnInfoMap) {
		this.baseCoumnInfoMap = baseCoumnInfoMap;
		System.out.println("baseCoumnInfoMap:" + this.baseCoumnInfoMap);
		if (baseCoumnInfoMap != null) {
			this.columnName = String.valueOf(this.baseCoumnInfoMap.get("COLUMN_NAME"));
			this.keyFlag = this.baseCoumnInfoMap.get("EXTRA") != null;
			this.columnIsNull = "YES"
					.equalsIgnoreCase(GenerateCodeUtils.getToStr(this.baseCoumnInfoMap.get("COLUMN_COMMENT")));
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
			DbColumnType dbColumnType= new MySqlTypeConvert().processTypeConvert(this.columnType);
//			System.err.print(dbColumnType.name());
//			System.err.print(dbColumnType.getPkg());
//			System.err.println(dbColumnType.getType());
//			;
		}
	}

	private boolean keyFlag = false;// 是否是主键
	private String columnType;// 字段类型
	private Integer columnTypeLength;// 字段类型长度
	private String columnComment;// 字段备注
	private String columnName;
	private boolean columnIsNull = false; // 字段是否可以为空 可以为空：true 不能为空：false
	private String entityType;// 实体类类型

	@Override
	public String toString() {
		return "ColumnInfo [baseCoumnInfoMap=" + baseCoumnInfoMap + ", keyFlag=" + keyFlag + ", columnType="
				+ columnType + ", columnTypeLength=" + columnTypeLength + ", columnComment=" + columnComment + "]";
	}

	public Map<String, Object> getBaseCoumnInfoMap() {
		return baseCoumnInfoMap;
	}

	public void setBaseCoumnInfoMap(Map<String, Object> baseCoumnInfoMap) {
		this.baseCoumnInfoMap = baseCoumnInfoMap;
	}

	public boolean isKeyFlag() {
		return keyFlag;
	}

	public void setKeyFlag(boolean keyFlag) {
		this.keyFlag = keyFlag;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
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
	
}
