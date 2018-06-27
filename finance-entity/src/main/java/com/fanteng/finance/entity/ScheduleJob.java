package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the schedule_job database table.
 * 
 */
@Entity
@Table(name = "schedule_job")
@NamedQuery(name = "ScheduleJob.findAll", query = "SELECT s FROM ScheduleJob s")
public class ScheduleJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "bean_class")
	private String beanClass;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "cron_expression")
	private String cronExpression;

	@Column(name = "job_group")
	private String jobGroup;

	@Column(name = "job_name")
	private String jobName;

	@Column(name = "method_name")
	private String methodName;

	private String remark;

	private short status;

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