package com.fanteng.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;

public class BeanUtil extends BeanUtils {

	/**
	 * bean转map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj) {
		if (null == obj) {
			return null;
		}
		BeanMap beanMap = new BeanMap(obj);
		Map<String, Object> map = new HashMap<String, Object>(0);
		beanMap.forEach((key, value) -> map.put(key.toString(), value));
		map.remove("class");
		return map;
	}

	/**
	 * bean转map
	 * 
	 * @param obj
	 *            要转换的bean
	 * @param fiters
	 *            要忽略的属性
	 * @return
	 */
	public static Map<String, Object> toMapFiters(Object obj, String fiters) {
		if (null == obj) {
			return null;
		}
		BeanMap beanMap = new BeanMap(obj);
		Map<String, Object> map = new HashMap<String, Object>(0);
		beanMap.forEach((key, value) -> map.put(key.toString(), value));
		map.remove("class");

		if (StringUtil.isNotBlank(fiters)) {
			String[] keys = fiters.split(",");
			for (String key : keys) {
				map.remove(key.trim());
			}
		}
		return map;
	}

	/**
	 * bean转map
	 * 
	 * @param obj
	 *            要转换的bean
	 * @param includes
	 *            要保留的属性
	 * @return
	 */
	public static Map<String, Object> toMapIncludes(Object obj, String includes) {
		if (null == obj) {
			return null;
		}
		BeanMap beanMap = new BeanMap(obj);
		Map<String, Object> map = new HashMap<String, Object>(0);
		beanMap.forEach((k, v) -> {
			if (StringUtil.isNotBlank(includes)) {
				String[] keys = includes.split(",");
				for (String key : keys) {
					if (StringUtil.equals(k.toString(), key.trim())) {
						map.put(k.toString(), v);
					}
				}
			} else {
				map.put(k.toString(), v);
			}
		});
		map.remove("class");
		return map;
	}

	/**
	 * map转bean
	 * 
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object mapToBean(Map<String, Object> map, Class<?> beanClass)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (MapUtils.isEmpty(map)) {
			return null;
		}
		Object object = beanClass.newInstance();
		populate(object, map);
		return object;
	}

}