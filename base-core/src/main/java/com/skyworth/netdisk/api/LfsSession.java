package com.skyworth.netdisk.api;

public class LfsSession {
	
	public String status;//登录状态(SUCCESS,ERROR)
	public String cookieValue;//HTTP返回的cookie值，用于下次操作
	public int userId;//用户id
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
