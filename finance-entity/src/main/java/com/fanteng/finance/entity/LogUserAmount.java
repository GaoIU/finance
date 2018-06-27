package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the log_user_amount database table.
 * 
 */
@Entity
@Table(name = "log_user_amount")
@NamedQuery(name = "LogUserAmount.findAll", query = "SELECT l FROM LogUserAmount l")
public class LogUserAmount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "operate_result")
	private short operateResult;

	@Column(name = "operate_type")
	private short operateType;

	@Column(name = "order_no")
	private String orderNo;

	private String remarks;

	@Column(name = "user_id")
	private String userId;

	public LogUserAmount() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public short getOperateResult() {
		return this.operateResult;
	}

	public void setOperateResult(short operateResult) {
		this.operateResult = operateResult;
	}

	public short getOperateType() {
		return this.operateType;
	}

	public void setOperateType(short operateType) {
		this.operateType = operateType;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}