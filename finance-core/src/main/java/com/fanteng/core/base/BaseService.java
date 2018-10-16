package com.fanteng.core.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;

public interface BaseService<T> {

	T get(Serializable id);

	T load(Serializable id);

	Serializable save(T entity);

	boolean update(T entity);

	boolean updateIgnore(T entity);

	boolean updateIgnoreByFiters(T entity, String fiters);

	boolean updateIgnoreByIncludes(T entity, String includes);

	boolean checkUpdate(Serializable id, Timestamp updateTime);

	boolean delete(T entity);

	boolean delete(Serializable id);

	List<T> findAll();

	List<T> findAll(String properties);

	List<T> findAll(String properties, List<Condition> conditions);

	List<T> findAll(List<Condition> conditions);

	Page findPage(Integer current, Integer size, List<Condition> conditions);

	Page findPage(Integer current, Integer size, List<Condition> conditions, String properties);

	Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass);

	Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass, String properties);

	int getCount();

	int getCount(Class<T> entityClass);

	T findOne(String propertyName, Operation operation, Object value);

	T findOne(String propertyName, Operation operation, Object value, String properties);

	T findOne(List<Condition> conditions);

	T findOne(List<Condition> conditions, String properties);

	List<T> findOnes(String propertyName, Operation operation, Object value);

	List<T> findOnes(String propertyName, Operation operation, Object value, String properties);

	List<T> queryAll(String sql, Map<String, Object> param);

	List<Map<String, Object>> queryAllToMap(String sql, Map<String, Object> param);

	Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param);

	Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param, Class<T> entityClass);

	Page queryPageToMap(String sql, Integer current, Integer size, Map<String, Object> param);

}
