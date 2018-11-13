package com.fanteng.finance.app.order.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanteng.core.JsonResult;
import com.fanteng.core.RedisClient;
import com.fanteng.finance.app.order.service.RechargeOrderService;
import com.fanteng.finance.app.properties.RedisCommonKeyProperties;
import com.fanteng.finance.app.util.CommonUtil;
import com.fanteng.finance.entity.RechargeOrder;

@RestController
@RequestMapping("/rechargeOrder")
public class RechargeOrderController {

	@Autowired
	private RechargeOrderService rechargeOrderService;

	@Autowired
	private RedisClient redisClient;

	/**
	 * 生成充值订单
	 * 
	 * @param rechargeOrder
	 * @return
	 * @throws Exception
	 */
	@PostMapping
	public JsonResult createRechargeOrder(HttpServletRequest request, @Valid @RequestBody RechargeOrder rechargeOrder)
			throws Exception {
		Double ratio = Double.valueOf(redisClient.get(RedisCommonKeyProperties.SYS_AMOUNT_PROPORTION));
		BigDecimal amount = rechargeOrder.getAmount();
		amount = amount.divide(BigDecimal.valueOf(ratio));

		rechargeOrder.setAmount(amount);
		rechargeOrder.setOrderNo(CommonUtil.createOrderNo());
		rechargeOrder.setUserId(CommonUtil.getUserIdByToken(request));
		rechargeOrder.setUserName(CommonUtil.getUserNameByToken(request));

		Serializable save = rechargeOrderService.save(rechargeOrder);
		if (save != null) {
			return new JsonResult(com.fanteng.core.HttpStatus.OK, "操作成功");
		}
		return new JsonResult(com.fanteng.core.HttpStatus.INSUFFICIENT_STORAGE, "操作失败");
	}

}
