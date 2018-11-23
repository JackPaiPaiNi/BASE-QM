package com.skyworth.core.actor.vo;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.actor.entity.ActorPermission;

/**
 * 岗位权限管理Entity
 * @author 魏诚
 */
public class ActorPermissionVo extends ActorPermission {
	
	private String qxbm;	// 权限编码

	
	public ActorPermissionVo() {
		super();
	}

	public ActorPermissionVo(String id){
		super(id);
	}
	
	/**
     * 权限编码
     */
	@Length(min=1, max=100, message="权限编码长度必须介于 1 和 100 之间")
	public String getQxbm() {
		return qxbm;
	}

	/**
     * 权限编码
     */
	public void setQxbm(String qxbm) {
		this.qxbm = qxbm == null ? null : qxbm.trim();
	}

}