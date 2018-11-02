package com.fanteng.finance.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The persistent class for the recharge_order database table.
 * 
 */
@Entity
@Table(name = "recharge_order")
@NamedQuery(name = "RechargeOrder.findAll", query = "SELECT r FROM RechargeOrder r")
public class RechargeOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 充值方式：1-支付宝 */
	public static final short TYPE_ALIPAY = 1;

	/** 充值方式：2-微信 */
	public static final short TYPE_WECHAT = 2;

	/** 充值方式：3-银行卡 */
	public static final short TYPE_BANK_CARD = 3;

	/** 充值状态：0-审核中 */
	public static final short STATUS_REVIEW_WAITING = 0;

	/** 充值状态：1-审核通过 */
	public static final short STATUS_REVIEW_PASS = 1;

	/** 充值状态：2-审核拒绝 */
	public static final short STATUS_REVIEW_REFUSE = 2;

	@Id
	private String id;

	private BigDecimal amount = BigDecimal.ZERO;

	@Column(name = "audit_note")
	private String auditNote;

	@Column(name = "audit_time")
	private Timestamp auditTime;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "order_no")
	private String orderNo;

	private Short status = STATUS_REVIEW_WAITING;

	@Column(name = "sys_user_id")
	private String sysUserId;

	@Column(name = "sys_user_name")
	private String sysUserName;

	@NotNull(message = "请选择充值方式")
	private Short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String userName;

	public RechargeOrder() {
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

	public String getAuditNote() {
		return this.auditNote;
	}

	public void setAuditNote(String auditNote) {
		this.auditNote = auditNote;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSysUserName() {
		return this.sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}