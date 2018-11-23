package com.skyworth.core.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 数据字典管理Entity
 * @author 魏诚
 */
public class Dict extends BaseEntity {
	
	private String bm;		// 编码
	private String mc;		// 名称
	private String zdlxbm;		// 字典类型编码
	private String zdlx;		// 字典类型
	private String xh;		// 序号
	private String bzxx;	// 备注信息
	
	public Dict() {
		super();
	}

	public Dict(String id){
		super(id);
	}

	/**
     * 编码
     */
	@Length(min=1, max=20, message="编码长度必须介于 1 和 20 之间")
	public String getBm() {
		return bm;
	}

	/**
     * 编码
     */
	public void setBm(String bm) {
		this.bm = bm == null ? null : bm.trim();
	}
	
	/**
     * 名称
     */
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getMc() {
		return mc;
	}

	/**
     * 名称
     */
	public void setMc(String mc) {
		this.mc = mc == null ? null : mc.trim();
	}
	
	/**
     * 字典类型编码
     */
	@Length(min=1, max=20, message="字典类型编码长度必须介于 1 和 20 之间")
	public String getZdlxbm() {
		return zdlxbm;
	}

	/**
     * 字典类型编码
     */
	public void setZdlxbm(String zdlxbm) {
		this.zdlxbm = zdlxbm == null ? null : zdlxbm.trim();
	}
	
	/**
     * 字典类型
     */
	@Length(min=0, max=100, message="字典类型长度必须介于 0 和 100 之间")
	public String getZdlx() {
		return zdlx;
	}

	/**
     * 字典类型
     */
	public void setZdlx(String zdlx) {
		this.zdlx = zdlx == null ? null : zdlx.trim();
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

	public String getBzxx() {
		return bzxx;
	}

	public void setBzxx(String bzxx) {
		this.bzxx = bzxx;
	}
	
}