package com.skyworth.core.base.gridExport;

import org.apache.poi.ss.usermodel.CellStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GridModel {

	private String label;// 列描述
	private String name;// 列名
	private String skyFormatType;// 格式化显示类型
	private String skyFormat;// 格式化显示
	private Integer skyFormatUnit;// 格式化显示单位如（1,10000）
	
	// 指定的字段不会被序列化和反序列化。
	@JsonIgnore
	private CellStyle cellStyle;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSkyFormatType() {
		return skyFormatType;
	}

	public void setSkyFormatType(String skyFormatType) {
		this.skyFormatType = skyFormatType;
	}

	public String getSkyFormat() {
		return skyFormat;
	}

	public void setSkyFormat(String skyFormat) {
		this.skyFormat = skyFormat;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	/**
	 * @return skyFormatUnit
	 */
	public Integer getSkyFormatUnit() {
		return skyFormatUnit;
	}

	/**
	 * @param skyFormatUnit 要设置的 skyFormatUnit
	 */
	public void setSkyFormatUnit(Integer skyFormatUnit) {
		this.skyFormatUnit = skyFormatUnit;
	}
	
}
