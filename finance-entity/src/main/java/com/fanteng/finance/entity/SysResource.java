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
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the sys_resource database table.
 * 
 */
@Entity
@Table(name = "sys_resource")
@NamedQuery(name = "SysResource.findAll", query = "SELECT s FROM SysResource s")
public class SysResource implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 后台资源状态：0-正常 */
	public static final short status_normal = 0;

	/** 后台资源状态：1-禁用 */
	public static final short status_disable = 1;

	/** 后台资源类型：0-菜单 */
	public static final short type_menu = 0;

	/** 后台资源类型：1-按钮 */
	public static final short type_button = 1;

	/** 后台资源类型：2-功能 */
	public static final short type_function = 2;

	@Id
	private String id;

	@NotBlank(message = "资源编码不能为空")
	@Length(max = 32, message = "资源编码不能超过32位长度")
	private String code;

	@Column(name = "create_time")
	private Timestamp createTime;

	private String description;

	private String icon;

	@NotBlank(message = "访问方式不能为空")
	private String method;

	@NotBlank(message = "资源名称不能为空")
	@Length(max = 16, message = "资源名称不能超过16位长度")
	private String name;

	@Column(name = "parent_id")
	private String parentId;

	private Short sort = 0;

	private Short status = status_normal;

	@NotNull(message = "请选择资源类型")
	private Short type;

	@Column(name = "update_time")
	private Timestamp updateTime;

	private String url;

	public SysResource() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}