package com.fanteng.core.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Id;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

import com.fanteng.core.Condition;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.ReflectionKit;
import com.fanteng.util.StringUtil;

@SuppressWarnings({ "unchecked", "deprecation" })
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> clazz;

	public BaseDaoImpl() {
		this.clazz = ReflectionKit.getSuperClassGenricType(getClass());
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public HibernateTemplate getTemplate() {
		return new HibernateTemplate(sessionFactory);
	}

	@Override
	public NativeQuery<?> createNativeQuery(String sql) {
		return getSession().createNativeQuery(sql);
	}

	@Override
	public T get(Serializable id) {
		return getSession().get(clazz, id);
	}

	@Override
	public T load(Serializable id) {
		return getSession().load(clazz, id);
	}

	@Override
	public Serializable save(T entity) {
		String id = getIdProperty(entity);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		ReflectionKit.setMethodValue(entity, id, uuid);
		ReflectionKit.setMethodValue(entity, "createTime", new Timestamp(System.currentTimeMillis()));
		try {
			getSession().save(entity);
			return uuid;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getIdProperty(T entity) {
		String idName = null;

		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				idName = field.getName();
				break;
			}
		}
		return idName;
	}

	@Override
	public boolean update(T entity) {
		ReflectionKit.setMethodValue(entity, "updateTime", new Timestamp(System.currentTimeMillis()));
		try {
			getSession().update(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateIgnore(T entity) {
		String className = entity.getClass().getName();
		ReflectionKit.setMethodValue(entity, "updateTime", new Timestamp(System.currentTimeMillis()));
		Map<String, Object> map = BeanUtil.toMap(entity);
		StringBuffer sb = new StringBuffer("update " + className + " t set");
		map.forEach((k, v) -> {
			if (!StringUtil.equals("id", k) && StringUtil.isNotEmpty(v)) {
				sb.append(" t." + k + " = :" + k + ",");
			}
		});

		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where t.id = :id");

		Query<?> query = getSession().createQuery(sb.toString());
		map.forEach((k, v) -> {
			if (StringUtil.isNotEmpty(v)) {
				query.setParameter(k, v);
			}
		});

		int execute = query.executeUpdate();
		if (execute > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean updateIgnoreByFiters(T entity, String fiters) {
		String className = entity.getClass().getName();
		ReflectionKit.setMethodValue(entity, "updateTime", new Timestamp(System.currentTimeMillis()));
		Map<String, Object> map = BeanUtil.toMapFiters(entity, fiters);
		StringBuffer sb = new StringBuffer("update " + className + " t set");
		map.forEach((k, v) -> {
			if (!StringUtil.equals("id", k) && StringUtil.isNotEmpty(v)) {
				sb.append(" t." + k + " = :" + k + ",");
			}
		});

		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where t.id = :id");

		Query<?> query = getSession().createQuery(sb.toString());
		map.forEach((k, v) -> {
			if (StringUtil.isNotEmpty(v)) {
				query.setParameter(k, v);
			}
		});

		int execute = query.executeUpdate();
		if (execute > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean updateIgnoreByIncludes(T entity, String includes) {
		String className = entity.getClass().getName();
		ReflectionKit.setMethodValue(entity, "updateTime", new Timestamp(System.currentTimeMillis()));
		Map<String, Object> map = BeanUtil.toMapIncludes(entity, includes);
		StringBuffer sb = new StringBuffer("update " + className + " t set");
		map.forEach((k, v) -> {
			if (!StringUtil.equals("id", k) && StringUtil.isNotEmpty(v)) {
				sb.append(" t." + k + " = :" + k + ",");
			}
		});

		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where t.id = :id");

		Query<?> query = getSession().createQuery(sb.toString());
		map.forEach((k, v) -> {
			if (StringUtil.isNotEmpty(v)) {
				query.setParameter(k, v);
			}
		});

		int execute = query.executeUpdate();
		if (execute > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkUpdate(Serializable id, Timestamp updateTime) {
		T t = get(id);
		if (t != null) {
			Object object = ReflectionKit.getMethodValue(t, "updateTime");
			if (object == null) {
				return true;
			}

			if (updateTime == null) {
				return false;
			}

			Timestamp value = (Timestamp) object;
			return value.getTime() == updateTime.getTime();
		}
		return false;
	}

	@Override
	public boolean delete(T entity) {
		try {
			getSession().delete(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<T> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	@Override
	public List<T> findAll(String properties) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String propertyName : propertyNames) {
				projectionList.add(Projections.property(propertyName.trim()).as(propertyName.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	@Override
	public List<T> findAll(String properties, List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String propertyName : propertyNames) {
				projectionList.add(Projections.property(propertyName.trim()).as(propertyName.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	@Override
	public List<T> findAll(List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}
		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	private void addCondition(DetachedCriteria criteria, Condition condition) {
		String property = condition.getProperty();
		Operation operation = condition.getOperation();
		Object value = condition.getValue();

		switch (operation) {
		case EQ:
			criteria.add(Restrictions.eq(property, value));
			break;

		case NE:
			criteria.add(Restrictions.ne(property, value));
			break;

		case ALL_EQ:
			Map<String, ?> map = (Map<String, ?>) value;
			criteria.add(Restrictions.allEq(map));
			break;

		case GT:
			criteria.add(Restrictions.gt(property, value));
			break;

		case GE:
			criteria.add(Restrictions.ge(property, value));
			break;

		case LT:
			criteria.add(Restrictions.lt(property, value));
			break;

		case LE:
			criteria.add(Restrictions.le(property, value));
			break;

		case IS_NULL:
			criteria.add(Restrictions.isNull(property));
			break;

		case IS_NOT_NULL:
			criteria.add(Restrictions.isNotNull(property));
			break;

		case BETWEEN:
			Object[] obj = (Object[]) value;
			criteria.add(Restrictions.between(property, obj[0], obj[1]));
			break;

		case NOT_BETWEEN:
			Object[] objs = (Object[]) value;
			criteria.add(Restrictions.not(Restrictions.between(property, objs[0], objs[1])));
			break;

		case LIKE_LEFT:
			criteria.add(Restrictions.like(property, value.toString(), MatchMode.END));
			break;

		case LIKE_RIGHT:
			criteria.add(Restrictions.like(property, value.toString(), MatchMode.START));
			break;

		case LIKE_ANY:
			criteria.add(Restrictions.like(property, value.toString(), MatchMode.ANYWHERE));
			break;

		case IN:
			Collection<?> values = (Collection<?>) value;
			criteria.add(Restrictions.in(property, values));
			break;

		case NOT_IN:
			Collection<?> ins = (Collection<?>) value;
			criteria.add(Restrictions.not(Restrictions.in(property, ins)));
			break;

		case AND:
			Criterion[] predicates = (Criterion[]) value;
			criteria.add(Restrictions.and(predicates));
			break;

		case OR:
			Criterion[] criterions = (Criterion[]) value;
			criteria.add(Restrictions.or(criterions));
			break;

		case ASC:
			String[] propertysByAsc = value.toString().split(",");
			for (String name : propertysByAsc) {
				criteria.addOrder(Order.asc(name.trim()));
			}
			break;

		case DESC:
			String[] propertys = value.toString().split(",");
			for (String name : propertys) {
				criteria.addOrder(Order.desc(name.trim()));
			}
			break;

		default:
			break;
		}
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}
		criteria.setProjection(null);

		List<?> list = getTemplate().findByCriteria(criteria, (current - 1) * size, size);

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions, String properties) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}
		criteria.setProjection(null);

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String propertyName : propertyNames) {
				projectionList.add(Projections.property(propertyName.trim()).as(propertyName.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<?> list = getTemplate().findByCriteria(criteria, (current - 1) * size, size);

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}
		criteria.setProjection(null);

		List<?> list = getTemplate().findByCriteria(criteria, (current - 1) * size, size);

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Page findPage(Integer current, Integer size, List<Condition> conditions, Class<T> entityClass,
			String properties) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}
		criteria.setProjection(null);

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String propertyName : propertyNames) {
				projectionList.add(Projections.property(propertyName.trim()).as(propertyName.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<?> list = getTemplate().findByCriteria(criteria, (current - 1) * size, size);

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public int getCount() {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		if (object != null) {
			return ((Number) object).intValue();
		}

		return 0;
	}

	@Override
	public int getCount(List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		if (object != null) {
			return ((Number) object).intValue();
		}

		return 0;
	}

	@Override
	public int getCount(Class<T> entityClass) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		if (object != null) {
			return ((Number) object).intValue();
		}

		return 0;
	}

	@Override
	public int getCount(Class<T> entityClass, List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		criteria.setProjection(Projections.rowCount());
		Object object = criteria.getExecutableCriteria(getSession()).uniqueResult();
		if (object != null) {
			return ((Number) object).intValue();
		}

		return 0;
	}

	@Override
	public T findOne(String propertyName, Operation operation, Object value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		Condition condition = new Condition(propertyName, operation, value);
		addCondition(criteria, condition);
		List<?> list = getTemplate().findByCriteria(criteria);
		if (CollectionUtils.isNotEmpty(list)) {
			return (T) list.get(0);
		}

		return null;
	}

	@Override
	public T findOne(String propertyName, Operation operation, Object value, String properties) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		Condition condition = new Condition(propertyName, operation, value);
		addCondition(criteria, condition);

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String property : propertyNames) {
				projectionList.add(Projections.property(property.trim()).as(property.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<?> list = getTemplate().findByCriteria(criteria);
		if (CollectionUtils.isNotEmpty(list)) {
			return (T) list.get(0);
		}
		return null;
	}

	@Override
	public T findOne(List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}
		List<?> list = getTemplate().findByCriteria(criteria);
		if (CollectionUtils.isNotEmpty(list)) {
			return (T) list.get(0);
		}

		return null;
	}

	@Override
	public T findOne(List<Condition> conditions, String properties) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String property : propertyNames) {
				projectionList.add(Projections.property(property.trim()).as(property.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<?> list = getTemplate().findByCriteria(criteria);
		if (CollectionUtils.isNotEmpty(list)) {
			return (T) list.get(0);
		}
		return null;
	}

	@Override
	public T queryOne(String sql, Map<String, Object> param) {
		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sql);
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.addEntity(clazz);

		return (T) nativeQuery.uniqueResult();
	}

	@Override
	public Map<String, Object> queryOneToMap(String sql, Map<String, Object> param) {
		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sql);
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return (Map<String, Object>) nativeQuery.getSingleResult();
	}

	@Override
	public List<T> findOnes(String propertyName, Operation operation, Object value) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		Condition condition = new Condition(propertyName, operation, value);
		addCondition(criteria, condition);
		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	@Override
	public List<T> findOnes(String propertyName, Operation operation, Object value, String properties) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		Condition condition = new Condition(propertyName, operation, value);
		addCondition(criteria, condition);

		if (StringUtil.isNotBlank(properties)) {
			ProjectionList projectionList = Projections.projectionList();

			String[] propertyNames = properties.split(",");
			for (String property : propertyNames) {
				projectionList.add(Projections.property(property.trim()).as(property.trim()));
			}
			criteria.setProjection(projectionList);
		}

		criteria.setResultTransformer(Transformers.aliasToBean(clazz));

		List<T> list = (List<T>) getTemplate().findByCriteria(criteria);
		return list;
	}

	@Override
	public List<T> queryAll(String sql, Map<String, Object> param) {
		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sql);
		nativeQuery.addEntity(clazz);
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});

		List<T> list = (List<T>) nativeQuery.getResultList();
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAllToMap(String sql, Map<String, Object> param) {
		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sql);
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Map<String, Object>> list = (List<Map<String, Object>>) nativeQuery.getResultList();
		return list;
	}

	@Override
	public Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		StringBuffer sb = new StringBuffer(sql);
		sb.insert(6, " SQL_CALC_FOUND_ROWS ");

		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sb.toString());
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.addEntity(clazz);
		nativeQuery.setFirstResult((current - 1) * size);
		nativeQuery.setMaxResults(size);

		List<T> list = (List<T>) nativeQuery.getResultList();

		String countSql = "SELECT FOUND_ROWS() gemini_page_count";
		NativeQuery<?> sqlQuery = getSession().createNativeQuery(countSql);
		Object object = sqlQuery.uniqueResult();

		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Page queryPage(String sql, Integer current, Integer size, Map<String, Object> param, Class<T> entityClass) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		StringBuffer sb = new StringBuffer(sql);
		sb.insert(6, " SQL_CALC_FOUND_ROWS ");

		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sb.toString());
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.addEntity(entityClass);
		nativeQuery.setFirstResult((current - 1) * size);
		nativeQuery.setMaxResults(size);

		List<?> list = nativeQuery.getResultList();

		String countSql = "SELECT FOUND_ROWS() gemini_page_count";
		NativeQuery<?> sqlQuery = getSession().createNativeQuery(countSql);
		Object object = sqlQuery.uniqueResult();

		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Page queryPageToMap(String sql, Integer current, Integer size, Map<String, Object> param) {
		if (current == null) {
			current = 1;
		}
		if (size == null) {
			size = 15;
		}

		StringBuffer sb = new StringBuffer(sql);
		sb.insert(6, " SQL_CALC_FOUND_ROWS ");

		NativeQuery<?> nativeQuery = getSession().createNativeQuery(sb.toString());
		param.forEach((k, v) -> {
			nativeQuery.setParameter(k, v);
		});
		nativeQuery.setFirstResult((current - 1) * size);
		nativeQuery.setMaxResults(size);
		nativeQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<Map<String, Object>> list = (List<Map<String, Object>>) nativeQuery.getResultList();

		String countSql = "SELECT FOUND_ROWS() gemini_page_count";
		NativeQuery<?> sqlQuery = getSession().createNativeQuery(countSql);
		Object object = sqlQuery.uniqueResult();

		int totle = 0;
		if (object != null) {
			totle = ((Number) object).intValue();
		}

		Page page = new Page(current, size, totle, list);
		return page;
	}

	@Override
	public Object getSum(String propertyName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.setProjection(Projections.sum(propertyName));
		return criteria.getExecutableCriteria(getSession()).uniqueResult();
	}

	@Override
	public Object getSum(String propertyName, Condition condition) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		addCondition(criteria, condition);
		criteria.setProjection(Projections.sum(propertyName));
		return criteria.getExecutableCriteria(getSession()).uniqueResult();
	}

	@Override
	public Object getSum(String propertyName, List<Condition> conditions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		for (Condition condition : conditions) {
			addCondition(criteria, condition);
		}
		criteria.setProjection(Projections.sum(propertyName));
		return criteria.getExecutableCriteria(getSession()).uniqueResult();
	}

}
