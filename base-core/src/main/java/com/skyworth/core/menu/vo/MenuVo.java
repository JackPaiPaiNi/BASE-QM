package com.skyworth.core.menu.vo;

import java.util.ArrayList;
import java.util.List;

import com.skyworth.core.menu.entity.Menu;

/**
 * 菜单信息管理Entity
 * @author 魏诚
 */
public class MenuVo extends Menu {
	// 生成首页菜单时递归生成树形结构
	private List<MenuVo> children = new ArrayList<MenuVo>();
	
	//jqGrid tree,zTree模式需要字段
	private Integer level;
	private Integer open = 1;
	private Integer checked = 0;
	
	private Integer lx; //0:菜单1:权限
	private Integer isXtcd = 1; //为1时表示是取所有，为0不取系统内置菜单	
	private String sjcd;	//上级菜单

	public MenuVo() {
		super();
	}

	public MenuVo(String id){
		super(id);
	}
	
	public List<MenuVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVo> children) {
		this.children = children;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getSjcd() {
		return sjcd;
	}

	public void setSjcd(String sjcd) {
		this.sjcd = sjcd;
	}

	public Integer getIsXtcd() {
		return isXtcd;
	}

	public void setIsXtcd(Integer isXtcd) {
		this.isXtcd = isXtcd;
	}

}