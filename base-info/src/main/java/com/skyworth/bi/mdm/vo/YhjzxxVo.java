package com.skyworth.bi.mdm.vo;

import com.skyworth.bi.mdm.entity.Yhjzxx;

/**
 * 用户兼职信息 Vo
 * 
 * @author 高文浩
 * 
 */
public class YhjzxxVo extends Yhjzxx {
	
	private String bmid;	//部门ID
	private String bm;	//部门名称
	
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
	
	
}
