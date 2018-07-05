package com.code.controller;

import java.util.ArrayList;
import java.util.List;

import com.code.bean.ColumnInfo;
import com.code.bean.ResultData;
import com.code.bean.TableInfo;
import com.code.model.JdbcConfig;
import com.code.model.Project;
import com.code.model.Template;
import com.code.util.FreemarkerUtil;
import com.code.util.GCM_DB_util;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;

public class TemplateController extends BaseController {

	public void list() {
		renderJson(new ResultData().setData(Template.dao.find("select * from template where project_id = ?",getPara("projectId"))));
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
		Template template = new Template();
		template.setName(getPara("name"));
		template.setProjectId(getParaToInt("project_id"));
		template.setPath(getPara("path"));
		template.setContent(getPara("content"));
		
		if(getPara("template_id")!=null){
			template.setTemplateId(getParaToInt("template_id"));
		}
		if(getPara("template_id") == null){
			template.save();
			if(getPara("name")==null){
				template.setName("模板-"+template.getTemplateId()+"");
			}
		}
		template.update();
		renderJson(new ResultData());
	}

	public void generateCode() throws Exception {
		Template template = Template.dao.findById(getPara("templateId"));
		// 数据
		TableInfo tableInfo = generateBean();
		FreemarkerUtil.createFile(template.getContent(), tableInfo,FreemarkerUtil.createStr(template.getPath(), tableInfo));
		renderJson(new ResultData());
	}

	private TableInfo generateBean() throws Exception {

		String projectId = getPara("projectId");

		Project project = Project.dao.findById(projectId);

		String tableName = getPara("tableName");

		JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
		DbPro dbPro = GCM_DB_util.getDb(project);

		TableInfo tableInfo = new TableInfo();
		
		//表信息
		Record baseTableInfo = dbPro.findFirst(
				"SELECT * FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ? and table_name = ?  ORDER BY table_name asc",
				jdbcConfig.getDbName(), tableName);

		tableInfo.setBaseTableInfoMap(baseTableInfo.getColumns());

		
		
		//列信息
		List<Record> listBaseCoumnInfo = dbPro.find(
				"SELECT * FROM information_schema. COLUMNS WHERE table_schema = ? and TABLE_NAME=?",
				jdbcConfig.getDbName(), tableName);

		
		List<ColumnInfo> listColumnInfo = new ArrayList<ColumnInfo>();
		if (listBaseCoumnInfo != null && listBaseCoumnInfo.size() > 0) {
			for (int i = 0; i < listBaseCoumnInfo.size(); i++) {
				ColumnInfo ColumnInfo = new ColumnInfo(listBaseCoumnInfo.get(i).getColumns());
				listColumnInfo.add(ColumnInfo);
			}
		}
		tableInfo.setListColumnInfo(listColumnInfo);
		
		System.out.println("tableInfo:"+tableInfo);
		return tableInfo;
	}
}