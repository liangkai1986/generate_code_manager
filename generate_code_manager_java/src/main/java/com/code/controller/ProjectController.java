package com.code.controller;

import com.code.bean.ResultData;
import com.code.model.Project;

public class ProjectController extends BaseController{
	
	public void list() {
		renderJson(new ResultData().setData(Project.dao.find("select project.*,jdbc_config.name jdbcName from project LEFT JOIN jdbc_config on project.jdbc_config_id=jdbc_config.jdbc_config_id order by project.project_id asc")));
	}
	public void del() {
		Project.dao.deleteById(getPara("id"));
		renderJson(new ResultData());
	}
	public void get() {
		renderJson(new ResultData().setData(Project.dao.findById(getPara("id"))));
	}
	public void saveUpdate(){
		Project project = new Project();
		
		project.setName(getPara("name"));
		if(getPara("jdbc_config_id")!=null){
			project.setJdbcConfigId(getParaToInt("jdbc_config_id"));
		}
		
		if(getPara("project_id") == null){
			project.save();
		}else{
			project.setProjectId(getParaToInt("project_id"));
			project.update();
		}
		
		renderJson(new ResultData());
	}
}