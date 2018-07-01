package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the sys_user_role database table.
 * 
 */
@Entity
@Table(name = "sys_user_role")
@NamedQuery(name = "SysUserRole.findAll", query = "SELECT s FROM SysUserRole s")
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "sys_role_id")
	private String sysRoleId;

	@Column(name = "sys_user_id")
	private String sysUserId;

	public SysUserRole() {
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

	public String getSysRoleId() {
		return this.sysRoleId;
	}

	public void setSysRoleId(String sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	public String getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public SysUserRole(String sysRoleId, String sysUserId) {
		super();
		this.sysRoleId = sysRoleId;
		this.sysUserId = sysUserId;
	}

}