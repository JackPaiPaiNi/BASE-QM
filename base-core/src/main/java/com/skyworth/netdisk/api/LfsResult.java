package com.skyworth.netdisk.api;

import java.util.Date;

public class LfsResult{
	
	// 文件ID删除用
	private String id;
	
	// 文件名称
	private String filename;
	
	// 后缀
	private String ext;
	
	// 创建时间
	
	private Date createDate;
	
	// 大小
	private String size;
	
	// 显示大小
	private String showSize;
	
	// 标签
	private String sTag;
	
	// 路径
	private String path;
	
	// 下载地址
	private String downloadurl;
	
	// 查看地址
	private String previewurl;
	
	// 状态
	private String status;
	
	// 消息
	private String msg;
	
	// 时间
	private String date;
	
	// md5
	private String md5;
	
	/** 
	 * 返回 文件ID删除用 
	 *  
	 * @return id 文件ID删除用 
	 */
	
	public String getId() {
		return id;
	}
	/**  
	 * 设置 文件ID删除用
	 * @param id 文件ID删除用
	 */
	
	public void setId(String id) {
		this.id = id;
	}
	/** 
	 * 返回 文件名称 
	 *  
	 * @return filename 文件名称 
	 */
	
	public String getFilename() {
		return filename;
	}
	/**  
	 * 设置 文件名称
	 * @param filename 文件名称
	 */
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/** 
	 * 返回 后缀 
	 *  
	 * @return ext 后缀 
	 */
	
	public String getExt() {
		return ext;
	}
	/**  
	 * 设置 后缀
	 * @param ext 后缀
	 */
	
	public void setExt(String ext) {
		this.ext = ext;
	}
	/** 
	 * 返回 大小 
	 *  
	 * @return size 大小 
	 */
	
	public String getSize() {
		return size;
	}
	/**  
	 * 设置 大小
	 * @param size 大小
	 */
	
	public void setSize(String size) {
		this.size = size;
	}
	/** 
	 * 返回 路径 
	 *  
	 * @return path 路径 
	 */
	
	public String getPath() {
		return path;
	}
	/**  
	 * 设置 路径
	 * @param path 路径
	 */
	
	public void setPath(String path) {
		this.path = path;
	}
	/** 
	 * 返回 下载地址 
	 *  
	 * @return downloadurl 下载地址 
	 */
	
	public String getDownloadurl() {
		return downloadurl;
	}
	/**  
	 * 设置 下载地址
	 * @param downloadurl 下载地址
	 */
	
	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	/** 
	 * 返回 查看地址 
	 *  
	 * @return previewurl 查看地址 
	 */
	
	public String getPreviewurl() {
		return previewurl;
	}
	/**  
	 * 设置 查看地址
	 * @param previewurl 查看地址
	 */
	
	public void setPreviewurl(String previewurl) {
		this.previewurl = previewurl;
	}
	/** 
	 * 返回 状态 
	 *  
	 * @return status 状态 
	 */
	
	public String getStatus() {
		return status;
	}
	/**  
	 * 设置 状态
	 * @param status 状态
	 */
	
	public void setStatus(String status) {
		this.status = status;
	}
	/** 
	 * 返回 消息 
	 *  
	 * @return msg 消息 
	 */
	
	public String getMsg() {
		return msg;
	}
	/**  
	 * 设置 消息
	 * @param msg 消息
	 */
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/** 
	 * 返回 时间 
	 *  
	 * @return date 时间 
	 */
	
	public String getDate() {
		return date;
	}
	/**  
	 * 设置 时间
	 * @param date 时间
	 */
	
	public void setDate(String date) {
		this.date = date;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getShowSize() {
		return showSize;
	}
	public void setShowSize(String showSize) {
		this.showSize = showSize;
	}
	public String getsTag() {
		return sTag;
	}
	public void setsTag(String sTag) {
		this.sTag = sTag;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
