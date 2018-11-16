package com.fanteng.finance.cms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.exception.CustomException;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.dao.SysUserDao;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.cms.service.SysUserService;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.BeanUtil;
import com.fanteng.util.DateUtil;
import com.fanteng.util.EncryptUtil;
import com.fanteng.util.FastDFSUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Value("${sys.user.default.avatar}")
	private String defaultAvatar;

	@Value("${sys.default.server.url}")
	private String sysDefaultServerUrl;

	/**
	 * 注册后台用户
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(SysUser sysUser) throws Exception {
		String id = sysUser.getId();

		boolean checkUserName = checkUserName(sysUser.getUserName(), id);
		if (checkUserName) {
			throw new ParamErrorException("该账号已被使用");
		}

		boolean checkNickName = checkNickName(sysUser.getNickName(), id);
		if (checkNickName) {
			throw new ParamErrorException("该昵称已被使用");
		}

		boolean checkMobile = checkMobile(sysUser.getMobile(), id);
		if (checkMobile) {
			throw new ParamErrorException("该手机号已存在");
		}

		String password = EncryptUtil.encodeByBC("123456");
		sysUser.setPassword(password);
		sysUser.setAvatar(defaultAvatar);
		String sysUserId = save(sysUser).toString();

		if (StringUtil.isNotBlank(sysUserId)) {
			if (StringUtil.isNotBlank(sysUser.getSysRoleIds())) {
				String[] sysRoleIds = sysUser.getSysRoleIds().split(",");
				if (ArrayUtils.isNotEmpty(sysRoleIds)) {
					for (String sysRoleId : sysRoleIds) {
						SysUserRole sysUserRole = new SysUserRole(sysRoleId, sysUserId);
						sysUserRoleService.save(sysUserRole);
					}
				}
			}

			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
	}

	/**
	 * 后台用户登录
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public JsonResult signIn(Map<String, Object> param) {
		String userName = MapUtils.getString(param, "userName");
		String password = MapUtils.getString(param, "password");

		if (StringUtil.isBlank(userName)) {
			throw new ParamErrorException("请输入用户名");
		}

		if (StringUtil.isEmpty(password)) {
			throw new ParamErrorException("请输入密码");
		}

		SysUser sysUser = findOne("userName", Operation.EQ, userName);
		if (sysUser != null) {
			if (EncryptUtil.matchesByBC(password, sysUser.getPassword())) {
				if (sysUser.getStatus() == SysUser.STATUS_NORMAL) {
					return new JsonResult(HttpStatus.OK, "登录成功，正在跳转...", sysUser);
				}
				throw new CustomException(HttpStatus.FORBIDDEN, "该用户已被禁用，请联系管理员");
			}
		}
		throw new CustomException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
	}

	/**
	 * 获取后台用户列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult queryList(Map<String, Object> params) throws Exception {
		Integer current = MapUtils.getInteger(params, "current");
		Integer size = MapUtils.getInteger(params, "size");
		String userName = MapUtils.getString(params, "userName");
		String mobile = MapUtils.getString(params, "phone");
		Short status = MapUtils.getShort(params, "status");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<>(0);
		Condition createTimeDesc = new Condition("createTime", Operation.DESC, "createTime");
		conditions.add(createTimeDesc);

		if (StringUtil.isNotBlank(userName)) {
			Condition condition = new Condition("userName", Operation.LIKE_ANY, userName);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(mobile)) {
			Condition condition = new Condition("mobile", Operation.LIKE_ANY, mobile);
			conditions.add(condition);
		}
		if (status != null) {
			Condition condition = new Condition("status", Operation.EQ, status);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(beginTime)) {
			Date begin = DateUtil.toDate(beginTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.GE, new Timestamp(begin.getTime()));
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(endTime)) {
			Date end = DateUtil.toDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
			Condition condition = new Condition("createTime", Operation.LE, new Timestamp(end.getTime()));
			conditions.add(condition);
		}

		List<Map<String, Object>> maps = new ArrayList<>(0);
		Page page = findPage(current, size, conditions);
		List<?> list = page.getList();
		for (Object object : list) {
			Map<String, Object> map = BeanUtil.toMapFiters(object, "password, sysRoleIds");
			maps.add(map);
		}
		page.setList(maps);

		Map<String, Object> data = new HashMap<>(0);
		data.put("page", page);
		data.put("condition", params);

		return new JsonResult(HttpStatus.OK, "操作成功", data);
	}

	/**
	 * 验证属性字段是否存在
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@Override
	public boolean checkPropertyName(String propertyName, Object value) {
		SysUser sysUser = findOne(propertyName, Operation.EQ, value);

		if (sysUser != null) {
			return true;
		}

		return false;
	}

	/**
	 * 上传头像
	 * 
	 * @param avatar
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonResult uploadAvatar(MultipartFile avatar, String sysUserId) throws Exception {
		String path = FastDFSUtil.upload(avatar);
		if (StringUtil.isNotBlank(path)) {
			path = sysDefaultServerUrl + path;
			SysUser sysUser = get(sysUserId);
			String oldAvatar = sysUser.getAvatar();
			String fileId = oldAvatar.replaceAll(sysDefaultServerUrl, "");
			FastDFSUtil.delete(fileId);
			sysUser.setAvatar(path);
			update(sysUser);
			return new JsonResult(HttpStatus.OK, "操作成功", sysUser);
		}
		throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "上传文件失败");
	}

	/**
	 * 修改密码
	 * 
	 * @param sysUser
	 * @param password
	 * @param oldPwd
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonResult changePwd(SysUser sysUser, String password, String oldPwd) throws Exception {
		boolean checkPassword = EncryptUtil.matchesByBC(oldPwd, sysUser.getPassword());
		if (checkPassword) {
			password = EncryptUtil.encodeByBC(password);
			sysUser.setPassword(password);
			checkPassword = updateIgnore(sysUser);
			String msg = "操作成功";
			if (!checkPassword) {
				msg = "操作失败";
			}
			return new JsonResult(HttpStatus.OK, msg, checkPassword);
		}
		throw new ParamErrorException("旧密码错误，请重新输入");
	}

	/**
	 * 验证手机
	 * 
	 * @param mobile
	 * @param sysUserId
	 * @return
	 */
	@Override
	public boolean checkMobile(String mobile, String sysUserId) {
		boolean checkMobile = checkPropertyName("mobile", mobile);
		if (StringUtil.isNotBlank(sysUserId)) {
			SysUser sysUser = get(sysUserId);
			if (sysUser != null) {
				if (StringUtil.equals(mobile, sysUser.getMobile())) {
					checkMobile = false;
				}
			}
		}

		return checkMobile;
	}

	/**
	 * 验证昵称
	 * 
	 * @param nickName
	 * @param sysUserId
	 * @return
	 */
	@Override
	public boolean checkNickName(String nickName, String sysUserId) {
		boolean checkNickName = checkPropertyName("nickName", nickName);
		if (StringUtil.isNotBlank(sysUserId)) {
			SysUser sysUser = get(sysUserId);
			if (sysUser != null) {
				if (StringUtil.equals(nickName, sysUser.getNickName())) {
					checkNickName = false;
				}
			}
		}

		return checkNickName;
	}

	/**
	 * 验证账户
	 * 
	 * @param userName
	 * @param sysUserId
	 * @return
	 */
	@Override
	public boolean checkUserName(String userName, String sysUserId) {
		boolean checkUserName = checkPropertyName("userName", userName);
		if (StringUtil.isNotBlank(sysUserId)) {
			SysUser sysUser = get(sysUserId);
			if (sysUser != null) {
				if (StringUtil.equals(userName, sysUser.getUserName())) {
					checkUserName = false;
				}
			}
		}

		return checkUserName;
	}

	/**
	 * 启用或者禁用后台用户
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean usable(String id, Short status) {
		SysUser sysUser = get(id);
		if (sysUser == null) {
			throw new ParamErrorException("非法请求");
		}

		sysUser.setStatus(status);

		return update(sysUser);
	}

	/**
	 * 修改后台用户
	 * 
	 * @param sysUser
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult edit(SysUser sysUser) {
		boolean b = updateIgnoreByFiters(sysUser, "sysRoleIds");
		if (b) {
			List<SysUserRole> list = sysUserRoleService.findOnes("sysUserId", Operation.EQ, sysUser.getId());
			if (CollectionUtils.isNotEmpty(list)) {
				for (SysUserRole sysUserRole : list) {
					sysUserRoleService.delete(sysUserRole);
				}
			}

			if (StringUtil.isNotBlank(sysUser.getSysRoleIds())) {
				String[] ids = sysUser.getSysRoleIds().split(",");
				for (String id : ids) {
					SysUserRole sysUserRole = new SysUserRole(id.trim(), sysUser.getId());
					sysUserRoleService.save(sysUserRole);
				}
			}
		}

		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 删除后台用户
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult del(String[] ids) {
		for (String id : ids) {
			delete(id.trim());
		}

		List<String> idlist = new ArrayList<>(ids.length);
		Collections.addAll(idlist, ids);
		List<SysUserRole> list = sysUserRoleService.findOnes("sysUserId", Operation.IN, idlist);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysUserRole sysUserRole : list) {
				sysUserRoleService.delete(sysUserRole);
			}
		}

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
	}

}
