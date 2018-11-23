package com.skyworth.core.menu.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.skyworth.core.base.BaseService;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.menu.dao.MenuDao;
import com.skyworth.core.menu.vo.MenuVo;

/**
 * 菜单信息管理Service
 * @author 魏诚
 */
@Service
public class MenuService extends BaseService<MenuDao, MenuVo> {
	
	@Override
	public ResultEntity save(MenuVo vo) {
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