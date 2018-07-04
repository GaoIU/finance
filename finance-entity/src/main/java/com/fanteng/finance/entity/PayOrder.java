package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the pay_order database table.
 * 
 */
@Entity
@Table(name = "pay_order")
@NamedQuery(name = "PayOrder.findAll", query = "SELECT p FROM PayOrder p")
public class PayOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "fictitious_coin")
	private double fictitiousCoin;

	@Column(name = "operate_mode_id")
	private String operateModeId;

	@Column(name = "operate_mode_type")
	private short operateModeType;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "product_id")
	private String productId;

	@Column(name = "review_desc")
	private String reviewDesc;

	@Column(name = "review_time")
	private Timestamp reviewTime;

	private short status;

	@Column(name = "sys_user_id")
	private String sysUserId;

	@Column(name = "sys_user_name")
	private String sysUserName;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "user_id")
	private String userId;

	public PayOrder() {
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

	public double getFictitiousCoin() {
		return this.fictitiousCoin;
	}

	public void setFictitiousCoin(double fictitiousCoin) {
		this.fictitiousCoin = fictitiousCoin;
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

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getReviewDesc() {
		return this.reviewDesc;
	}

	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
	}

	public Timestamp getReviewTime() {
		return this.reviewTime;
	}

	public void setReviewTime(Timestamp reviewTime) {
		this.reviewTime = reviewTime;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
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