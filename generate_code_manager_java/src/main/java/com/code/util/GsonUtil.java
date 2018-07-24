package com.code.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
	 * 说明：
	 * @author: xkq
	 * @Package com.admin  
	 * @Description: TODO(用一句话描述该文件做什么)  
	 * @author xkq  
	 * @date 2018年4月3日  
	 */

public class GsonUtil {
	private static Gson gson = new Gson();
	static {
		gson.serializeNulls();
	}
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
	    return gson.fromJson(json, classOfT);
	}
	/**
	 *  new TypeToken<Map<String, String>>() {}.getType()
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T fromJson(String json,Type t){
		return gson.fromJson(json,t);
	}
	public static void main(String[] args) {
	}
}
