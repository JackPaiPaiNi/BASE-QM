package com.skyworth.core.menu.entity;

import com.skyworth.core.base.BaseEntity;

/**
 * 菜单操作说明Entity
 * 
 * @author 王歌
 */
public class MenuCzsm extends BaseEntity {
	private String czsm; // 图标
	
	public MenuCzsm() {
		super();
	}

	public MenuCzsm(String id){
		super(id);
	}

	public String getCzsm() {
		return czsm;
	}

	public void setCzsm(String czsm) {
		this.czsm = czsm;
	}

}