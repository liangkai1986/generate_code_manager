package com.code.controller;

import com.code.bean.ResultData;
import com.code.model.Config;

public class ConfigController extends BaseController{
	
	public void list() {
		renderJson(new ResultData().setData(Config.dao.find("select * from config where project_id=?",getPara("projectId"))));
	}
	public void del() {
		Config.dao.deleteById(getPara("id"));
		renderJson(new ResultData());
	}
	public void get() {
		renderJson(new ResultData().setData(Config.dao.findById(getPara("id"))));
	}
	public void saveUpdate(){
//		JdbcConfig jdbcConfig = new JdbcConfig();
//		
//		jdbcConfig.setName(getPara("name"));
//		jdbcConfig.setDbName(getPara("db_name"));
//		jdbcConfig.setUser(getPara("user"));
//		jdbcConfig.setPassword(getPara("password"));
//		jdbcConfig.setJdbcUrl(getPara("jdbcUrl"));
//		if(getPara("jdbc_config_id") == null){
//			jdbcConfig.save();
//		}else{
//			jdbcConfig.setJdbcConfigId(getParaToInt("jdbc_config_id"));
//			jdbcConfig.update();
//		}
		
		renderJson(new ResultData());
	}
}