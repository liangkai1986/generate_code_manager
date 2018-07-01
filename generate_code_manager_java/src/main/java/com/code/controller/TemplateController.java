package com.code.controller;

import java.util.HashMap;

import com.code.bean.ResultData;
import com.code.model.JdbcConfig;
import com.code.model.Project;
import com.code.model.Template;
import com.code.util.FreemarkerUtil;
import com.code.util.GCM_DB_util;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class TemplateController extends BaseController {

	public void list() {
		renderJson(new ResultData().setData(Template.dao.find("select * from template")));
	}

	public void del() {
		// JdbcConfig.dao.deleteById(getPara("id"));
		// renderJson(new ResultData());
	}

	public void get() {
		// renderJson(new
		// ResultData().setData(JdbcConfig.dao.findById(getPara("id"))));
	}

	public void saveUpdate() {

	}

	public void generateCode() throws Exception {
		String projectId = getPara("projectId");
		
		Project project = Project.dao.findById(projectId);

		String tableName = getPara("tableName");

		JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
		DbPro dbPro = GCM_DB_util.getDb(project);
		Record record = dbPro.findFirst(
				"SELECT * FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ? and table_name = ?  ORDER BY table_name asc",
				jdbcConfig.getDbName(), tableName );
		System.out.println(record);
//		renderJson(new ResultData().setData(record));
		
		Template template = Template.dao.findById(getPara("templateId"));
		String aString = FreemarkerUtil.createStr(template.getContent(), new HashMap<String, String>());
		System.out.println("ddd"+aString);

		renderJson(new ResultData());
	}
}