package com.fanteng.finance.cms.service;

import java.util.List;
import java.util.Map;

import com.fanteng.core.JsonResult;
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
	 * @param childName
	 * @param isMenu
	 * @return
	 */
	List<Object> getMenu(List<SysResource> list, String childName, boolean isMenu);

	/**
	 * 根据父级ID查询菜单
	 * 
	 * @param id
	 * @return
	 */
	List<SysResource> getMenuByParentId(String id);

	/**
	 * 获取后台资源列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

	/**
	 * 验证资源编码
	 * 
	 * @param code
	 * @param sysResourceId
	 * @return
	 */
	boolean checkCode(String code, String sysResourceId);

	/**
	 * 启用或者禁用后台资源
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	boolean usable(String id, Short status);

	/**
	 * 批量删除后台资源
	 * 
	 * @param ids
	 */
	void del(String[] ids);

	/**
	 * 获取权限
	 * 
	 * @param list
	 * @return
	 */
	List<Object> getPermission(List<SysResource> list);

}
