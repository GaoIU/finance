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
 * The persistent class for the log_user_account database table.
 * 
 */
@Entity
@Table(name = "log_user_account")
@NamedQuery(name = "LogUserAccount.findAll", query = "SELECT l FROM LogUserAccount l")
public class LogUserAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 操作类型：0-注册奖励 */
	public static final short OPERATION_TYPE_REGISTER = 0;

	@Id
	private String id;

	private BigDecimal amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "operation_type")
	private short operationType;

	@Column(name = "user_account_id")
	private String userAccountId;

	@Column(name = "user_id")
	private String userId;

	public LogUserAccount() {
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

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public short getOperationType() {
		return this.operationType;
	}

	public void setOperationType(short operationType) {
		this.operationType = operationType;
	}

	public String getUserAccountId() {
		return this.userAccountId;
	}

	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LogUserAccount(BigDecimal amount, short operationType, String userAccountId, String userId) {
		super();
		this.amount = amount;
		this.operationType = operationType;
		this.userAccountId = userAccountId;
		this.userId = userId;
	}

}