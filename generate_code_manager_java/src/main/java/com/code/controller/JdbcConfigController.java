package com.code.controller;

import com.code.bean.ResultData;
import com.code.model.JdbcConfig;

public class JdbcConfigController extends BaseController{
	
	public void list() {
		renderJson(new ResultData().setData(JdbcConfig.dao.find("select * from jdbc_config")));
	}
	public void del() {
		JdbcConfig.dao.deleteById(getPara("id"));
		renderJson(new ResultData());
	}
	public void get() {
		renderJson(new ResultData().setData(JdbcConfig.dao.findById(getPara("id"))));
	}
	public void saveUpdate(){
		JdbcConfig jdbcConfig = new JdbcConfig();
		
		jdbcConfig.setName(getPara("name"));
		jdbcConfig.setUser(getPara("user"));
		jdbcConfig.setPassword(getPara("password"));
		jdbcConfig.setJdbcUrl(getPara("jdbcUrl"));
		if(getPara("jdbc_config_id") == null){
			jdbcConfig.save();
		}else{
			jdbcConfig.setJdbcConfigId(getParaToInt("jdbc_config_id"));
			jdbcConfig.update();
		}
		
		renderJson(new ResultData());
	}
}