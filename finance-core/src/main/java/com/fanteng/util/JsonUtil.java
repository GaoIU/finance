package com.fanteng.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {

	private final static Gson GSON = new Gson();

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
		return GSON.toJson(obj);
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
		return GSON.fromJson(json, cla);
	}

	/**
	 * 解析JSON数组
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> fromJsonArray(String json) {
		List<Map<String, Object>> list = new ArrayList<>(0);

		JsonParser parsers = new JsonParser();
		JsonArray jsonArrays = parsers.parse(json).getAsJsonArray();
		for (JsonElement jsonElement : jsonArrays) {
			Map<?, ?> fromJson = GSON.fromJson(jsonElement, Map.class);

			Map<String, Object> map = new HashMap<>(0);
			fromJson.forEach((k, v) -> {
				map.put(k.toString(), v);
			});

			list.add(map);
		}

		return list;
	}

}