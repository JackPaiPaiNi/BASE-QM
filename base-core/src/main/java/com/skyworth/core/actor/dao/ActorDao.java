package com.skyworth.core.actor.dao;

import java.util.Map;

import com.skyworth.core.base.BaseDao;
import com.skyworth.core.base.BatisRepository;

/**
 * 岗位信息管理DAO接口
 * @author 魏诚
 */
@BatisRepository
public interface ActorDao extends BaseDao {
	
	// 菜单权限查询（权限）
	void selectMenuQx(Map<String, Object> param);
	
	// 权限保存
	void saveQx(Map<String, Object> param);
	
}