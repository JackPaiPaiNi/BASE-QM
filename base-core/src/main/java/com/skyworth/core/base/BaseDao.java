package com.skyworth.core.base;

import java.util.Map;

public interface BaseDao {

	void selectById(Map<String, Object> param);

	void select(Map<String, Object> param);

	void save(Map<String, Object> param);

	void delete(Map<String, Object> param);

}

