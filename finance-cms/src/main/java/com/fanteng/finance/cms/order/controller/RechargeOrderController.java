package com.fanteng.finance.cms.order.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fanteng.core.JsonResult;
import com.fanteng.exception.ParamErrorException;
import com.fanteng.finance.cms.order.service.RechargeOrderService;
import com.fanteng.finance.entity.RechargeOrder;
import com.fanteng.finance.entity.SysUser;
import com.fanteng.util.StringUtil;

@RestController
@RequestMapping("/rechargeOrder")
public class RechargeOrderController {

	@Autowired
	private RechargeOrderService rechargeOrderService;

	@Value("${sys.user.default.session.key}")
	private String defaultSessionKey;

	/**
	 * 跳转至充值列表页面
	 * 
	 * @return
	 */
	@GetMapping("/gotoList")
	public ModelAndView gotoList() {
		ModelAndView mav = new ModelAndView("/order/recharge/list");
		return mav;
	}

	/**
	 * 获取充值列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public JsonResult queryList(@RequestParam Map<String, Object> params) throws Exception {
		return rechargeOrderService.queryList(params);
	}

	/**
	 * 审核订单
	 * 
	 * @param param
	 * @return
	 */
	@PutMapping
	public JsonResult audit(@RequestBody Map<String, Object> param, HttpSession session) {
		String id = MapUtils.getString(param, "id");
		Short status = MapUtils.getShort(param, "status");
		String auditNote = MapUtils.getString(param, "auditNote");
		if (StringUtil.isBlank(id) || status == null || StringUtil.isBlank(auditNote)) {
			throw new ParamErrorException("无效参数");
		}

		SysUser sysUser = (SysUser) session.getAttribute(defaultSessionKey);
		RechargeOrder rechargeOrder = rechargeOrderService.get(id);
		rechargeOrder.setStatus(status);
		rechargeOrder.setSysUserId(sysUser.getId());
		rechargeOrder.setSysUserName(sysUser.getUserName());
		rechargeOrder.setAuditNote(auditNote);
		rechargeOrder.setAuditTime(new Timestamp(new Date().getTime()));

		return rechargeOrderService.audit(rechargeOrder);
	}

	@PostMapping("/export")
	public void export(HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception {
		params.put("size", Integer.MAX_VALUE);
		rechargeOrderService.export(response, params);
	}

}
