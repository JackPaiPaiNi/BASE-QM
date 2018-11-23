package com.skyworth.core.menu.vo;

import com.skyworth.core.menu.entity.MenuPermission;

/**
 * 菜单权限管理Entity
 * @author 魏诚
 */
public class MenuPermissionVo extends MenuPermission {

	private Integer isChecked;

	public MenuPermissionVo() {
		super();
	}

	public MenuPermissionVo(String id){
		super(id);
	}

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

}