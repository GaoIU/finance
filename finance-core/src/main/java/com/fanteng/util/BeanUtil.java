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