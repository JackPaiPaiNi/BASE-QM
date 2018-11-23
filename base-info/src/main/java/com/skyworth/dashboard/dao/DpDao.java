
package com.skyworth.dashboard.dao;

import java.util.Map;

import com.skyworth.core.base.BatisRepository;

/**
 * Description:驾驶舱图表Dao
 * 
 * @author 魏诚
 * @date 2018年8月21日
 */
@BatisRepository
public interface DpDao {
	
	void selectYwbm(Map<String, Object> param);
	
	void selectFgs(Map<String, Object> param);
	
	void selectMap(Map<String, Object> param);
	
}
