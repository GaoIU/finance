package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * The persistent class for the operate_config database table.
 * 
 */
@Entity
@Table(name = "operate_config")
@NamedQuery(name = "OperateConfig.findAll", query = "SELECT o FROM OperateConfig o")
public class OperateConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 类型：1-充值 */
	public static final short TYPE_RECHARGE = 1;

	/** 类型：2-提现 */
	public static final short TYPE_WITHDRAW = 2;

	/** 配置状态：0-正常 */
	public static final short STATUS_NORMAL = 0;

	/** 配置状态：1-禁用 */
	public static final short STATUS_DISABLE = 1;

	@Id
	private String id;

	@Column(name = "create_time")
	private Timestamp createTime;

	@NotNull(message = "配置名称不能为空")
	@Length(min = 1, max = 16, message = "配置名称长度不能超过16位")
	private String name;

	@NotNull(message = "配置状态不能为空")
	private Short status = STATUS_NORMAL;

	@NotNull(message = "配置类型不能为空")
	private Short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@NotNull(message = "配置值不能为空")
	private Short value;

	public OperateConfig() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

	public Short getValue() {
		return this.value;
	}

	public void setValue(Short value) {
		this.value = value;
	}

}