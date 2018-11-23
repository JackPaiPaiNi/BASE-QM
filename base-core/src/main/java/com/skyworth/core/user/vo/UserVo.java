package com.skyworth.core.user.vo;

import com.skyworth.core.user.entity.User;

/**
 * 用户信息管理Entity
 * @author 魏诚
 */
public class UserVo extends User {
	
	private static final long serialVersionUID = 5636739519751550310L;
	
	private Integer mrgwid; //默认岗位id
	private String mrgw;  //默认岗位
	private String gwids; //附加岗位id列表
	private String cwgs; // 公司全称
	private String openid; // 微信openid
	
	public UserVo() {
		super();
	}

	public Integer getMrgwid() {
		return mrgwid;
	}

	public void setMrgwid(Integer mrgwid) {
		this.mrgwid = mrgwid;
	}

	public String getMrgw() {
		return mrgw;
	}

	public void setMrgw(String mrgw) {
		this.mrgw = mrgw;
	}

	public String getGwids() {
		return gwids;
	}

	public void setGwids(String gwids) {
		this.gwids = gwids;
	}

	public String getCwgs() {
		return cwgs;
	}

	public void setCwgs(String cwgs) {
		this.cwgs = cwgs;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}