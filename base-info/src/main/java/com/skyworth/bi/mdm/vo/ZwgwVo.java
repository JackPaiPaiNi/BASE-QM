package com.skyworth.bi.mdm.vo;

import com.skyworth.bi.mdm.entity.Zwgw;

/**
 * 职务岗位对应关系VO
 * @author tianrong
 * */
public class ZwgwVo extends Zwgw {
	
	private String gw; //岗位
	
	public ZwgwVo(){
		super();
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

}