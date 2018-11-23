package com.skyworth.dashboard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyworth.core.spring.MultipleDataSource;
import com.skyworth.dashboard.dao.DpDao;
import com.skyworth.dashboard.vo.DpVo;

/**
 * Description:驾驶舱图表Service
 * 
 * @author 魏诚
 * @date 2018年8月21日
 */
@Service
public class DpService {
	
	@Autowired
	private DpDao dao;
	
	public Object queryYwbm(DpVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		MultipleDataSource.setDataSourceKey("cwbbDataSource");
		dao.selectYwbm(param);
		MultipleDataSource.setDataSourceKey("biDataSource");
		return param;
	}
	
	public Object queryFgs(DpVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		MultipleDataSource.setDataSourceKey("cwbbDataSource");
		dao.selectFgs(param);
		MultipleDataSource.setDataSourceKey("biDataSource");
		return param;
	}
	
	public Object queryMap(DpVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		MultipleDataSource.setDataSourceKey("cwbbDataSource");
		dao.selectMap(param);
		MultipleDataSource.setDataSourceKey("biDataSource");
		return param;
	}
	
}
