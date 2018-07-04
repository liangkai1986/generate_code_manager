package com.code.util.SqlTypeUtil;


public interface ITypeConvert {
	 /**
     * <p>
     * 执行类型转换
     * </p>
     *
     * @param fieldType
     *            字段类型
     * @return
     */
    DbColumnType processTypeConvert(String fieldType);
}
