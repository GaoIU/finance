package com.fanteng.finance.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name = "sys_user")
@NamedQuery(name = "SysUser.findAll", query = "SELECT s FROM SysUser s")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 后台用户状态：0-正常 */
	public static final short status_normal = 0;

	/** 后台用户状态：1-禁用 */
	public static final short status_disable = 1;

	@Id
	private String id;

	private String avatar;

	@Column(name = "create_time")
	private Timestamp createTime;

	@NotBlank(message = "手机号码不能为空")
	@Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "手机号码不合法")
	private String mobile;

	@NotBlank(message = "用户昵称不能为空")
	@Length(min = 1, max = 8, message = "用户昵称长度不能大于8位")
	@Column(name = "nick_name")
	private String nickName;

	private String password;

	@Column(name = "real_name")
	private String realName;

	@Transient
	private String sysRoleIds;

	private short status = status_normal;

	@Column(name = "update_time")
	private Timestamp updateTime;

	@NotBlank(message = "用户账号不能为空")
	@Length(min = 1, max = 8, message = "用户账号长度不能大于8位")
	@Column(name = "user_name")
	private String userName;

	public SysUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSysRoleIds() {
		return sysRoleIds;
	}

	public void setSysRoleIds(String sysRoleIds) {
		this.sysRoleIds = sysRoleIds;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}