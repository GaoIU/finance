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
	 * @throws Exception
	 */
	JsonResult register(UserInfo userInfo) throws Exception;

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	boolean checkProperty(String property, Object value);

	/**
	 * 用户账号密码登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	JsonResult signIn(String userName, String password) throws Exception;

	/**
	 * 用户短信验证码登录
	 * 
	 * @param userName
	 * @param code
	 * @return
	 */
	JsonResult signInCode(String userName, String code);

}
