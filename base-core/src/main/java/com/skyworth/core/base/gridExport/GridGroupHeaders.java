package com.skyworth.core.base.gridExport;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GridGroupHeaders {

	private Integer index;
	private List<GridGroupHeader> groupHeaderList = new ArrayList<GridGroupHeader>();
	
	
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}

	public List<GridGroupHeader> getGroupHeaderList() {
		return groupHeaderList;
	}

	public void setGroupHeaderList(List<GridGroupHeader> groupHeaderList) {
		this.groupHeaderList = groupHeaderList;
	}
	
}
