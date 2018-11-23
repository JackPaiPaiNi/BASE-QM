
package com.skyworth.bi.mdm.dao;

import java.util.Map;

import com.skyworth.core.base.BaseDao;
import com.skyworth.core.base.BatisRepository;



/**
 * 用户兼职信息  Dao
 * @author 高文浩
 * 
 * */
@BatisRepository
public interface YhjzxxDao extends BaseDao{

	void cancel(Map<String, Object> param);

	void selectJzbm(Map<String, Object> param);

}
