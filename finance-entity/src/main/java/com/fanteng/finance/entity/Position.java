package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the position database table.
 * 
 */
@Entity
@NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p")
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "buy_fee")
	private double buyFee;

	@Column(name = "buy_num")
	private int buyNum;

	@Column(name = "buy_price")
	private double buyPrice;

	private String code;

	@Column(name = "create_time")
	private Timestamp createTime;

	private short direction;

	@Column(name = "fail_price")
	private double failPrice;

	@Column(name = "frozen_num")
	private int frozenNum;

	private String market;

	private String name;

	private int num;

	@Column(name = "room_id")
	private String roomId;

	@Column(name = "sell_fee")
	private double sellFee;

	@Column(name = "sell_price")
	private double sellPrice;

	private short status;

	private short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "usable_num")
	private int usableNum;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "win_price")
	private double winPrice;

	public Position() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getBuyFee() {
		return this.buyFee;
	}

	public void setBuyFee(double buyFee) {
		this.buyFee = buyFee;
	}

	public int getBuyNum() {
		return this.buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public double getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public short getDirection() {
		return this.direction;
	}

	public void setDirection(short direction) {
		this.direction = direction;
	}

	public double getFailPrice() {
		return this.failPrice;
	}

	public void setFailPrice(double failPrice) {
		this.failPrice = failPrice;
	}

	public int getFrozenNum() {
		return this.frozenNum;
	}

	public void setFrozenNum(int frozenNum) {
		this.frozenNum = frozenNum;
	}

	public String getMarket() {
		return this.market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public double getSellFee() {
		return this.sellFee;
	}

	public void setSellFee(double sellFee) {
		this.sellFee = sellFee;
	}

	public double getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getType() {
		return this.type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getUsableNum() {
		return this.usableNum;
	}

	public void setUsableNum(int usableNum) {
		this.usableNum = usableNum;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getWinPrice() {
		return this.winPrice;
	}

	public void setWinPrice(double winPrice) {
		this.winPrice = winPrice;
	}

}