package com.skyworth.core.base.gridExport;

public enum FileServiceMessage {
	FILE_SERVICE_ERROR("FSC001", "系统错误"),
	
	FILE_SAVE_ERROR("FSW001", "文件保存错误"),
	FILE_TOO_BIG("FSW002", "文件太大"),
	FILE_CANNOT_WRITE("FSW003", "文件没有写权限"),
	FILESYSTEM_NOT_ENOUGH("FSW004", "文件系统满"),
	
	FILE_GET_ERROR("FSR001", "获取文件出错"),
	FILE_CANNOT_READ("FSR002", "文件没有写权限"),
	FILE_INFO_NOT_EXIST("FSR003", "文件信息不存在"),
	FILE_NOT_EXIST("FSR004", "文件不存在"),
	;
	
	private String code;
	
	private String name;
	
	private FileServiceMessage(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return code + "   " + name;
	}

}
