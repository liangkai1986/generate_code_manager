package com.code.bean;

import java.util.Map;

public class ColumnInfo {
			/** 这条sql查询出来的字段
			  SELECT
			 
				*
			FROM
				information_schema.COLUMNS
			WHERE
				table_schema = 'generate_code_manager' and TABLE_NAME='jdbc_config';
				*/
		private Map<String, Object> baseCoumnInfoMap ;//原始字段信息
		public ColumnInfo() {
			
		}
		public ColumnInfo(Map<String, Object> baseCoumnInfoMap) {
			this.baseCoumnInfoMap=baseCoumnInfoMap;
			System.out.println(this.baseCoumnInfoMap);
			if(baseCoumnInfoMap!=null) {
				//根据这个map生成 对应的字段
			}
		}
		private boolean keyFlag = false;//是否是主键
		private String columnType;//字段类型
		private Integer columnTypelength;//字段类型长度
		private String columnComment;//字段备注
		@Override
		public String toString() {
			return "ColumnInfo [baseCoumnInfoMap=" + baseCoumnInfoMap + ", keyFlag=" + keyFlag + ", columnType="
					+ columnType + ", columnTypelength=" + columnTypelength + ", columnComment=" + columnComment + "]";
		}
		
		
		
		
		
}
