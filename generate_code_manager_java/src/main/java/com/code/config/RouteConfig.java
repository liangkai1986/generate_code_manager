package com.code.config;

import com.code.controller.JdbcConfigController;
import com.code.controller.ProjectController;
import com.code.controller.TableInfoController;
import com.code.controller.TemplateController;
import com.code.controller.TestController;
import com.jfinal.config.Routes;

public class RouteConfig extends Routes{

	@Override
	public void config() {
		add("/test", TestController.class);
		add("/jdbcConfig",JdbcConfigController.class);
		add("/project",ProjectController.class);
		add("/tableInfo",TableInfoController.class);
		add("/template",TemplateController.class);
	}
}