package com.code.controller;

import java.util.ArrayList;
import java.util.List;

import com.code.bean.ResultData;
import com.code.model.Config;
import com.code.model.ConfigKeyVal;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

public class ConfigController extends BaseController {

	public void list() {
		List<Config> listConfig = Config.dao.find("select * from config where project_id=?", getPara("projectId"));
		if (listConfig != null && listConfig.size() > 0) {
			for (int i = 0; i < listConfig.size(); i++) {
				List<ConfigKeyVal> listConfigKeyVal = ConfigKeyVal.dao
						.find("select * from configKeyVal where config_id=?", listConfig.get(i).getConfigId());
				if (listConfigKeyVal != null) {
					listConfig.get(i).put("keyValList", listConfigKeyVal);
				} else {
					listConfig.get(i).put("keyValList", new ArrayList<Object>());
				}

			}
		}
		renderJson(new ResultData().setData(listConfig));
	}

	@Before(Tx.class)
	public void delConfig() {
		Config config = Config.dao.findById(getPara("id"));
		if(config==null) {
			renderJson(new ResultData().setMsg("查无数据，删除失败").setOk(false));
			return;
		}
		Db.delete("delete from config_record where project_id=? and config_id=?",config.getProjectId(),config.getConfigId());
		config.delete();
		renderJson(new ResultData());
	}

	public void delConfigKeyVal() {
		ConfigKeyVal configKeyVal = ConfigKeyVal.dao.findById(getPara("id"));
		if(configKeyVal==null) {
			renderJson(new ResultData().setMsg("查无数据，删除失败").setOk(false));
			return;
		}
		configKeyVal.delete();
		renderJson(new ResultData());
	}

	public void get() {
		renderJson(new ResultData().setData(Config.dao.findById(getPara("id"))));
	}

	public void saveUpdate() {
		if (StrKit.isBlank(getPara("code"))) {
			renderJson(new ResultData().setOk(false).setMsg("code不能为空"));
			return;
		}
		if (StrKit.isBlank(getPara("configName"))) {
			renderJson(new ResultData().setOk(false).setMsg("配置名称不能为空"));
			return;
		}
		if (StrKit.isBlank(getPara("inputType"))) {
			renderJson(new ResultData().setOk(false).setMsg("类型不能为空"));
			return;
		}
		if (StrKit.isBlank(getPara("projectId"))) {
			renderJson(new ResultData().setOk(false).setMsg("项目不能为空"));
			return;
		}

		Config config = new Config();
		config.setCode(getPara("code"));
		config.setConfigName(getPara("configName"));
		config.setInputType(getPara("inputType"));
		config.setProjectId(getParaToInt("projectId"));
		// 判断唯一性
		Config configTmp = Config.dao.findFirst("select * from config where project_id=? and `code`=?",
				config.getProjectId(), config.getCode());
		if (configTmp != null) {
			if (getPara("configId") == null) {
				renderJson(new ResultData().setOk(false).setMsg("code重复了"));
				return;
			} else if (configTmp.getConfigId() != getParaToInt("configId")) {
				renderJson(new ResultData().setOk(false).setMsg("code重复了"));
				return;
			}
		}
		if (getPara("configId") == null) {
			config.save();
		} else {
			config.setConfigId(getParaToInt("configId"));
			config.update();
		}

		renderJson(new ResultData());
	}

	public void configKeyValSaveUpdate() {
		if (StrKit.isBlank(getPara("name"))) {
			renderJson(new ResultData().setOk(false).setMsg("name不能为空"));
			return;
		}
		if (StrKit.isBlank(getPara("val"))) {
			renderJson(new ResultData().setOk(false).setMsg("val不能为空"));
			return;
		}
		if (StrKit.isBlank(getPara("configId"))) {
			renderJson(new ResultData().setOk(false).setMsg("configId不能为空"));
			return;
		}

		ConfigKeyVal configKeyVal = new ConfigKeyVal();
		configKeyVal.setConfigId(getParaToInt("configId"));
		configKeyVal.setName(getPara("name"));
		configKeyVal.setVal(getPara("val"));
		configKeyVal.setVal1(getPara("val1"));
		configKeyVal.setVal2(getPara("val2"));
		configKeyVal.setListImportPkg(getPara("listImportPkg"));
		// 判断唯一性
		if (getPara("id") == null) {
			configKeyVal.save();
		} else {
			configKeyVal.setId(getParaToInt("id"));
			configKeyVal.update();
		}
		renderJson(new ResultData());
	}
}