package com.skyworth.bi.mdm.entity;

import com.skyworth.core.base.BaseEntity;

/**
 * 用户兼职信息 Entity
 * @author 高文浩
 * 
 * */

public class Yhjzxx extends BaseEntity {

	private String gh;	//工号
	private String xm;	//姓名
	private Integer jzgwid;	//兼职岗位ID
	private String jzgwmc;	//兼职岗位名称
	private String jzbmid;	//兼职部门ID
	private String jzbmmc;	//兼职部门名称
	
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public Integer getJzgwid() {
		return jzgwid;
	}
	public void setJzgwid(Integer jzgwid) {
		this.jzgwid = jzgwid;
	}
	public String getJzgwmc() {
		return jzgwmc;
	}
	public void setJzgwmc(String jzgwmc) {
		this.jzgwmc = jzgwmc;
	}
	public String getJzbmid() {
		return jzbmid;
	}
	public void setJzbmid(String jzbmid) {
		this.jzbmid = jzbmid;
	}
	public String getJzbmmc() {
		return jzbmmc;
	}
	public void setJzbmmc(String jzbmmc) {
		this.jzbmmc = jzbmmc;
	}
}
