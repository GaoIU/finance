package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the sys_config database table.
 * 
 */
@Entity
@Table(name = "sys_config")
@NamedQuery(name = "SysConfig.findAll", query = "SELECT s FROM SysConfig s")
public class SysConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 系统参数配置状态：0-正常 */
	public static final short STATUS_NORMAL = 0;

	/** 系统参数配置状态：1-禁用 */
	public static final short STATUS_DISABLE = 1;

	@Id
	private String id;

	@NotBlank(message = "配置编码不能为空")
	@Length(min = 1, max = 32, message = "配置编码长度不能大于32位")
	private String code;

	@NotBlank(message = "配置内容不能为空")
	@Lob
	private String content;

	@Column(name = "create_time")
	private Timestamp createTime;

	private String description;

	private String name;

	private Short status = STATUS_NORMAL;

	@Column(name = "update_time")
	private Timestamp updateTime;

	public SysConfig() {
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}