package com.fanteng.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReflectionKit {

	private static final Log logger = LogFactory.getLog(ReflectionKit.class);

	/**
	 * <p>
	 * 反射 method 方法名，例如 getId
	 * </p>
	 *
	 * @param field
	 * @param str
	 *            属性字符串内容
	 * @return
	 */
	public static String getMethodCapitalize(Field field, final String str) {
		Class<?> fieldType = field.getType();
		// fix #176
		return StringUtil.concatCapitalize(boolean.class.equals(fieldType) ? "is" : "get", str);
	}

	/**
	 * <p>
	 * 获取 public get方法的值
	 * </p>
	 *
	 * @param cls
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 * @return Object
	 */
	public static Object getMethodValue(Class<?> cls, Object entity, String str) {
		Map<String, Field> fieldMaps = getFieldMap(cls);
		try {
			if (MapUtils.isEmpty(fieldMaps)) {
				throw new RuntimeException(
						String.format("Error: NoSuchField in %s for %s.  Cause:", cls.getSimpleName(), str));
			}
			Method method = cls.getMethod(getMethodCapitalize(fieldMaps.get(str), str));
			return method.invoke(entity);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(String.format("Error: NoSuchMethod in %s.  Cause:", cls.getSimpleName()) + e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					String.format("Error: Cannot execute a private method. in %s.  Cause:", cls.getSimpleName()) + e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Error: InvocationTargetException on getMethodValue.  Cause:" + e);
		}
	}

	/**
	 * <p>
	 * 获取 public get方法的值
	 * </p>
	 *
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 * @return Object
	 */
	public static Object getMethodValue(Object entity, String str) {
		if (null == entity) {
			return null;
		}
		return getMethodValue(entity.getClass(), entity, str);
	}

	/**
	 * <p>
	 * 设置 public set方法的值
	 * </p>
	 *
	 * @param cls
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 */
	public static void setMethodValue(Class<?> cls, Object entity, String str, Object value) {
		try {
			Field field = cls.getDeclaredField(str);
			if (null == field) {
				throw new RuntimeException(
						String.format("Error: NoSuchField in %s for %s.  Cause:", cls.getSimpleName(), str));
			}
			field.setAccessible(true);
			field.set(entity, value);
		} catch (NoSuchFieldException e1) {
			throw new RuntimeException(
					String.format("Error: NoSuchField in %s for %s.  Cause:", cls.getSimpleName(), str));
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					String.format("Error: Cannot execute a private method. in %s.  Cause:", cls.getSimpleName()) + e);
		}
	}

	/**
	 * <p>
	 * 设置 public set方法的值
	 * </p>
	 *
	 * @param entity
	 *            实体
	 * @param str
	 *            属性字符串内容
	 */
	public static void setMethodValue(Object entity, String str, Object value) {
		if (null == entity) {
			throw new RuntimeException("Error: entity is null");
		}
		setMethodValue(entity.getClass(), entity, str, value);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * <p>
	 * 反射对象获取泛型
	 * </p>
	 *
	 * @param clazz
	 *            对象
	 * @param index
	 *            泛型所在位置
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
					clazz.getSimpleName(), params.length));
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
					clazz.getSimpleName()));
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * <p>
	 * 获取该类的所有属性列表
	 * </p>
	 *
	 * @param clazz
	 *            反射类
	 * @return
	 */
	public static Map<String, Field> getFieldMap(Class<?> clazz) {
		List<Field> fieldList = getFieldList(clazz);
		Map<String, Field> fieldMap = Collections.emptyMap();
		if (CollectionUtils.isNotEmpty(fieldList)) {
			fieldMap = new LinkedHashMap<>();
			for (Field field : fieldList) {
				fieldMap.put(field.getName(), field);
			}
		}
		return fieldMap;
	}

	/**
	 * <p>
	 * 获取该类的所有属性列表
	 * </p>
	 *
	 * @param clazz
	 *            反射类
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> clazz) {
		if (null == clazz) {
			return null;
		}
		List<Field> fieldList = new LinkedList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			/* 过滤静态属性 */
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			/* 过滤 transient关键字修饰的属性 */
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			fieldList.add(field);
		}
		/* 处理父类字段 */
		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return fieldList;
		}
		/* 排除重载属性 */
		return excludeOverrideSuperField(fieldList, getFieldList(superClass));
	}

	/**
	 * <p>
	 * 排序重置父类属性
	 * </p>
	 *
	 * @param fieldList
	 *            子类属性
	 * @param superFieldList
	 *            父类属性
	 */
	public static List<Field> excludeOverrideSuperField(List<Field> fieldList, List<Field> superFieldList) {
		// 子类属性
		Map<String, Field> fieldMap = new HashMap<>();
		for (Field field : fieldList) {
			fieldMap.put(field.getName(), field);
		}
		for (Field superField : superFieldList) {
			if (null == fieldMap.get(superField.getName())) {
				// 加入重置父类属性
				fieldList.add(superField);
			}
		}
		return fieldList;
	}

}