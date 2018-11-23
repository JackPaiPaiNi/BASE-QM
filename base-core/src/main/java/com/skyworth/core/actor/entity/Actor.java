package com.skyworth.core.actor.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 岗位信息管理Entity
 * @author 魏诚
 */
public class Actor extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -7186007843115828154L;
	
	private String gw;		// 岗位
	private String bzxx;		// 备注信息
	
	public Actor() {
		super();
	}

	public Actor(String id){
		super(id);
	}

	/**
     * 岗位
     */
	@Length(min=1, max=100, message="岗位长度必须介于 1 和 100 之间")
	public String getGw() {
		return gw;
	}

	/**
     * 岗位
     */
	public void setGw(String gw) {
		this.gw = gw == null ? null : gw.trim();
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