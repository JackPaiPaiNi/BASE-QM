package com.skyworth.core.base.entity;


public class Page {

	// 页数
	private int page = 0;
	// 每一页的数
	private int rows = 0;
	// 报表类型
	private String sort = null;
	// 排序方式：ASC或DES
	private String order = null;
	
	
	/**
	 * @return page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page 要设置的 page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return rows
	 */
	public int getRows() {
		return rows;
	}
	/**
	 * @param rows 要设置的 rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * @return sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort 要设置的 sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order 要设置的 order
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	
}
