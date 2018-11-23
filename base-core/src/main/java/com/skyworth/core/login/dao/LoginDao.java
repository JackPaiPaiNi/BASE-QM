package com.skyworth.core.login.dao;

import java.util.Map;

import com.skyworth.core.base.BatisRepository;

/**
 * 登录管理DAO接口
 * @author 魏诚
 */
@BatisRepository
public interface LoginDao {
	// 根据工号取用户信息
	void selectUserByGh(Map<String, Object> param);
	// 根据工号、岗位取菜单
	void selectCd(Map<String, Object> param);
	// 根据工号、岗位取权限
	void selectQx(Map<String, Object> param);
	// 根据工号、岗位取session
	void selectSession(Map<String, Object> param);
}