package com.skyworth.core.actor.entity;

import org.hibernate.validator.constraints.Length;

import com.skyworth.core.base.BaseEntity;

/**
 * 岗位权限管理Entity
 * @author 魏诚
 */
public class ActorPermission extends BaseEntity {
	
	private Integer gwid;		// 岗位ID
	private String qxid;		// 权限ID
	
	public ActorPermission() {
		super();
	}

	public ActorPermission(String id){
		super(id);
	}

	/**
     * 岗位ID
     */
	public Integer getGwid() {
		return gwid;
	}

	/**
     * 岗位ID
     */
	public void setGwid(Integer gwid) {
		this.gwid = gwid;
	}
	
	/**
     * 权限ID
     */
	@Length(min=1, max=50, message="权限ID长度必须介于 1 和 50 之间")
	public String getQxid() {
		return qxid;
	}

	/**
     * 权限ID
     */
	public void setQxid(String qxid) {
		this.qxid = qxid == null ? null : qxid.trim();
	}
	
}