package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the proxy database table.
 * 
 */
@Entity
@NamedQuery(name = "Proxy.findAll", query = "SELECT p FROM Proxy p")
public class Proxy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "audit_status")
	private short auditStatus;

	@Column(name = "audit_time")
	private Timestamp auditTime;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "bank_no")
	private String bankNo;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "extract_ratio")
	private double extractRatio;

	@Column(name = "extract_time")
	private Timestamp extractTime;

	@Column(name = "id_card")
	private String idCard;

	@Column(name = "last_login_time")
	private Timestamp lastLoginTime;

	private String mobile;

	private String name;

	@Column(name = "parent_id")
	private String parentId;

	private String password;

	@Column(name = "proxy_level")
	private short proxyLevel;

	private short sex;

	private short status;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "user_id")
	private String userId;

	public Proxy() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public short getAuditStatus() {
		return this.auditStatus;
	}

	public void setAuditStatus(short auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public double getExtractRatio() {
		return this.extractRatio;
	}

	public void setExtractRatio(double extractRatio) {
		this.extractRatio = extractRatio;
	}

	public Timestamp getExtractTime() {
		return this.extractTime;
	}

	public void setExtractTime(Timestamp extractTime) {
		this.extractTime = extractTime;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getProxyLevel() {
		return this.proxyLevel;
	}

	public void setProxyLevel(short proxyLevel) {
		this.proxyLevel = proxyLevel;
	}

	public short getSex() {
		return this.sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
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