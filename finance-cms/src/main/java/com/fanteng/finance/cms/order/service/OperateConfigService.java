package com.fanteng.finance.cms.order.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.OperateConfig;

public interface OperateConfigService extends BaseService<OperateConfig> {

	/**
	 * 获取充值-提现操作配置列表
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> params) throws Exception;

	/**
	 * 添加充值-提现操作配置
	 * 
	 * @param operateConfig
	 * @param file
	 * @return
	 * @throws Exception
	 */
	JsonResult register(OperateConfig operateConfig, MultipartFile file) throws Exception;

	/**
	 * 修改充值-提现操作配置
	 * 
	 * @param operateConfig
	 * @param file
	 * @return
	 * @throws Exception
	 */
	JsonResult edit(OperateConfig operateConfig, MultipartFile file) throws Exception;

	/**
	 * 批量删除充值-提现操作配置
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	JsonResult del(String[] ids) throws Exception;

}
