package com.code.controller;

import java.util.List;

import com.code.bean.ResultData;
import com.code.model.JdbcConfig;
import com.code.model.Project;
import com.code.util.GCM_DB_util;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class TableInfoController extends BaseController {
	public void listTable() throws Exception {
		String projectId = getPara("projectId");
		Project project = Project.dao.findById(projectId);
		if (project.getJdbcConfigId() == null) {
			throw new Exception("项目没有配置数据源");
		}
		String tableName = getPara("tableName");

		JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
		DbPro dbPro = GCM_DB_util.getDb(project);
		List<Record> listTable = null;
		if (StrKit.notBlank(tableName)) {
			listTable = dbPro.find(
					"SELECT * FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ? and table_name like ?  ORDER BY table_name asc",
					jdbcConfig.getDbName(),"%"+tableName+"%");
		}else{
			listTable = dbPro.find(
					"SELECT * FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ?  ORDER BY table_name asc",
					jdbcConfig.getDbName());
		}
		System.out.println(listTable);
		renderJson(new ResultData().setData(listTable));
	}
}
