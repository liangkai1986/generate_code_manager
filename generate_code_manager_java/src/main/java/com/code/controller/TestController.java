package com.code.controller;

import java.util.HashMap;
import java.util.Map;

import com.code.config.Config;
import com.code.model.Template;
import com.code.util.FreemarkerUtil;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.druid.DruidPlugin;

public class TestController extends BaseController{
	
	public void index() {
		System.out.println("sss");
		Template t = Template.dao.findById(1);
		Map data = new HashMap<String, Object>();
		data.put("user", "哈哈哈");
		String fileContent = FreemarkerUtil.createFile(t.getContent(), data, "G:\\"+getPara("fileName"));
		renderText(fileContent);
	}
	
	public void b() {
		DruidPlugin druidPlugin = Config.createDruidPlugin();
		druidPlugin.start();
//		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin plugin = new ActiveRecordPlugin("a",druidPlugin);
		plugin.start();
		System.out.println(Db.use("a").find("select 1"));
//		Db.use(configName)
//		Db.use("a").find("show tables");
		
		renderText("这是String path");
	}
}
