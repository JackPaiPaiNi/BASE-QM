package com.skyworth.core.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.skyworth.core.actor.vo.ActorVo;
import com.skyworth.core.common.Constants;
import com.skyworth.core.user.vo.UserVo;

/**
 * 用户工具类
 */
public class UserUtils {

	/**
	 * 获取当前用户
	 */
	public static UserVo getUser(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		UserVo curUser = (UserVo)session.getAttribute(Constants.CURRENT_USER);
		return curUser;
	}
	
	/**
	 * 是否有岗位
	 */
	public static boolean hasActor(String actorId){
		if (StringUtils.isBlank(actorId)) {
			return false;
		}
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		@SuppressWarnings("unchecked")
		List<ActorVo> actorList = (List<ActorVo>)session.getAttribute(Constants.CURRENT_ACTORS);
		
		for (int i = 0; i < actorList.size(); i++) {
			if (actorId.equals(actorList.get(i).getId())) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 可查看部门列表，逗号隔开
	 */
	public static String getViewOrgs(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String viewOrgs = (String)session.getAttribute(Constants.CURRENT_VIEW_ORGS);
		return viewOrgs;
	}
	
	/**
	 * 可操作部门列表，逗号隔开
	 */
	public static String getOperOrgs(){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String operOrgs = (String)session.getAttribute(Constants.CURRENT_OPER_ORGS);
		return operOrgs;
	}
	
}
