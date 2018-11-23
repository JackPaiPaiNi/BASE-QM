package com.skyworth.core.menu.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 菜单信息管理Entity
 * @author 魏诚
 */
public class Menu extends BaseEntity {
	
	private String cd;		// 菜单
	private Integer sjcdid;		// 上级菜单ID
	private Integer sfmj;		// 是否末级
	private String xh;		// 序号
	private String ljdz;		// 链接地址
	private Integer sfbkzqx;		// 是否不控制权限
	private String tb;		//	图标
	
	public Menu() {
		super();
	}

	public Menu(String id){
		super(id);
	}

	/**
     * 菜单
     */
	@Length(min=1, max=100, message="菜单长度必须介于 1 和 100 之间")
	public String getCd() {
		return cd;
	}

	/**
     * 菜单
     */
	public void setCd(String cd) {
		this.cd = cd == null ? null : cd.trim();
	}
	
	/**
     * 上级菜单ID
     */
	public Integer getSjcdid() {
		return sjcdid;
	}

	/**
     * 上级菜单ID
     */
	public void setSjcdid(Integer sjcdid) {
		this.sjcdid = sjcdid;
	}
	
	/**
     * 是否末级
     */
	@NotNull(message="是否末级不能为空")
	public Integer getSfmj() {
		return sfmj;
	}

	/**
     * 是否末级
     */
	public void setSfmj(Integer sfmj) {
		this.sfmj = sfmj;
	}
	
	/**
     * 序号
     */
	@Length(min=0, max=20, message="序号长度必须介于 0 和 20 之间")
	public String getXh() {
		return xh;
	}

	/**
     * 序号
     */
	public void setXh(String xh) {
		this.xh = xh == null ? null : xh.trim();
	}
	
	/**
     * 链接地址
     */
	@Length(min=0, max=200, message="链接地址长度必须介于 0 和 200 之间")
	public String getLjdz() {
		return ljdz;
	}

	/**
     * 链接地址
     */
	public void setLjdz(String ljdz) {
		this.ljdz = ljdz == null ? null : ljdz.trim();
	}
	
	/**
     * 是否不控制权限
     */
	public Integer getSfbkzqx() {
		return sfbkzqx;
	}

	/**
     * 是否不控制权限
     */
	public void setSfbkzqx(Integer sfbkzqx) {
		this.sfbkzqx = sfbkzqx;
	}
	
	/**
     * 菜单
     */
	@Length(min=1, max=50, message="图标长度必须介于 1 和 50 之间")
	public String getTb() {
		return tb;
	}

	/**
     * 菜单
     */
	public void setTb(String tb) {
		this.tb = tb == null ? null : tb.trim();
	}
	
}