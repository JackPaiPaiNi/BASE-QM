package com.skyworth.bi.pub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyworth.bi.pub.dao.PubDao;
import com.skyworth.bi.pub.vo.PubBmVo;
import com.skyworth.bi.pub.vo.PubVo;
import com.skyworth.core.utils.UserUtils;


/**
 * 公共加载数据Service
 * @author 邓海
 */
@Service
public class PubService  {
	
	@Autowired
	private PubDao pubDao;
	
	/**
	 * 登陆账号和本地缓存不一致，强行设置重新获取新数据
	 * 拦截检验
	 * @param vo
	 */
	public void validateTimestamp(PubVo vo) {
		if (!UserUtils.getUser().getGh().equals(vo.getUsercode())) {
			vo.setTimestamp("-1");// 默认当前缓存不存在，后台遇到-1就会加载最新数据
		}
		//强制设置取当前登陆账号的可控数据
		vo.setUsercode(UserUtils.getUser().getGh());
	}
	
	/**
	 * 加载所有部门
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object findBmAll() {
		Map<String, Object> param = new HashMap<String, Object>();
		pubDao.selectBmAll(param);
		List<PubBmVo> list = (List<PubBmVo>) param.get("list");
		return list;
	}
	
	/**
	 * 加载部门（缓存）
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object findBmByGh(PubVo vo) {
		this.validateTimestamp(vo);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		pubDao.selectBm(param);
		List<PubBmVo> list = (List<PubBmVo>) param.get("list");

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("rows", list);
		dataMap.put("timestamp", vo.getTimestamp());
		dataMap.put("usercode", vo.getUsercode());
		return dataMap;
	}
	
	
}