package com.skyworth.core.base.entity;

public class ResultEntity {
	
	private String result;	// 请求响应结果 0：失败
	private String message;	// 请求响应消息
	private Object vo;	// 请求实体
	private String token;//请求响应token值
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getVo() {
		return vo;
	}
	public void setVo(Object vo) {
		this.vo = vo;
	}
	
}
