package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the schedule_job database table.
 * 
 */
@Entity
@Table(name = "schedule_job")
@NamedQuery(name = "ScheduleJob.findAll", query = "SELECT s FROM ScheduleJob s")
public class ScheduleJob implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 定时任务配置状态：0-正常 */
	public final static short STATUS_NORMAL = 0;

	/** 定时任务配置状态：1-暂停 */
	public final static short STATUS_PAUSE = 1;
	
	/** 定时任务是否并发状态：0-是 */
	public final static short CONCURRENT_IS = 0;
	
	/** 定时任务是否并发状态：1-否 */
	public final static short CONCURRENT_NOT = 1;

	@Id
	private String id;

	@NotBlank(message = "定时任务所在的类路径不能为空")
	@Column(name = "bean_class")
	private String beanClass;
	
	private short concurrent = CONCURRENT_NOT;

	@Column(name = "create_time")
	private Timestamp createTime;

	@NotBlank(message = "时间表达式不能为空")
	@Column(name = "cron_expression")
	private String cronExpression;

	@NotBlank(message = "任务组不能为空")
	@Column(name = "job_group")
	private String jobGroup;

	@NotBlank(message = "任务名不能为空")
	@Column(name = "job_name")
	private String jobName;

	@NotBlank(message = "要执行的方法名不能为空")
	@Column(name = "method_name")
	private String methodName;

	private String remark;

	private short status = STATUS_NORMAL;

	@Column(name = "update_time")
	private Timestamp updateTime;

	public ScheduleJob() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeanClass() {
		return this.beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public short getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(short concurrent) {
		this.concurrent = concurrent;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}