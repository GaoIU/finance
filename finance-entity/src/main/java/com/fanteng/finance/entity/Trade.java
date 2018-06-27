package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the trade database table.
 * 
 */
@Entity
@NamedQuery(name = "Trade.findAll", query = "SELECT t FROM Trade t")
public class Trade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "cancel_the_order_num")
	private int cancelTheOrderNum;

	private String code;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "entrust_direction")
	private short entrustDirection;

	@Column(name = "entrust_frozen_price")
	private double entrustFrozenPrice;

	@Column(name = "entrust_num")
	private int entrustNum;

	@Column(name = "entrust_price")
	private double entrustPrice;

	@Column(name = "entrust_time")
	private Timestamp entrustTime;

	@Column(name = "entrust_type")
	private short entrustType;

	@Column(name = "fail_price")
	private double failPrice;

	private double fee;

	private short forcedsell;

	private String market;

	private String name;

	@Column(name = "position_id")
	private String positionId;

	@Column(name = "room_id")
	private String roomId;

	private short status;

	@Column(name = "trade_amount")
	private double tradeAmount;

	@Column(name = "trade_num")
	private int tradeNum;

	@Column(name = "trade_price")
	private double tradePrice;

	@Column(name = "trade_time")
	private Timestamp tradeTime;

	@Column(name = "trade_type")
	private short tradeType;

	private short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "win_price")
	private double winPrice;

	public Trade() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCancelTheOrderNum() {
		return this.cancelTheOrderNum;
	}

	public void setCancelTheOrderNum(int cancelTheOrderNum) {
		this.cancelTheOrderNum = cancelTheOrderNum;
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

	public short getEntrustDirection() {
		return this.entrustDirection;
	}

	public void setEntrustDirection(short entrustDirection) {
		this.entrustDirection = entrustDirection;
	}

	public double getEntrustFrozenPrice() {
		return this.entrustFrozenPrice;
	}

	public void setEntrustFrozenPrice(double entrustFrozenPrice) {
		this.entrustFrozenPrice = entrustFrozenPrice;
	}

	public int getEntrustNum() {
		return this.entrustNum;
	}

	public void setEntrustNum(int entrustNum) {
		this.entrustNum = entrustNum;
	}

	public double getEntrustPrice() {
		return this.entrustPrice;
	}

	public void setEntrustPrice(double entrustPrice) {
		this.entrustPrice = entrustPrice;
	}

	public Timestamp getEntrustTime() {
		return this.entrustTime;
	}

	public void setEntrustTime(Timestamp entrustTime) {
		this.entrustTime = entrustTime;
	}

	public short getEntrustType() {
		return this.entrustType;
	}

	public void setEntrustType(short entrustType) {
		this.entrustType = entrustType;
	}

	public double getFailPrice() {
		return this.failPrice;
	}

	public void setFailPrice(double failPrice) {
		this.failPrice = failPrice;
	}

	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public short getForcedsell() {
		return this.forcedsell;
	}

	public void setForcedsell(short forcedsell) {
		this.forcedsell = forcedsell;
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

	public String getPositionId() {
		return this.positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public double getTradeAmount() {
		return this.tradeAmount;
	}

	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public int getTradeNum() {
		return this.tradeNum;
	}

	public void setTradeNum(int tradeNum) {
		this.tradeNum = tradeNum;
	}

	public double getTradePrice() {
		return this.tradePrice;
	}

	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Timestamp getTradeTime() {
		return this.tradeTime;
	}

	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}

	public short getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(short tradeType) {
		this.tradeType = tradeType;
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