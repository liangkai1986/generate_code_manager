package com.code.config;

import com.code.controller.ConfigController;
import com.code.controller.JdbcConfigController;
import com.code.controller.ProjectController;
import com.code.controller.TableInfoController;
import com.code.controller.TemplateController;
import com.jfinal.config.Routes;

public class RouteConfig extends Routes{

	@Override
	public void config() {
		add("/jdbcConfig",JdbcConfigController.class);
		add("/project",ProjectController.class);
		add("/tableInfo",TableInfoController.class);
		add("/template",TemplateController.class);
		add("/config",ConfigController.class);
	}
}