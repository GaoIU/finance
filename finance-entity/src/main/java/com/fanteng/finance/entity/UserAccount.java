package com.fanteng.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "available_amount")
	private BigDecimal availableAmount = BigDecimal.ZERO;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "frozen_amount")
	private BigDecimal frozenAmount = BigDecimal.ZERO;

	@Column(name = "update_time")
	private Timestamp updateTime;

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

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailableAmount() {
		return this.availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getFrozenAmount() {
		return this.frozenAmount;
	}

	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
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