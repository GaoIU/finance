package com.fanteng.finance.cms.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.SysUser;

public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	JsonResult register(SysUser sysUser) throws Exception;

	/**
	 * 后台用户登录
	 * 
	 * @param param
	 * @return
	 */
	JsonResult signIn(Map<String, Object> param);

	/**
	 * 获取后台用户列表
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
	 * 上传头像
	 * 
	 * @param avatar
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	JsonResult uploadAvatar(MultipartFile avatar, String sysUserId) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @param sysUser
	 * @param password
	 * @param oldPwd
	 * @return
	 * @throws Exception
	 */
	JsonResult changePwd(SysUser sysUser, String password, String oldPwd) throws Exception;

}
