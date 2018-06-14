package com.code.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseJdbcConfig<M extends BaseJdbcConfig<M>> extends Model<M> implements IBean {

	public M setJdbcConfigId(java.lang.Integer jdbcConfigId) {
		set("jdbc_config_id", jdbcConfigId);
		return (M)this;
	}
	
	public java.lang.Integer getJdbcConfigId() {
		return getInt("jdbc_config_id");
	}

	public M setJdbcUrl(java.lang.String jdbcUrl) {
		set("jdbcUrl", jdbcUrl);
		return (M)this;
	}
	
	public java.lang.String getJdbcUrl() {
		return getStr("jdbcUrl");
	}

	public M setUser(java.lang.String user) {
		set("user", user);
		return (M)this;
	}
	
	public java.lang.String getUser() {
		return getStr("user");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	public java.lang.String getPassword() {
		return getStr("password");
	}

}
