package com.code.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTemplate<M extends BaseTemplate<M>> extends Model<M> implements IBean {

	public M setTemplateId(java.lang.Integer templateId) {
		set("template_id", templateId);
		return (M)this;
	}
	
	public java.lang.Integer getTemplateId() {
		return getInt("template_id");
	}

	public M setProjectId(java.lang.Integer projectId) {
		set("project_id", projectId);
		return (M)this;
	}
	
	public java.lang.Integer getProjectId() {
		return getInt("project_id");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setPath(java.lang.String path) {
		set("path", path);
		return (M)this;
	}
	
	public java.lang.String getPath() {
		return getStr("path");
	}

}
