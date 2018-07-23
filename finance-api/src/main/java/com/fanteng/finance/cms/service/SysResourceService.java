package com.fanteng.finance.cms.service;

import java.util.List;

import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysResource;

public interface SysResourceService extends BaseService<SysResource> {

	/**
	 * 根据后台用户ID获取所有资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	List<SysResource> getResourcesBySysUserId(String sysUserId);

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	boolean checkPropertyName(String propertyName, Object value);

	/**
	 * 根据后台用户ID获取所有菜单资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	List<SysResource> getResource(String sysUserId);

	/**
	 * 获取树形菜单
	 * 
	 * @param list
	 * @return
	 */
	List<Object> getMenu(List<SysResource> list);

	/**
	 * 根据父级ID查询菜单
	 * 
	 * @param id
	 * @return
	 */
	List<SysResource> getMenuByParentId(String id);

}
