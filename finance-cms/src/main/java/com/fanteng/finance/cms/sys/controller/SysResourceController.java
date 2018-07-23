package com.fanteng.finance.cms.sys.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.finance.cms.service.SysResourceService;
import com.fanteng.finance.entity.SysResource;

@RestController
@RequestMapping("/sysResource")
public class SysResourceController {

	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 添加后台资源
	 * 
	 * @param sysResource
	 * @return
	 */
	@PostMapping
	public JsonResult register(@Valid @RequestBody SysResource sysResource) {
		String code = sysResource.getCode().toUpperCase();
		boolean checkCode = sysResourceService.checkPropertyName("code", code);
		if (checkCode) {
			return new JsonResult(com.fanteng.core.HttpStatus.BAD_REQUEST, "该资源编码已被使用");
		}

		String method = sysResource.getMethod().toUpperCase();
		sysResource.setCode(code);
		sysResource.setMethod(method);

		Serializable id = sysResourceService.save(sysResource);
		if (id != null) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

}
