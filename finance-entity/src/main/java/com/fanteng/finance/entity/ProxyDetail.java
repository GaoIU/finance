package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the proxy_detail database table.
 * 
 */
@Entity
@Table(name = "proxy_detail")
@NamedQuery(name = "ProxyDetail.findAll", query = "SELECT p FROM ProxyDetail p")
public class ProxyDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double amount;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "frozen_price")
	private double frozenPrice;

	@Column(name = "pay_price")
	private double payPrice;

	@Column(name = "proxy_id")
	private String proxyId;

	private short status;

	@Column(name = "subordinate_contribution")
	private double subordinateContribution;

	private Timestamp time;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "usable_price")
	private double usablePrice;

	public ProxyDetail() {
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

	public double getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public String getProxyId() {
		return this.proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public double getSubordinateContribution() {
		return this.subordinateContribution;
	}

	public void setSubordinateContribution(double subordinateContribution) {
		this.subordinateContribution = subordinateContribution;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
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