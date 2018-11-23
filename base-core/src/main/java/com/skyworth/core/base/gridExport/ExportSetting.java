package com.skyworth.core.base.gridExport;

import java.util.List;

import com.skyworth.core.base.gridExport.GridGroupHeaders;
import com.skyworth.core.base.gridExport.GridModel;
/**
 * ExcelSetting excel配置信息
 * @author 
 */
public class ExportSetting {
	
	private String fileName;
	
	private List<GridGroupHeaders> groupHeaders;
	
	private List<GridModel> columns;
	/** 
	 * 返回 bare_field_comment 
	 *  
	 * @return fileName bare_field_comment 
	 */
	
	public String getFileName() {
		return fileName;
	}
	/**  
	 * 设置 bare_field_comment
	 * @param fileName bare_field_comment
	 */
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/** 
	 * 返回 bare_field_comment 
	 *  
	 * @return columns bare_field_comment 
	 */
	
	public List<GridModel> getColumns() {
		return columns;
	}
	/**  
	 * 设置 bare_field_comment
	 * @param columns bare_field_comment
	 */
	
	public void setColumns(List<GridModel> columns) {
		this.columns = columns;
	}
	/** 
	 * 返回 bare_field_comment 
	 *  
	 * @return groupHeaders bare_field_comment 
	 */
	
	public List<GridGroupHeaders> getGroupHeaders() {
		return groupHeaders;
	}
	/**  
	 * 设置 bare_field_comment
	 * @param groupHeaders bare_field_comment
	 */
	
	public void setGroupHeaders(List<GridGroupHeaders> groupHeaders) {
		this.groupHeaders = groupHeaders;
	}
}
