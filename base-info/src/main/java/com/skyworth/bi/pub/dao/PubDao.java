package com.skyworth.bi.pub.dao;

import java.util.Map;

import com.skyworth.core.base.BatisRepository;

/**
 * 公共加载数据DAO接口
 * @author 邓海
 */
@BatisRepository
public interface PubDao {
	//加载所有部门
	void selectBmAll(Map<String, Object> param);
	//加载部门（缓存）
	void selectBm(Map<String, Object> param);
	
}