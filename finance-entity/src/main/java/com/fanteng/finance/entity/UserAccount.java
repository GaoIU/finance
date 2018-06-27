package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the user_account database table.
 * 
 */
@Entity
@Table(name = "user_account")
@NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u")
public class UserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "cash_price")
	private double cashPrice;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "frozen_price")
	private double frozenPrice;

	@Column(name = "pay_price")
	private double payPrice;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "usable_price")
	private double usablePrice;

	@Column(name = "user_id")
	private String userId;

	public UserAccount() {
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

	public double getCashPrice() {
		return this.cashPrice;
	}

	public void setCashPrice(double cashPrice) {
		this.cashPrice = cashPrice;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public double getFrozenPrice() {
		return this.frozenPrice;
	}

	public void setFrozenPrice(double frozenPrice) {
		this.frozenPrice = frozenPrice;
	}

	public double getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public double getUsablePrice() {
		return this.usablePrice;
	}

	public void setUsablePrice(double usablePrice) {
		this.usablePrice = usablePrice;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}