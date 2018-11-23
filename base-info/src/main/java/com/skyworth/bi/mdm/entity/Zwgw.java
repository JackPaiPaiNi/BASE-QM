package com.skyworth.bi.mdm.entity;

import com.skyworth.core.base.BaseEntity;

/**
 * 职务岗位对应关系
 * @author tianrong
 * */
public class Zwgw extends BaseEntity {
	
	private String bmjc;	//部門級次
	private String hrzw;	//HR职务
	private String gwid;	//岗位ID
	
	public String getBmjc() {
		return bmjc;
	}
	public void setBmjc(String bmjc) {
		this.bmjc = bmjc;
	}
	public String getHrzw() {
		return hrzw;
	}
	public void setHrzw(String hrzw) {
		this.hrzw = hrzw;
	}
	public String getGwid() {
		return gwid;
	}
	public void setGwid(String gwid) {
		this.gwid = gwid;
	}

	
}