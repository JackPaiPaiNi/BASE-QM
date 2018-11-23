package com.skyworth.core.user.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

/**
 * 用户信息管理Entity
 * @author 魏诚
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 403892485790339599L;
	
	private String gh;		// 工号
	private String xm;		// 姓名
	private String hrzw;	// 职务
	private String hrgw;	// 岗位
	private String ywlx;	// 业务类型
	private String bmjc;	// 部门级次
	private String mm;		// 密码
	private String hrzt;	// 状态（0：离职、1：在职）
	private String bmid;
	private String bm;
	private Integer zt;
	
	public User() {
		super();
	}

	/**
     * 工号
     */
	@Length(min=1, max=20, message="工号长度必须介于 1 和 20 之间")
	public String getGh() {
		return gh;
	}

	/**
     * 工号
     */
	public void setGh(String gh) {
		this.gh = gh == null ? null : gh.trim();
	}
	
	/**
     * 姓名
     */
	@Length(min=1, max=50, message="姓名长度必须介于 1 和 50 之间")
	public String getXm() {
		return xm;
	}

	/**
     * 姓名
     */
	public void setXm(String xm) {
		this.xm = xm == null ? null : xm.trim();
	}
	
	/**
     * 密码
     */
	@Length(min=1, max=50, message="密码长度必须介于 1 和 50 之间")
	public String getMm() {
		return mm;
	}

	/**
     * 密码
     */
	public void setMm(String mm) {
		this.mm = mm == null ? null : mm.trim();
	}
	
	public String getHrzw() {
		return hrzw;
	}

	public void setHrzw(String hrzw) {
		this.hrzw = hrzw;
	}

	public String getHrgw() {
		return hrgw;
	}

	public void setHrgw(String hrgw) {
		this.hrgw = hrgw;
	}

	public String getYwlx() {
		return ywlx;
	}

	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}

	public String getBmjc() {
		return bmjc;
	}

	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}

	public String getHrzt() {
		return hrzt;
	}

	public void setHrzt(String hrzt) {
		this.hrzt = hrzt;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid;
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
}