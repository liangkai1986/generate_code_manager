package com.code.config;

import com.code.controller.TestController;
import com.jfinal.config.Routes;

public class RouteConfig extends Routes{

	@Override
	public void config() {
		add("/", TestController.class,"/");
	}
}