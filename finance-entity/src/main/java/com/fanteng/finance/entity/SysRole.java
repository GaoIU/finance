package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the sys_role database table.
 * 
 */
@Entity
@Table(name = "sys_role")
@NamedQuery(name = "SysRole.findAll", query = "SELECT s FROM SysRole s")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotBlank(message = "角色编码不能为空")
	@Length(min = 1, max = 32, message = "角色编码长度不能超过32位")
	private String code;

	@Column(name = "create_time")
	private Timestamp createTime;

	@NotBlank(message = "角色名称不能为空")
	@Length(min = 1, max = 16, message = "角色名称长度不能超过16位")
	private String name;

	@Transient
	private String sysResourceIds;

	private short status;

	@Column(name = "update_time")
	private Timestamp updateTime;

	public SysRole() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSysResourceIds() {
		return sysResourceIds;
	}

	public void setSysResourceIds(String sysResourceIds) {
		this.sysResourceIds = sysResourceIds;
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

}