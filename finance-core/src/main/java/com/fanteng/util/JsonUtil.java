package com.fanteng.util;

import com.google.gson.Gson;

public class JsonUtil {

	private final static Gson gson = new Gson();

	/**
	 * Object转JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (null == obj) {
			return null;
		}
		return gson.toJson(obj);
	}

	/**
	 * 简单JSON转对象
	 * 
	 * @param json
	 * @param cla
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> cla) {
		if (StringUtil.isEmpty(json)) {
			return null;
		}
		return gson.fromJson(json, cla);
	}

}