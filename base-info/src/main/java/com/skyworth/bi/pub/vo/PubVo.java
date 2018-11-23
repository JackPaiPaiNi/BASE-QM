package com.skyworth.bi.pub.vo;

/**
 * 公共加载数据VO
 * @author 邓海
 */
public class PubVo {
	
	private String usercode; // 工号
	private Integer bmjcdm;// 部门级次
	private String timestamp; // 时间戳
	
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public Integer getBmjcdm() {
		return bmjcdm;
	}
	public void setBmjcdm(Integer bmjcdm) {
		this.bmjcdm = bmjcdm;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}