package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the room database table.
 * 
 */
@Entity
@NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "base_room_id")
	private String baseRoomId;

	@Column(name = "bond_amount")
	private double bondAmount;

	@Column(name = "close_position")
	private double closePosition;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "entrust_price")
	private double entrustPrice;

	private double fee;

	@Column(name = "frozen_price")
	private double frozenPrice;

	private int multiple;

	private double principal;

	private short status;

	private short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@Column(name = "usable_price")
	private double usablePrice;

	@Column(name = "user_id")
	private String userId;

	public Room() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseRoomId() {
		return this.baseRoomId;
	}

	public void setBaseRoomId(String baseRoomId) {
		this.baseRoomId = baseRoomId;
	}

	public double getBondAmount() {
		return this.bondAmount;
	}

	public void setBondAmount(double bondAmount) {
		this.bondAmount = bondAmount;
	}

	public double getClosePosition() {
		return this.closePosition;
	}

	public void setClosePosition(double closePosition) {
		this.closePosition = closePosition;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public double getEntrustPrice() {
		return this.entrustPrice;
	}

	public void setEntrustPrice(double entrustPrice) {
		this.entrustPrice = entrustPrice;
	}

	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getFrozenPrice() {
		return this.frozenPrice;
	}

	public void setFrozenPrice(double frozenPrice) {
		this.frozenPrice = frozenPrice;
	}

	public int getMultiple() {
		return this.multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public double getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
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