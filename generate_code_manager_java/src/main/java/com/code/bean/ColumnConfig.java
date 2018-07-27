package com.code.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StrKit;

/**
 * 每个字段的配置信息 根据config表 和页面上选择的值 创建的实体类
 * 
 * @author Administrator
 *
 */
public class ColumnConfig {
	@SuppressWarnings("unchecked")
	public ColumnConfig(List<Map<String, Object>> configList) {
		if (configList != null && configList.size() > 0) {
			for (int i = 0; i < configList.size(); i++) {
				Map<String, Object> map = configList.get(i);
				if(map.get("code")!=null) {
					Map<String , Object> dataTmp = new HashMap<String, Object>();
					dataTmp.put("baseConfig", map);//最基础的从页面上传过来的东西  
					dataTmp.put("checkFlag", false);//是否是 单选
					dataTmp.put("radioFlag", false);//是否是 多选
					List<Map<String, Object>> configKeyValList = (List<Map<String, Object>>) map.get("configKeyValList");
					if ("check".equalsIgnoreCase(String.valueOf(map.get("input_type")))) {
						dataTmp.put("checkFlag", true);
						dataTmp.put("listData", new ArrayList<Object>());
						if(map.get("checkValList")!=null) {
							List<Integer> checkValList = (List<Integer>) map.get("checkValList");
							dataTmp.put("listData", getCheckDate(configKeyValList, checkValList));
						}
					}
					if ("radio".equalsIgnoreCase(String.valueOf(map.get("input_type")))) {
						dataTmp.put("radioFlag", true);
						dataTmp.put("data", new HashMap<String, Object>());
						if(map.get("checkVal")!=null &&StrKit.notBlank(String.valueOf(map.get("checkVal")))) {
							Integer checkVal =  Double.valueOf(String.valueOf(map.get("checkVal"))).intValue();
							dataTmp.put("data", getRadioDate(configKeyValList, checkVal));
						}
					}
					this.configMap.put(String.valueOf(map.get("code")), dataTmp);
				}
			}
		}
	}
	private List<Map<String, Object>> getCheckDate(List<Map<String, Object>> configKeyValList,List<Integer> checkValList){
		List<Map<String, Object>> dataListTmp = new ArrayList<Map<String,Object>>();
		if(configKeyValList == null || configKeyValList.size()==0) {
			return dataListTmp;
		}
		if(checkValList == null || checkValList.size() == 0) {
			return dataListTmp;
		}
		for (int i = 0; i < checkValList.size(); i++) {
			Map<String, Object> tmpMap = getRadioDate(configKeyValList, checkValList.get(i));
			if(tmpMap!=null&&tmpMap.size()>0) {
				dataListTmp.add(tmpMap);
			}
		}
		return dataListTmp;
	}
	private Map<String, Object> getRadioDate(List<Map<String, Object>> configKeyValList,Integer checkVal){
		if(configKeyValList == null || configKeyValList.size()==0) {
			return new HashMap<String, Object>();
		}
		if(checkVal == null ) {
			return new HashMap<String, Object>();
		}
		
		for (int i = 0; i < configKeyValList.size(); i++) {
			Map<String, Object> map = configKeyValList.get(i);
			Integer id = Double.valueOf(String.valueOf(map.get("id"))).intValue();
			if(checkVal.intValue() == id.intValue()) {
				System.err.println(map);
				try {
					if(map.get("listImportPkg")!=null&&StrKit.notBlank(String.valueOf(map.get("listImportPkg")))) {
						String [] listImportPkgTmp = String.valueOf(map.get("listImportPkg")).split("#");
						listImportPkg.addAll(Arrays.asList(listImportPkgTmp));
					}
				} catch (Exception e) {
				}
				return map;
			}
		}
		//给listImportPkg去重
		listImportPkg = new ArrayList<String>(new HashSet<String>(listImportPkg));
		return new HashMap<String, Object>();
	}
	private Map<String, Object> configMap = new HashMap<String, Object>();
	private List<String> listImportPkg = new ArrayList<String>();//需要引入的package列表
	public Map<String, Object> getConfigMap() {
		return configMap;
	}
	public void setConfigMap(Map<String, Object> configMap) {
		this.configMap = configMap;
	}
	@Override
	public String toString() {
		return "ColumnConfig [configMap=" + configMap + "]";
	}
	public List<String> getListImportPkg() {
		return listImportPkg;
	}
	public void setListImportPkg(List<String> listImportPkg) {
		this.listImportPkg = listImportPkg;
	}
}
