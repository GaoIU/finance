package com.fanteng.finance.app.user.service;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {

	/**
	 * 用户注册
	 * 
	 * @param userInfo
	 * @return
	 */
	JsonResult register(UserInfo userInfo);

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	boolean checkProperty(String property, Object value);

}
