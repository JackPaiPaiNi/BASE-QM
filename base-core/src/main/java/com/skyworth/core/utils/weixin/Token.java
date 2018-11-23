package com.skyworth.core.utils.weixin;

/**
 * 凭证
 * @author 魏诚
 * @date 2018-01-11
 */
public class Token {
	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	
	}
	
	public String toString() {
		String rst = "accessToken:"+this.accessToken+"expiresIn:"+this.expiresIn ;
		return  rst ;
	}

}