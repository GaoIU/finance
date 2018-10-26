package com.fanteng.finance.cms.service;

import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysConfig;

public interface SysConfigService extends BaseService<SysConfig> {

	/**
	 * 获取后台系统配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	boolean checkPropertyName(String propertyName, Object value);

	/**
	 * 验证配置编码
	 * 
	 * @param code
	 * @param sysConfigId
	 * @return
	 */
	boolean checkCode(String code, String sysConfigId);

	/**
	 * 添加后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	JsonResult register(SysConfig sysConfig);

	/**
	 * 修改后台系统配置
	 * 
	 * @param sysConfig
	 * @return
	 */
	JsonResult edit(SysConfig sysConfig);

	/**
	 * 批量删除后台系统配置
	 * 
	 * @param ids
	 */
	void del(String[] ids);

}
