package com.skyworth.core.dict.entity;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 字典类型管理Entity
 * @author 魏诚
 */
public class DictType extends BaseEntity {
	
	private String zdlxbm;		// 字典类型编码
	private String zdlx;		// 字典类型
	private String bzxx;		// 备注信息
	
	public DictType() {
		super();
	}

	public DictType(String id){
		super(id);
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
	@Length(min=1, max=100, message="字典类型长度必须介于 1 和 100 之间")
	public String getZdlx() {
		return zdlx;
	}

	/**
     * 字典类型
     */
	public void setZdlx(String zdlx) {
		this.zdlx = zdlx == null ? null : zdlx.trim();
	}

	public String getBzxx() {
		return bzxx;
	}

	public void setBzxx(String bzxx) {
		this.bzxx = bzxx;
	}
	
}