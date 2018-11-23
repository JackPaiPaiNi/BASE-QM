package com.skyworth.core.menu.entity;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 菜单权限管理Entity
 * @author 魏诚
 */
public class MenuPermission extends BaseEntity {
	
	private Integer cdid;		// 菜单ID
	private String qxbm;		// 权限编码
	private String qx;		// 权限
	private Integer xh;		// 序号
	private String bzxx;		// 备注信息
	
	public MenuPermission() {
		super();
	}

	public MenuPermission(String id){
		super(id);
	}

	/**
     * 菜单ID
     */
	public Integer getCdid() {
		return cdid;
	}

	/**
     * 菜单ID
     */
	public void setCdid(Integer cdid) {
		this.cdid = cdid;
	}
	
	/**
     * 权限编码
     */
	@Length(min=1, max=100, message="权限编码长度必须介于 1 和 100 之间")
	public String getQxbm() {
		return qxbm;
	}

	/**
     * 权限编码
     */
	public void setQxbm(String qxbm) {
		this.qxbm = qxbm == null ? null : qxbm.trim();
	}
	
	/**
     * 权限
     */
	@Length(min=1, max=50, message="权限长度必须介于 1 和 50 之间")
	public String getQx() {
		return qx;
	}

	/**
     * 权限
     */
	public void setQx(String qx) {
		this.qx = qx == null ? null : qx.trim();
	}
	
	/**
     * 序号
     */
	public Integer getXh() {
		return xh;
	}

	/**
     * 序号
     */
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	
	/**
     * 备注信息
     */
	@Length(min=0, max=200, message="备注信息长度必须介于 0 和 200 之间")
	public String getBzxx() {
		return bzxx;
	}

	/**
     * 备注信息
     */
	public void setBzxx(String bzxx) {
		this.bzxx = bzxx == null ? null : bzxx.trim();
	}
	
}