package com.skyworth.bi.pub.vo;

/**
 * 公共加载数据VO
 * @author 邓海
 */
public class PubBmVo {
	
	private String bmid;	 // 部门编码
	private String bm;		 // 名称
	private String cwgs;	 // 公司全程
	
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
	public String getCwgs() {
		return cwgs;
	}
	public void setCwgs(String cwgs) {
		this.cwgs = cwgs;
	}
	
}