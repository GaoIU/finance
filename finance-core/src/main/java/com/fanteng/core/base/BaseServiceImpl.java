package com.fanteng.core.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;

public class BaseServiceImpl<M extends BaseDao<T>, T> implements BaseService<T> {

	@Autowired
	private M baseDao;

	@Override
	public T get(Serializable id) {
		return baseDao.get(id);
	}

	@Override
	public T load(Serializable id) {
		return baseDao.load(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Serializable save(T entity) {
		return baseDao.save(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(T entity) {
		return baseDao.update(entity);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateIgnore(T entity) {
		return baseDao.updateIgnore(entity);
	}

	@Override
	public boolean checkUpdate(Serializable id, Timestamp updateTime) {
		return baseDao.checkUpdate(id, updateTime);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delete(T entity) {
		return baseDao.delete(entity);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delete(Serializable id) {
		T t = get(id);
		return baseDao.delete(t);
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public List<T> findAll(List<Condition> conditions) {
		return baseDao.findAll(conditions);
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions) {
		return baseDao.findPage(current, size, conditions);
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass) {
		return baseDao.findPage(current, size, conditions, entityClass);
	}

	@Override
	public int getCount() {
		return baseDao.getCount();
	}

	@Override
	public int getCount(Class<T> entityClass) {
		return baseDao.getCount(entityClass);
	}

	@Override
	public T findOne(String propertyName, Operation operation, Object value) {
		return baseDao.findOne(propertyName, operation, value);
	}

	@Override
	public T findOne(List<Condition> conditions) {
		return baseDao.findOne(conditions);
	}

	@Override
	public List<T> findOnes(String propertyName, Operation operation, Object value) {
		return baseDao.findOnes(propertyName, operation, value);
	}

	@Override
	public List<T> queryAll(String sql, Map<String, Object> param) {
		return baseDao.queryAll(sql, param);
	}

	@Override
	public List<Map<String, Object>> queryAllToMap(String sql, Map<String, Object> param) {
		return baseDao.queryAllToMap(sql, param);
	}

	@Override
	public Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param) {
		return baseDao.queryPage(sql, current, size, param);
	}

	@Override
	public Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param, Class<T> entityClass) {
		return baseDao.queryPage(sql, current, size, param, entityClass);
	}

	@Override
	public Page queryPageToMap(String sql, Integer current, Integer size, Map<String, Object> param) {
		return baseDao.queryPageToMap(sql, current, size, param);
	}

}
