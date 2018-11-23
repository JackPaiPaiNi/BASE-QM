package com.skyworth.bi.mdm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.skyworth.bi.mdm.dao.YhjzxxDao;
import com.skyworth.bi.mdm.vo.YhjzxxVo;
import com.skyworth.core.base.BaseService;
import com.skyworth.core.base.entity.ResultEntity;

/**
 * 用户兼职信息 Service
 * 
 * @author 高文浩
 * 
 */
@Service
public class YhjzxxService extends BaseService<YhjzxxDao, YhjzxxVo> {
	
	/**
	 * 用户兼职取消
	 * @param vo
	 * @return
	 */
	public ResultEntity cancel(YhjzxxVo vo) {
		vo.preUpdate();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.cancel(param);
		afterCallProcedure(param);
		return entity;
	}

	public Object jzbmQuery(YhjzxxVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.selectJzbm(param);
		@SuppressWarnings("unchecked")
		List<YhjzxxVo> rows = (List<YhjzxxVo>) param.get("list");
		return rows;
	}
	
}
