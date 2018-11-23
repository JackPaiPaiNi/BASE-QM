package com.skyworth.core.user.dao;

import java.util.Map;

import com.skyworth.core.base.BaseDao;
import com.skyworth.core.base.BatisRepository;

/**
 * 用户信息管理DAO接口
 * @author 魏诚
 */
@BatisRepository
public interface UserDao extends BaseDao {
	
	void selectByGh(Map<String, Object> param);
	
	void selectByGw(Map<String, Object> param);
	// 判断岗位是否有人
	void checkByGw(Map<String, Object> param);	
	
}