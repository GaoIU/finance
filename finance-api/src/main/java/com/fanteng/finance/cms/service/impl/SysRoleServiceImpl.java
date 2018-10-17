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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.dao.SysRoleDao;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.cms.service.SysRoleResourceService;
import com.fanteng.finance.cms.service.SysRoleService;
import com.fanteng.finance.cms.service.SysUserRoleService;
import com.fanteng.finance.entity.SysResource;
import com.fanteng.finance.entity.SysRole;
import com.fanteng.finance.entity.SysRoleResource;
import com.fanteng.finance.entity.SysUserRole;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

	@Autowired
	private SysRoleResourceService sysRoleResourceService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 添加后台角色
	 * 
	 * @param sysRole
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult register(SysRole sysRole) throws Exception {
		String code = sysRole.getCode().toUpperCase();
		boolean checkCode = checkCode(code, sysRole.getId());
		if (checkCode) {
			return new JsonResult(HttpStatus.BAD_REQUEST, "该角色编码已被使用");
		}
		sysRole.setCode(code);
		String sysRoleId = save(sysRole).toString();

		if (StringUtil.isNotBlank(sysRoleId)) {
			if (StringUtil.isNotBlank(sysRole.getSysResourceIds())) {
				String[] sysResourceIds = sysRole.getSysResourceIds().split(",");
				List<String> ids = getSysResourceIds(sysResourceIds);

				if (CollectionUtils.isNotEmpty(ids)) {
					for (String sysResourceId : ids) {
						SysRoleResource sysRoleResource = new SysRoleResource(sysResourceId.trim(), sysRoleId);
						sysRoleResourceService.save(sysRoleResource);
					}
				}
			}

			return new JsonResult(HttpStatus.OK, "操作成功");
		}

		return new JsonResult(HttpStatus.INTERNAL_SERVER_ERROR, "操作失败");
	}

	/**
	 * 获取功能权限
	 * 
	 * @param sysResourceIds
	 * @return
	 */
	private List<String> getSysResourceIds(String[] sysResourceIds) {
		List<String> idlist = new ArrayList<>(sysResourceIds.length);
		if (ArrayUtils.isNotEmpty(sysResourceIds)) {
			Collections.addAll(idlist, sysResourceIds);

			List<Condition> conditions = new ArrayList<>(0);
			Condition parentId = new Condition("parentId", Operation.IN, idlist);
			Condition type = new Condition("type", Operation.EQ, SysResource.TYPE_FUNCTION);
			Condition status = new Condition("status", Operation.EQ, SysResource.STATUS_NORMAL);
			conditions.add(parentId);
			conditions.add(type);
			conditions.add(status);

			List<SysResource> list = sysResourceService.findAll("id", conditions);
			if (CollectionUtils.isNotEmpty(list)) {
				for (SysResource sysResource : list) {
					idlist.add(sysResource.getId());
				}
			}
		}
		return idlist;
	}

	/**
	 * 根据后台用户ID获取所有的角色
	 * 
	 * @param sysUserId
	 * @return
	 */
	@Override
	public List<SysRole> getSysRolesBySysUserId(String sysUserId) {
		List<SysUserRole> sysUserRoles = sysUserRoleService.findOnes("sysUserId", Operation.EQ, sysUserId);
		if (CollectionUtils.isNotEmpty(sysUserRoles)) {
			List<String> sysRoleIds = new ArrayList<String>(0);
			for (SysUserRole sysUserRole : sysUserRoles) {
				sysRoleIds.add(sysUserRole.getSysRoleId());
			}

			List<Condition> conditions = new ArrayList<Condition>(0);
			Condition ids = new Condition("id", Operation.IN, sysRoleIds);
			Condition status = new Condition("status", Operation.EQ, SysRole.STATUS_NORMAL);
			conditions.add(ids);
			conditions.add(status);

			return findAll(conditions);
		}
		return null;
	}

	/**
	 * 获取后台角色列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult queryList(Map<String, Object> params) throws Exception {
		Integer current = MapUtils.getInteger(params, "current");
		Integer size = MapUtils.getInteger(params, "size");
		String name = MapUtils.getString(params, "name");
		String code = MapUtils.getString(params, "code");
		Short status = MapUtils.getShort(params, "status");
		String beginTime = MapUtils.getString(params, "beginTime");
		String endTime = MapUtils.getString(params, "endTime");
		List<Condition> conditions = new ArrayList<Condition>(0);

		if (StringUtil.isNotBlank(name)) {
			Condition condition = new Condition("name", Operation.LIKE_ANY, name);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(code)) {
			Condition condition = new Condition("code", Operation.LIKE_ANY, code.toUpperCase());
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
			Date end = DateUtil.toDate(endTime, "yyyy-MM-dd");
			Condition condition = new Condition("createTime", Operation.LE, new Timestamp(end.getTime()));
			conditions.add(condition);
		}

		Page page = findPage(current, size, conditions);
		Map<String, Object> data = new HashMap<>(0);
		data.put("page", page);
		data.put("condition", params);

		return new JsonResult(HttpStatus.OK, "操作成功", data);
	}

	/**
	 * 验证角色编码
	 * 
	 * @param code
	 * @param sysRoleId
	 * @return
	 */
	@Override
	public boolean checkCode(String code, String sysRoleId) {
		boolean checkCode = checkPropertyName("code", code);
		if (StringUtil.isNotBlank(sysRoleId)) {
			SysRole sysRole = get(sysRoleId);
			if (sysRole != null) {
				if (StringUtil.equals(code, sysRole.getCode())) {
					checkCode = false;
				}
			}
		}

		return checkCode;
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
		SysRole sysRole = findOne(propertyName, Operation.EQ, value);

		if (sysRole != null) {
			return true;
		}

		return false;
	}

	/**
	 * 修改后台角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonResult edit(SysRole sysRole) {
		boolean b = updateIgnoreByFiters(sysRole, "sysResourceIds");
		if (b) {
			List<SysRoleResource> list = sysRoleResourceService.findOnes("sysRoleId", Operation.EQ, sysRole.getId());
			if (CollectionUtils.isNotEmpty(list)) {
				for (SysRoleResource sysRoleResource : list) {
					sysRoleResourceService.delete(sysRoleResource);
				}
			}

			if (StringUtil.isNotBlank(sysRole.getSysResourceIds())) {
				String[] ids = sysRole.getSysResourceIds().split(",");
				List<String> idlist = getSysResourceIds(ids);

				for (String sysResourceId : idlist) {
					SysRoleResource sysRoleResource = new SysRoleResource(sysResourceId.trim(), sysRole.getId());
					sysRoleResourceService.save(sysRoleResource);
				}
			}
		}

		return new JsonResult(HttpStatus.OK, "操作成功");
	}

	/**
	 * 删除后台角色
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

		List<SysRoleResource> srrs = sysRoleResourceService.findOnes("sysRoleId", Operation.IN, idlist);
		if (CollectionUtils.isNotEmpty(srrs)) {
			for (SysRoleResource sysRoleResource : srrs) {
				sysRoleResourceService.delete(sysRoleResource);
			}
		}

		List<SysUserRole> surs = sysUserRoleService.findOnes("sysRoleId", Operation.IN, idlist);
		if (CollectionUtils.isNotEmpty(surs)) {
			for (SysUserRole sysUserRole : surs) {
				sysUserRoleService.delete(sysUserRole);
			}
		}

		return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
	}

	/**
	 * 启用或者禁用后台角色
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean usable(String id, Short status) {
		SysRole sysRole = get(id);
		if (sysRole == null) {
			throw new ParamErrorException("非法请求");
		}

		sysRole.setStatus(status);

		return update(sysRole);
	}

}
