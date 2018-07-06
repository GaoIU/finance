package com.fanteng.core.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;

public interface BaseDao<T> {

	Session getSession();

	HibernateTemplate getTemplate();

	NativeQuery<?> createNativeQuery(String sql);

	T get(Serializable id);

	T load(Serializable id);

	Serializable save(T entity);

	boolean update(T entity);

	boolean updateIgnore(T entity);

	boolean checkUpdate(Serializable id, Timestamp updateTime);

	boolean delete(T entity);

	List<T> findAll();

	List<T> findAll(List<Condition> conditions);

	Page findPage(Integer current, Integer size, List<Condition> conditions);

	Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass);

	int getCount();

	int getCount(List<Condition> conditions);

	int getCount(Class<T> entityClass);

	int getCount(Class<T> entityClass, List<Condition> conditions);

	T findOne(String propertyName, Operation operation, Object value);

	T findOne(List<Condition> conditions);

	List<T> findOnes(String propertyName, Operation operation, Object value);

	List<T> queryAll(String sql, Map<String, Object> param);

	List<Map<String, Object>> queryAllToMap(String sql, Map<String, Object> param);

	Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param);

	Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param, Class<T> entityClass);

	Page queryPageToMap(String sql, Integer current, Integer size, Map<String, Object> param);

}
