package com.fanteng.finance.cms.order.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.order.service.OperateConfigService;
import com.fanteng.finance.entity.OperateConfig;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/operateConfig")
public class OperateConfigController {

	@Autowired
	private OperateConfigService operateConfigService;

	/**
	 * 跳转至充值-提现操作配置列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		return new ModelAndView("/order/configuration/list");
	}

	/**
	 * 获取充值-提现操作配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return operateConfigService.queryList(params);
	}

	/**
	 * 跳转至充值-提现操作配置新增或修改页面
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/gotoInfo")
	public ModelAndView gotoInfo(String id) {
		ModelAndView mav = new ModelAndView("/order/configuration/info");
		if (StringUtil.isNotBlank(id)) {
			OperateConfig operateConfig = operateConfigService.get(id);
			if (operateConfig == null) {
				throw new ParamErrorException("非法请求");
			}
			mav.addObject("info", operateConfig);
		}
		return mav;
	}

	/**
	 * 添加充值-提现操作配置
	 * 
	 * @param operateConfig
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult register(@Valid OperateConfig operateConfig, MultipartFile file) throws Exception {
		return operateConfigService.register(operateConfig, file);
	}

	/**
	 * 修改充值-提现操作配置
	 * 
	 * @param operateConfig
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PutMapping
	public JsonResult edit(@Valid OperateConfig operateConfig, MultipartFile file) throws Exception {
		return operateConfigService.edit(operateConfig, file);
	}

	/**
	 * 批量删除充值-提现操作配置
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping
	public JsonResult del(String id) throws Exception {
		if (StringUtil.isBlank(id)) {
			throw new ParamErrorException("无效参数");
		}
		String[] ids = id.split(",");
		if (ArrayUtils.isEmpty(ids)) {
			throw new ParamErrorException("无效参数");
		}

		return operateConfigService.del(ids);
	}

	/**
	 * 启用或者禁用充值-提现操作配置
	 * 
	 * @param param
	 * @return
	 */
	@PutMapping("/usable")
	public JsonResult usable(@RequestBody Map<String, Object> param) {
		String id = MapUtils.getString(param, "id");
		Short status = MapUtils.getShort(param, "status");
		if (StringUtil.isBlank(id) || status == null) {
			throw new ParamErrorException("无效参数");
		}

		OperateConfig operateConfig = operateConfigService.get(id);
		if (operateConfig == null) {
			throw new ParamErrorException("无效参数");
		}

		operateConfig.setStatus(status);
		boolean usable = operateConfigService.update(operateConfig);
		if (usable) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功", usable);
		}

		return new JsonResult(com.fanteng.core.HttpStatus.ACCEPTED, "操作失败");
	}

}
