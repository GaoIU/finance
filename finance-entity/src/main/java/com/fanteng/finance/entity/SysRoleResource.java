package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the sys_role_resource database table.
 * 
 */
@Entity
@Table(name = "sys_role_resource")
@NamedQuery(name = "SysRoleResource.findAll", query = "SELECT s FROM SysRoleResource s")
public class SysRoleResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "sys_resource_id")
	private String sysResourceId;

	@Column(name = "sys_role_id")
	private String sysRoleId;

	public SysRoleResource() {
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

	public String getSysResourceId() {
		return this.sysResourceId;
	}

	public void setSysResourceId(String sysResourceId) {
		this.sysResourceId = sysResourceId;
	}

	public String getSysRoleId() {
		return this.sysRoleId;
	}

	public void setSysRoleId(String sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

}