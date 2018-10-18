package com.fanteng.finance.quartz.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fanteng.core.Condition;
import com.fanteng.core.HttpStatus;
import com.fanteng.core.JsonResult;
import com.fanteng.core.Operation;
import com.fanteng.core.Page;
import com.fanteng.core.base.BaseServiceImpl;
import com.fanteng.finance.entity.ScheduleJob;
import com.fanteng.finance.quartz.dao.ScheduleJobDao;
import com.fanteng.finance.quartz.service.ScheduleJobService;
import com.fanteng.finance.quartz.util.QuartzJobFactory;
import com.fanteng.finance.quartz.util.QuartzJobFactoryDisallowConcurrentExecution;
import com.fanteng.util.DateUtil;
import com.fanteng.util.StringUtil;

@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobDao, ScheduleJob> implements ScheduleJobService {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 初始化定时任务
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void init() throws Exception {
		List<ScheduleJob> list = findAll();

		if (CollectionUtils.isNotEmpty(list)) {
			for (ScheduleJob scheduleJob : list) {
				addJob(scheduleJob);
			}
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	private void addJob(ScheduleJob scheduleJob) throws Exception {
		String jobName = scheduleJob.getJobName();
		String jobGroup = scheduleJob.getJobGroup();

		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());

		if (trigger == null) {
			JobDetail jobDetail = null;
			if (scheduleJob.getConcurrent() == ScheduleJob.CONCURRENT_IS) {
				Class<QuartzJobFactory> clazz = QuartzJobFactory.class;
				jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
			}
			if (scheduleJob.getConcurrent() == ScheduleJob.CONCURRENT_NOT) {
				Class<QuartzJobFactoryDisallowConcurrentExecution> clazz = QuartzJobFactoryDisallowConcurrentExecution.class;
				jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
			}

			jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);

			trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(schedBuilder).build();

			scheduler.scheduleJob(jobDetail, trigger);

			if (scheduleJob.getStatus() == ScheduleJob.STATUS_PAUSE) {
				pauseJob(scheduleJob.getId());
			}
		} else {
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(schedBuilder).build();

			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 暂停定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void pauseJob(String id) throws Exception {
		ScheduleJob scheduleJob = get(id);
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void resumeJob(String id) throws Exception {
		ScheduleJob scheduleJob = get(id);
		scheduleJob.setStatus(ScheduleJob.STATUS_NORMAL);
		update(scheduleJob);

		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 是否并发
	 * 
	 * @param id
	 * @param concurrent
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void concurrent(String id, Short concurrent) throws Exception {
		ScheduleJob scheduleJob = get(id);
		scheduleJob.setConcurrent(concurrent);
		update(scheduleJob);

		removeJob(id);
		addJob(scheduleJob);
	}

	/**
	 * 立即执行一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void runOnce(String id) throws Exception {
		ScheduleJob scheduleJob = get(id);
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 更新时间表达式
	 * 
	 * @param id
	 * @param cronExpression
	 * @throws Exception
	 */
	@Override
	public void updateCron(String id, String cronExpression) throws Exception {
		ScheduleJob scheduleJob = get(id);
		scheduleJob.setCronExpression(cronExpression);
		scheduleJob.setStatus(ScheduleJob.STATUS_NORMAL);
		update(scheduleJob);

		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.rescheduleJob(triggerKey, trigger);
	}

	/**
	 * 移除一个定时任务
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void removeJob(String id) throws Exception {
		ScheduleJob scheduleJob = get(id);
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseTrigger(triggerKey);
		scheduler.unscheduleJob(triggerKey);
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 停止所有定时任务
	 * 
	 * @throws Exception
	 */
	@Override
	public void shutdowns() throws Exception {
		scheduler.shutdown();
	}

	/**
	 * 分页获取定时任务列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public JsonResult queryList(Map<String, Object> param) throws Exception {
		Integer current = MapUtils.getInteger(param, "current");
		Integer size = MapUtils.getInteger(param, "size");
		String jobName = MapUtils.getString(param, "jobName");
		String jobGroup = MapUtils.getString(param, "jobGroup");
		Short status = MapUtils.getShort(param, "status");
		String beginTime = MapUtils.getString(param, "beginTime");
		String endTime = MapUtils.getString(param, "endTime");

		List<Condition> conditions = new ArrayList<Condition>(0);
		if (StringUtil.isNotBlank(jobName)) {
			Condition condition = new Condition("jobName", Operation.LIKE_ANY, jobName);
			conditions.add(condition);
		}
		if (StringUtil.isNotBlank(jobGroup)) {
			Condition condition = new Condition("jobGroup", Operation.LIKE_ANY, jobGroup);
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
		return new JsonResult(HttpStatus.OK, "操作成功", page);
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Serializable saveJob(ScheduleJob scheduleJob) throws Exception {
		Serializable id = save(scheduleJob);
		addJob(scheduleJob);
		return id;
	}

	/**
	 * 修改一个定时任务
	 * 
	 * @param scheduleJob
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateJob(ScheduleJob scheduleJob) throws Exception {
		boolean b = update(scheduleJob);
		updateCron(scheduleJob.getId(), scheduleJob.getCronExpression());
		return b;
	}

	/**
	 * 删除一个定时任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delJob(String id) throws Exception {
		removeJob(id);
		return delete(id);
	}

}
