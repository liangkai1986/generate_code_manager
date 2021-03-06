package com.code.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseProject<M extends BaseProject<M>> extends Model<M> implements IBean {

	public M setProjectId(java.lang.Integer projectId) {
		set("project_id", projectId);
		return (M)this;
	}
	
	public java.lang.Integer getProjectId() {
		return getInt("project_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setJdbcConfigId(java.lang.Integer jdbcConfigId) {
		set("jdbc_config_id", jdbcConfigId);
		return (M)this;
	}
	
	public java.lang.Integer getJdbcConfigId() {
		return getInt("jdbc_config_id");
	}

}
