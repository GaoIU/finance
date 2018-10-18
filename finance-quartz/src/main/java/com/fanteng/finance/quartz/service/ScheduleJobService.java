package com.fanteng.finance.quartz.service;

import java.io.Serializable;
import java.util.Map;

import com.fanteng.core.JsonResult;
import com.fanteng.core.base.BaseService;
import com.fanteng.finance.entity.ScheduleJob;

public interface ScheduleJobService extends BaseService<ScheduleJob> {

	/**
	 * 暂停定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	void pauseJob(String id) throws Exception;

	/**
	 * 恢复一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	void resumeJob(String id) throws Exception;

	/**
	 * 是否并发
	 * 
	 * @param id
	 * @param concurrent
	 * @throws Exception
	 */
	void concurrent(String id, Short concurrent) throws Exception;

	/**
	 * 立即执行一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	void runOnce(String id) throws Exception;

	/**
	 * 更新时间表达式
	 * 
	 * @param id
	 * @param cronExpression
	 * @throws Exception
	 */
	void updateCron(String id, String cronExpression) throws Exception;

	/**
	 * 移除一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	void removeJob(String id) throws Exception;

	/**
	 * 停止所有定时任务
	 * 
	 * @throws Exception
	 */
	void shutdowns() throws Exception;

	/**
	 * 分页获取定时任务列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	JsonResult queryList(Map<String, Object> param) throws Exception;

	/**
	 * 添加一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	Serializable saveJob(ScheduleJob scheduleJob) throws Exception;

	/**
	 * 修改一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	boolean updateJob(ScheduleJob scheduleJob) throws Exception;

	/**
	 * 删除一个定时任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean delJob(String id) throws Exception;

}
