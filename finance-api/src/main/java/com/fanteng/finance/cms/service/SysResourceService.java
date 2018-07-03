package com.fanteng.finance.cms.service;

import java.util.List;

import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysResource;

public interface SysResourceService extends BaseService<SysResource> {

	/**
	 * 根据用户ID获取所有资源
	 * 
	 * @param sysUserId
	 * @return
	 */
	List<SysResource> getResourcesBySysUserId(String sysUserId);

}
