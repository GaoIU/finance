package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the cash_order database table.
 * 
 */
@Entity
@Table(name = "cash_order")
@NamedQuery(name = "CashOrder.findAll", query = "SELECT c FROM CashOrder c")
public class CashOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	private double fee;

	@Column(name = "operate_mode_id")
	private String operateModeId;

	@Column(name = "operate_mode_type")
	private short operateModeType;

	@Column(name = "order_no")
	private String orderNo;

	private short status;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "user_id")
	private String userId;

	public CashOrder() {
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

	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getOperateModeId() {
		return this.operateModeId;
	}

	public void setOperateModeId(String operateModeId) {
		this.operateModeId = operateModeId;
	}

	public short getOperateModeType() {
		return this.operateModeType;
	}

	public void setOperateModeType(short operateModeType) {
		this.operateModeType = operateModeType;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}