package com.skyworth.core.actor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skyworth.core.actor.dao.ActorDao;
import com.skyworth.core.actor.vo.ActorVo;
import com.skyworth.core.base.BaseService;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.menu.vo.MenuVo;

/**
 * 岗位信息管理Service
 * @author 魏诚
 */
@Service
public class ActorService extends BaseService<ActorDao, ActorVo> {
	
	public List<MenuVo> queryMenuQx(Integer gwid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gwid", gwid);
		dao.selectMenuQx(param);
		@SuppressWarnings("unchecked")
		List<MenuVo> list = (List<MenuVo>) param.get("list");
		
		return list;
	}
	
	@Transactional
	public ResultEntity saveQx(Integer gwid, String cdid, String qxid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gwid", gwid);
		param.put("cdid", cdid);
		param.put("qxid", qxid);
		dao.saveQx(param);
		super.afterCallProcedure(param);
		return super.entity;
	}
	
	@Override
	public ResultEntity save(ActorVo vo) {
		if(StringUtils.isBlank(vo.getId())){
			vo.preInsert();
			vo.setId(null);
		} else {
			vo.preUpdate();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.save(param);
		super.afterCallProcedure(param);
		super.entity.setVo(vo);
		return super.entity;
	}
	
}