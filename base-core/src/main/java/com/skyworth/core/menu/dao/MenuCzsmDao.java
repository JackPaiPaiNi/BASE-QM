package com.skyworth.core.menu.dao;

import java.util.Map;

import com.skyworth.core.base.BaseDao;
import com.skyworth.core.base.BatisRepository;

/**
 * 报表取数规则DAO接口
 * @author 魏诚
 */
@BatisRepository
public interface MenuCzsmDao  extends BaseDao{
	void selectCzsm(Map<String, Object> param);
}