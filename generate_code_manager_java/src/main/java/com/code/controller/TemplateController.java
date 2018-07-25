package com.code.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.code.bean.ColumnConfig;
import com.code.bean.ColumnInfo;
import com.code.bean.ResultData;
import com.code.bean.TableInfo;
import com.code.model.Config;
import com.code.model.ConfigKeyVal;
import com.code.model.ConfigRecord;
import com.code.model.JdbcConfig;
import com.code.model.Project;
import com.code.model.Template;
import com.code.util.FreemarkerUtil;
import com.code.util.GCM_DB_util;
import com.code.util.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class TemplateController extends BaseController {

	public void list() throws Exception {
		List<Template> dataList = Template.dao.find("select * from template where project_id = ?",
				getPara("projectId"));
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				// 获得已经关联的 configId
				List<Integer> configIdList = new ArrayList<Integer>();
				List<ConfigRecord> configRecordList = ConfigRecord.dao.find(
						"select * from config_record where template_id= ? and project_id=?",
						dataList.get(i).getTemplateId(), getPara("projectId"));
				if (configRecordList != null && configRecordList.size() > 0) {
					for (int j = 0; j < configRecordList.size(); j++) {
						configIdList.add(configRecordList.get(j).getConfigId());
					}
				}
				dataList.get(i).put("configIdList", configIdList);
				dataList.get(i).put("columnsList", new ArrayList<Map<String, Object>>());
				if (StrKit.notBlank(getPara("tableName"))) {
					String projectId = getPara("projectId");
					Project project = Project.dao.findById(projectId);
					String tableName = getPara("tableName");
					JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
					DbPro dbPro = GCM_DB_util.getDb(project);
					// 列信息
					List<Record> listBaseCoumnInfo = dbPro.find(
							"SELECT * FROM information_schema. COLUMNS WHERE table_schema = ? and TABLE_NAME=?",
							jdbcConfig.getDbName(), tableName);
					List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
					if (listBaseCoumnInfo != null && listBaseCoumnInfo.size() > 0) {
						Map<String, Object> columnMap = null;
						List<Config> configListTemp = Config.dao.find(
								"select * from config where config_id in(select config_id from config_record where template_id= ? and project_id=?)",
								dataList.get(i).getTemplateId(), getPara("projectId"));

						for (int j = 0; j < configListTemp.size(); j++) {
							List<ConfigKeyVal> configKeyValList = ConfigKeyVal.dao.find(
									"select * from configKeyVal where config_id =?",
									configListTemp.get(j).getConfigId());
							configListTemp.get(j).put("configKeyValList", configKeyValList);
							configListTemp.get(j).put("checkValList", new ArrayList<Object>());
							configListTemp.get(j).put("checkVal", "");
						}

						for (int k = 0; k < listBaseCoumnInfo.size(); k++) {
							columnMap = new HashMap<String, Object>();
							ColumnInfo columnInfo = new ColumnInfo(listBaseCoumnInfo.get(k).getColumns());
							columnMap.put("columnInfo", columnInfo);
							columnMap.put("configList", configListTemp);
							columnsList.add(columnMap);
						}
					}
					dataList.get(i).put("columnsList", columnsList);
				}
				// dataList.get(i).put("configRecordList", configRecordList);

			}
		}
		renderJson(new ResultData().setData(dataList));
	}

	@Before(Tx.class)
	public void del() {
		Template template = Template.dao.findById(getPara("templateId"));
		if (template == null) {
			renderJson(new ResultData().setMsg("查无数据，删除失败").setOk(false));
			return;
		}
		Db.delete("delete from config_record where project_id=? and template_id=?", template.getProjectId(),
				template.getTemplateId());
		template.delete();
		renderJson(new ResultData());
	}

	public void get() {
		// renderJson(new
		// ResultData().setData(JdbcConfig.dao.findById(getPara("id"))));
	}

	@Before(Tx.class)
	public void saveUpdate() {
		Template template = new Template();
		template.setName(getPara("name"));
		template.setProjectId(getParaToInt("project_id"));
		template.setPath(getPara("path"));
		template.setContent(getPara("content"));

		if (getPara("template_id") != null) {
			template.setTemplateId(getParaToInt("template_id"));
		}
		if (getPara("template_id") == null) {
			template.save();
			if (getPara("name") == null) {
				template.setName("模板-" + template.getTemplateId() + "");
			}
		}
		template.update();
		// 保存configIdList
		// 删除
		Db.delete("delete from config_record where  project_id=? and template_id=? ", template.getProjectId(),
				template.getTemplateId());
		if (getPara("configIdList") != null && StrKit.notBlank(getPara("configIdList"))) {
			// Db.delete("delete from config_record where project_id=? and template_id=? and
			// config_id not in (?)",
			// template.getProjectId(),template.getTemplateId(),getPara("configIdList"));
			String[] configIdList = getPara("configIdList").split(",");
			if (configIdList != null && configIdList.length > 0) {
				Set<String> setData = new HashSet<String>(Arrays.asList(configIdList));
				for (String data : setData) {
					ConfigRecord configRecord = new ConfigRecord();
					configRecord.setProjectId(template.getProjectId());
					configRecord.setConfigId(Integer.valueOf(data));
					configRecord.setTemplateId(template.getTemplateId());
					configRecord.save();
				}
			}
		}

		renderJson(new ResultData());
	}

	public void generateCode() throws Exception {
		Template template = Template.dao.findById(getPara("templateId"));
		// 数据
		TableInfo tableInfo = generateBean();
		FreemarkerUtil.createFile(template.getContent(), tableInfo,
				FreemarkerUtil.createStr(template.getPath(), tableInfo));
		renderJson(new ResultData());
	}

	private TableInfo generateBean() throws Exception {

		String projectId = getPara("projectId");

		Project project = Project.dao.findById(projectId);

		String tableName = getPara("tableName");

		JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
		DbPro dbPro = GCM_DB_util.getDb(project);

		TableInfo tableInfo = new TableInfo();

		// 表信息
		Record baseTableInfo = dbPro.findFirst(
				"SELECT * FROM INFORMATION_SCHEMA.tables WHERE TABLE_SCHEMA = ? and table_name = ?  ORDER BY table_name asc",
				jdbcConfig.getDbName(), tableName);

		tableInfo.setBaseTableInfoMap(baseTableInfo.getColumns());

		// 列信息
		List<Record> listBaseCoumnInfo = dbPro.find(
				"SELECT * FROM information_schema. COLUMNS WHERE table_schema = ? and TABLE_NAME=?",
				jdbcConfig.getDbName(), tableName);

		List<ColumnInfo> listColumnInfo = new ArrayList<ColumnInfo>();
		if (listBaseCoumnInfo != null && listBaseCoumnInfo.size() > 0) {
			for (int i = 0; i < listBaseCoumnInfo.size(); i++) {
				ColumnInfo columnInfo = new ColumnInfo(listBaseCoumnInfo.get(i).getColumns());
				// 设置一些配置的属性
				setColumnInfoConfig(columnInfo);

				listColumnInfo.add(columnInfo);
			}
		}
		tableInfo.setListColumnInfo(listColumnInfo);
		System.err.println("tableInfo:"+GsonUtil.toJson(tableInfo));
		return tableInfo;
	}

	// 设置 字段 config配置
	@SuppressWarnings("unchecked")
	public void setColumnInfoConfig(ColumnInfo columnInfo) {
		String columnsListStr = getPara("columnsListStr");

		if (StrKit.notBlank(columnsListStr)) {
			List<Map<String, Object>> columnsList = GsonUtil.fromJson(columnsListStr,
					new TypeToken<List<Map<String, Object>>>() {
					}.getType());
			if (columnsList != null) {
				for (int i = 0; i < columnsList.size(); i++) {
					Map<String, Object> columnsMap = columnsList.get(i);
					ColumnInfo columnInfoTmp = GsonUtil.fromJson(GsonUtil.toJson(columnsMap.get("columnInfo")), ColumnInfo.class);
					if (columnInfo != null
							&& columnInfo.getColumnName().equalsIgnoreCase(columnInfoTmp.getColumnName())) {
						List<Map<String, Object>> configList = (List<Map<String, Object>>) columnsMap.get("configList");
						columnInfo.setColumnConfig(new ColumnConfig(configList));
					}
				}
			}
		}
	}
}