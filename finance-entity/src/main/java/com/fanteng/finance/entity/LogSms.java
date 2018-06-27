package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the log_sms database table.
 * 
 */
@Entity
@Table(name = "log_sms")
@NamedQuery(name = "LogSms.findAll", query = "SELECT l FROM LogSms l")
public class LogSms implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "create_time")
	private Timestamp createTime;

	private String mobile;

	private String msg;

	@Column(name = "send_result")
	private short sendResult;

	@Column(name = "send_time")
	private Timestamp sendTime;

	public LogSms() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public short getSendResult() {
		return this.sendResult;
	}

	public void setSendResult(short sendResult) {
		this.sendResult = sendResult;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

}