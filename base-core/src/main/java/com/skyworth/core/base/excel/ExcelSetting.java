package com.skyworth.core.base.excel;

import java.util.Map;

/**
 * ExcelSetting excel配置信息
 * @author 魏诚
 */
public class ExcelSetting {
	
	private String id;
	private String className;
	private String description;
	private int startRow;
	private boolean defaultConfig;
	/**
	 * spring bean or class name
	 */
	private boolean stopOnError = false;
	private boolean redirect = false;
	private String nextAction;
	private String idField;
	private String codeField;
	private Map<Integer, ExcelSettingColumn> columns;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public Map<Integer, ExcelSettingColumn> getColumns() {
		return columns;
	}

	public void setColumns(Map<Integer, ExcelSettingColumn> columns) {
		this.columns = columns;
	}

	public boolean isDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getCodeField() {
		return codeField;
	}

	public void setCodeField(String codeField) {
		this.codeField = codeField;
	}

	public boolean getRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public boolean isStopOnError() {
		return stopOnError;
	}

	public void setStopOnError(boolean stopOnError) {
		this.stopOnError = stopOnError;
	}
}
