package com.code.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseConfigKeyVal<M extends BaseConfigKeyVal<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setConfigId(java.lang.Integer configId) {
		set("config_id", configId);
		return (M)this;
	}
	
	public java.lang.Integer getConfigId() {
		return getInt("config_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setVal(java.lang.String val) {
		set("val", val);
		return (M)this;
	}
	
	public java.lang.String getVal() {
		return getStr("val");
	}

	public M setVal1(java.lang.String val1) {
		set("val1", val1);
		return (M)this;
	}
	
	public java.lang.String getVal1() {
		return getStr("val1");
	}

	public M setVal2(java.lang.String val2) {
		set("val2", val2);
		return (M)this;
	}
	
	public java.lang.String getVal2() {
		return getStr("val2");
	}

	public M setCheck(java.lang.Integer check) {
		set("check", check);
		return (M)this;
	}
	
	public java.lang.Integer getCheck() {
		return getInt("check");
	}

	public M setListImportPkg(java.lang.String listImportPkg) {
		set("listImportPkg", listImportPkg);
		return (M)this;
	}
	
	public java.lang.String getListImportPkg() {
		return getStr("listImportPkg");
	}

}
