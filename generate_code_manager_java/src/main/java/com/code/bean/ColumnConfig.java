package com.code.bean;

import java.util.ArrayList;
import java.util.HashMap;
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
							Integer checkVal =  Integer.valueOf(String.valueOf(map.get("checkVal")));
							dataTmp.put("data", getRadioDate(configKeyValList, checkVal));
						}
					}
					this.configMap.put(String.valueOf(map.get("code")), dataTmp);
				}
			}
		}
		// {config_name=注解, code=aa, checkVal=, config_id=1.0, project_id=1.0,
		// configKeyValList=[{val=我勒个去, config_id=1.0, val2=1, val1=1, name=1, id=1.0,
		// check=0.0}, {val=2, config_id=1.0, val2=1, val1=1, name=2222, id=2.0,
		// check=0.0}], input_type=radio, checkValList=[]}
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
				return map;
			}
		}
		return new HashMap<String, Object>();
	}
	private Map<String, Object> configMap = new HashMap<String, Object>();
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
	
}
