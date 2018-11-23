package com.skyworth.core.base.entity;

import java.util.List;

public class Paginator {
	
	// 记录数
	private int total = 0;
	
	// 每页行数
	private int pageRows = 0;
	
	// 页数
	private int totalPage = 0;
	
	// 当页数据
	private List<?> rows = null;
	
	/**	
	 * @param total
	 * @param rows
	 */	
	public Paginator(int total, int pageRows, List<?> rows) {
		super();
		this.total = total;
		this.pageRows = pageRows;
		Double tmp = Math.ceil((double)total / pageRows);
		this.totalPage = tmp.intValue();
		this.rows = rows;
	}
	/**
	 * @return total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total 要设置的 total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * @return rows
	 */
	public List<?> getRows() {
		return rows;
	}
	/**
	 * @param total 要设置的 total
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
