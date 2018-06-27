package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the proxy_account database table.
 * 
 */
@Entity
@Table(name = "proxy_account")
@NamedQuery(name = "ProxyAccount.findAll", query = "SELECT p FROM ProxyAccount p")
public class ProxyAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "frozen_price")
	private double frozenPrice;

	@Column(name = "proxy_id")
	private String proxyId;

	@Column(name = "settle_price")
	private double settlePrice;

	@Column(name = "subordinate_num")
	private int subordinateNum;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "usable_price")
	private double usablePrice;

	public ProxyAccount() {
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

	public double getFrozenPrice() {
		return this.frozenPrice;
	}

	public void setFrozenPrice(double frozenPrice) {
		this.frozenPrice = frozenPrice;
	}

	public String getProxyId() {
		return this.proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public double getSettlePrice() {
		return this.settlePrice;
	}

	public void setSettlePrice(double settlePrice) {
		this.settlePrice = settlePrice;
	}

	public int getSubordinateNum() {
		return this.subordinateNum;
	}

	public void setSubordinateNum(int subordinateNum) {
		this.subordinateNum = subordinateNum;
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

}