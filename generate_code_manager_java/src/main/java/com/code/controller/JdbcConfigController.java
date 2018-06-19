package com.code.controller;

import com.code.model.JdbcConfig;

public class JdbcConfigController extends BaseController{
	
	public void list() {
		renderJson(JdbcConfig.dao.find("select * from jdbc_config"));
	}
	
}