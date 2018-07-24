package com.code.util;

import com.code.model.JdbcConfig;
import com.code.model.Project;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.druid.DruidPlugin;

public class GCM_DB_util {

	public synchronized static DbPro getDb(Project project) throws Exception {

		if (project.getJdbcConfigId() == null) {
			throw new Exception("项目没有配置数据源");
		}
		JdbcConfig jdbcConfig = JdbcConfig.dao.findById(project.getJdbcConfigId());
		try {
			DbPro dbPro = Db.use(jdbcConfig.getJdbcConfigId().toString());
			if (dbPro != null) {
				return dbPro;
			}
		} catch (Exception e) {
			
		}
		System.out.println(jdbcConfig.getJdbcConfigId().toString()+"  没有数据源 开始创建");
		DruidPlugin druidPlugin = new DruidPlugin(jdbcConfig.getJdbcUrl(), jdbcConfig.getUser(),
				jdbcConfig.getPassword());
		druidPlugin.start();
		// me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin plugin = new ActiveRecordPlugin(jdbcConfig.getJdbcConfigId().toString(), druidPlugin);
		plugin.start();
		return Db.use(jdbcConfig.getJdbcConfigId().toString());
	}
}
