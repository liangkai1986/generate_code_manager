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
	
}